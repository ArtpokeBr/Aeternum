"""Per-mod time breakdown of a Flare (spark) CPU profile.

Usage:
    python tools/flare_report.py                 # newest report URL in config/flare/activity/
    python tools/flare_report.py <url|key|file>  # spark.lucko.me URL, bytebin key, or .sparkprofile
    options: --thread NAME (default: "Client thread", "all" for every thread), --top N,
             --callers PATTERN [--depth N] (who calls a hot class/method, e.g. --callers JeiStarter.start)

Flare's stage profiling (S:stages in config/flare/flare.cfg) can only upload to spark's
bytebin; the activity log records the URL. The raw profile is fetched back from bytebin
and decoded here, so the spark viewer isn't needed.

Attribution: each sample goes to the deepest frame on its stack whose class belongs to a
mod jar (spark's class_sources). Vanilla/Forge/JDK work a mod calls into counts as that
mod's; samples with no mod frame at all count as "(minecraft/forge/jvm)". Code mixed into
vanilla classes stays vanilla, since mixins don't change a class's source jar.
"""
import argparse
import gzip
import re
import struct
import sys
import urllib.request
from collections import defaultdict
from pathlib import Path

INSTANCE = Path(__file__).resolve().parent.parent
ACTIVITY = INSTANCE / "config" / "flare" / "activity"
BYTEBIN = "https://spark-usercontent.lucko.me/"
UNOWNED = "(minecraft/forge/jvm)"
# Shaded libraries spark credits to whichever jars bundle them, e.g. Mixin to four unrelated mods.
LIBRARIES = {"org.spongepowered.asm.": "Mixin (library)"}
# Profiler and idle threads that dominate "all threads" without being boot work.
SKIP_THREADS = ("flare-", "Reference Handler", "Signal Dispatcher", "Finalizer")


def owner_name(cls, sources):
    for prefix, name in LIBRARIES.items():
        if cls.startswith(prefix):
            return name
    mod = sources.get(cls)
    if mod and ", " in mod:
        first, *rest = mod.split(", ")
        return f"{first} (+{len(rest)} jar mods)"
    return mod


def varint(buf, i):
    shift = result = 0
    while True:
        b = buf[i]
        i += 1
        result |= (b & 0x7F) << shift
        if not b & 0x80:
            return result, i
        shift += 7


def fields(buf):
    """Yield (field number, wire type, value) for one protobuf message."""
    i, end = 0, len(buf)
    while i < end:
        key, i = varint(buf, i)
        num, wt = key >> 3, key & 7
        if wt == 0:
            val, i = varint(buf, i)
        elif wt == 1:
            val, i = buf[i:i + 8], i + 8
        elif wt == 2:
            n, i = varint(buf, i)
            val, i = buf[i:i + n], i + n
        elif wt == 5:
            val, i = buf[i:i + 4], i + 4
        else:
            raise ValueError(f"unsupported wire type {wt}")
        yield num, wt, val


def doubles(wt, val):
    if wt == 1:
        return [struct.unpack("<d", val)[0]]
    return list(struct.unpack(f"<{len(val) // 8}d", val))


def ints(wt, val):
    if wt == 0:
        return [val]
    out, i = [], 0
    while i < len(val):
        v, i = varint(val, i)
        out.append(v)
    return out


def parse_stack_node(buf):
    node = {"cls": "", "method": "", "times": [], "refs": []}
    for num, wt, val in fields(buf):
        if num == 3:
            node["cls"] = val.decode("utf-8", "replace")
        elif num == 4:
            node["method"] = val.decode("utf-8", "replace")
        elif num == 8:
            node["times"] += doubles(wt, val)
        elif num == 9:
            node["refs"] += ints(wt, val)
    node["time"] = sum(node["times"])
    return node


def parse_thread(buf):
    thread = {"name": "", "nodes": [], "times": [], "refs": []}
    for num, wt, val in fields(buf):
        if num == 1:
            thread["name"] = val.decode("utf-8", "replace")
        elif num == 3:
            thread["nodes"].append(parse_stack_node(val))
        elif num == 4:
            thread["times"] += doubles(wt, val)
        elif num == 5:
            thread["refs"] += ints(wt, val)
    thread["time"] = sum(thread["times"])
    return thread


def parse_profile(data):
    if data[:2] == b"\x1f\x8b":
        data = gzip.decompress(data)
    threads, sources = [], {}
    for num, wt, val in fields(data):
        if num == 2:
            threads.append(parse_thread(val))
        elif num == 3:
            entry = dict((n, v.decode("utf-8", "replace")) for n, _, v in fields(val))
            sources[entry.get(1, "")] = entry.get(2, "")
    return threads, sources


def latest_report_url():
    logs = sorted(ACTIVITY.glob("*.log"), key=lambda p: p.stat().st_mtime) if ACTIVITY.is_dir() else []
    for log in reversed(logs):
        urls = re.findall(r"Report: (\S+)", log.read_text(encoding="utf-8", errors="replace"))
        if urls:
            return urls[-1], log
    sys.exit(f"no 'Report:' URL in {ACTIVITY}; did a stage sampler run and upload?")


def load(source):
    path = Path(source)
    if path.is_file():
        return path.read_bytes()
    key = source.rstrip("/").split("/")[-1].lstrip("#")
    req = urllib.request.Request(BYTEBIN + key, headers={"User-Agent": "aeternum-flare-report"})
    with urllib.request.urlopen(req, timeout=60) as r:
        return r.read()


def attribute(thread, sources, per_mod, per_method):
    nodes = thread["nodes"]

    def walk(idx, owner, depth=0):
        node = nodes[idx]
        mod = owner_name(node["cls"], sources) or owner
        child_time = 0.0
        for ref in node["refs"]:
            child_time += nodes[ref]["time"]
            walk(ref, mod, depth + 1)
        self_time = max(node["time"] - child_time, 0.0)
        per_mod[mod or UNOWNED] += self_time
        per_method[(mod or UNOWNED, f'{node["cls"]}.{node["method"]}')] += self_time

    sys.setrecursionlimit(max(sys.getrecursionlimit(), 20000))
    for ref in thread["refs"]:
        walk(ref, None)


def fmt(ms):
    return f"{ms / 1000:8.1f} s"


def callers(threads, pattern, depth, top):
    """Total time under frames matching `pattern`, grouped by the call chain above the outermost match."""
    chains = defaultdict(float)
    for thread in threads:
        nodes, parent = thread["nodes"], {}
        for i, node in enumerate(nodes):
            for ref in node["refs"]:
                parent[ref] = i

        def label(n):
            return f'{n["cls"].rsplit(".", 1)[-1]}.{n["method"]}'

        for i, node in enumerate(nodes):
            if pattern not in f'{node["cls"]}.{node["method"]}':
                continue
            above, j = [], i
            while j in parent:
                j = parent[j]
                above.append(nodes[j])
            if any(pattern in f'{n["cls"]}.{n["method"]}' for n in above):
                continue  # counted at the outermost match
            chain = " <- ".join([label(node)] + [label(n) for n in above[:depth]])
            chains[f'[{thread["name"]}] {chain}'] += node["time"]
    if not chains:
        sys.exit(f'no frame matches "{pattern}"')
    print(f'\ntime under "{pattern}", by caller chain:')
    for chain, ms in sorted(chains.items(), key=lambda kv: -kv[1])[:top]:
        print(f"  {fmt(ms)}  {chain}")


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("source", nargs="?", help="spark URL, bytebin key or .sparkprofile path")
    ap.add_argument("--thread", default="Client thread", help='thread name, or "all"')
    ap.add_argument("--top", type=int, default=30)
    ap.add_argument("--callers", metavar="PATTERN",
                    help="instead of the summary, show who calls frames whose class.method contains PATTERN")
    ap.add_argument("--depth", type=int, default=8, help="caller chain length for --callers")
    args = ap.parse_args()

    if args.source:
        source = args.source
    else:
        source, log = latest_report_url()
        print(f"report: {source}  (from {log.name})")
    threads, sources = parse_profile(load(source))

    print("\nthreads (sampled time):")
    for t in sorted(threads, key=lambda t: -t["time"])[:12]:
        print(f"  {fmt(t['time'])}  {t['name']}")

    if args.thread == "all":
        chosen = [t for t in threads if not t["name"].startswith(SKIP_THREADS)]
    else:
        chosen = [t for t in threads if t["name"] == args.thread]
    if not chosen:
        sys.exit(f'no thread named "{args.thread}"; pick one from the list above or use --thread all')
    if args.callers:
        callers(chosen, args.callers, args.depth, args.top)
        return
    total = sum(t["time"] for t in chosen)
    per_mod, per_method = defaultdict(float), defaultdict(float)
    for t in chosen:
        attribute(t, sources, per_mod, per_method)

    label = "all threads" if args.thread == "all" else args.thread
    print(f"\nper mod, {label} ({fmt(total).strip()} sampled):")
    for mod, ms in sorted(per_mod.items(), key=lambda kv: -kv[1])[:args.top]:
        print(f"  {fmt(ms)}  {100 * ms / total:5.1f}%  {mod}")

    print(f"\nhottest methods (self time), {label}:")
    for (mod, method), ms in sorted(per_method.items(), key=lambda kv: -kv[1])[:args.top]:
        print(f"  {fmt(ms)}  {100 * ms / total:5.1f}%  [{mod}] {method}")


if __name__ == "__main__":
    main()
