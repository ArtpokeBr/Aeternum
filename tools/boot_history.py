"""Boot-time history of this instance, built from the game logs.

Usage:
    python tools/boot_history.py            # update tools/boot_history.csv, print recent boots
    python tools/boot_history.py --last 40  # show more rows

Scans logs/latest.log and the archived logs/*.log.gz (idempotent: boots are keyed by log
start time, so re-running only adds new ones). A boot counts when ModernSplash logged
"Startup took"; launches that crashed or were closed before the main menu are skipped.
The per-stage split comes from config/cleanroom_load_timings.dat, which Cleanroom
overwrites every launch, so only the latest boot gets it.
"""
import argparse
import csv
import datetime as dt
import gzip
import re
import struct
import subprocess
from pathlib import Path

INSTANCE = Path(__file__).resolve().parent.parent
LOGS = INSTANCE / "logs"
TIMINGS = INSTANCE / "config" / "cleanroom_load_timings.dat"
HISTORY = Path(__file__).resolve().parent / "boot_history.csv"
COLUMNS = ["started", "startup_s", "mods", "cleanroom", "java", "xmx", "pack_version", "commit", "stages"]

PATTERNS = {
    "first_time": re.compile(r"^\[(\d\d:\d\d:\d\d)\]"),
    "startup_ms": re.compile(r"\[ModernSplash\]: Startup took (\d+)ms"),
    "mods": re.compile(r"Forge Mod Loader has identified (\d+) mods"),
    "cleanroom": re.compile(r"\[FML\]: Cleanroom version (\S+)"),
    "java": re.compile(r"\[FML\]: Java is .*?version (\d[\w.]*)"),
    "xmx": re.compile(r"JVM Flags:.*?-Xmx(\w+)"),
}


def read_lines(path):
    opener = gzip.open if path.suffix == ".gz" else open
    with opener(path, "rt", encoding="utf-8", errors="replace") as f:
        yield from f


def parse_log(path):
    found = {}
    for line in read_lines(path):
        for key, pat in PATTERNS.items():
            if key not in found:
                m = pat.search(line)
                if m:
                    found[key] = m.group(1)
        if "startup_ms" in found:
            break
    if "startup_ms" not in found or "first_time" not in found:
        return None
    if path.suffix == ".gz":
        date = path.name[:10]  # Forge names archives by the date they were rotated, i.e. the boot date
    else:
        date = dt.datetime.fromtimestamp(path.stat().st_ctime).strftime("%Y-%m-%d")
    return {
        "started": f"{date} {found['first_time']}",
        "startup_s": f"{int(found['startup_ms']) / 1000:.1f}",
        "mods": found.get("mods", ""),
        "cleanroom": found.get("cleanroom", ""),
        "java": found.get("java", ""),
        "xmx": found.get("xmx", ""),
    }


def read_stage_timings():
    """Cleanroom's loading-bar timings: int version, int count, then (UTF name, int ms) pairs."""
    data = TIMINGS.read_bytes()
    i = 8
    stages = []
    while i < len(data):
        (n,) = struct.unpack_from(">H", data, i)
        name = data[i + 2:i + 2 + n].decode()
        (ms,) = struct.unpack_from(">i", data, i + 2 + n)
        stages.append((name, ms))
        i += 6 + n
    return stages


def git_head():
    try:
        return subprocess.run(["git", "rev-parse", "--short", "HEAD"], cwd=INSTANCE,
                              capture_output=True, text=True, check=True).stdout.strip()
    except (OSError, subprocess.CalledProcessError):
        return ""


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument("--last", type=int, default=15)
    args = ap.parse_args()

    rows = {}
    if HISTORY.exists():
        with HISTORY.open(newline="", encoding="utf-8") as f:
            rows = {r["started"]: r for r in csv.DictReader(f)}

    added = 0
    for path in sorted(LOGS.glob("[0-9]*.log.gz")):
        boot = parse_log(path)
        if boot and boot["started"] not in rows:
            rows[boot["started"]] = {**boot, "pack_version": "", "commit": "", "stages": ""}
            added += 1

    latest = LOGS / "latest.log"
    boot = parse_log(latest) if latest.exists() else None
    if boot and boot["started"] not in rows:
        # Only the current boot can be tied to the working tree and to the timings file.
        stages = ""
        if TIMINGS.exists() and abs(TIMINGS.stat().st_mtime - latest.stat().st_mtime) < 6 * 3600:
            stages = " ".join(f"{name}={ms}" for name, ms in read_stage_timings())
        version_file = INSTANCE / "metadata" / "pack_version.txt"
        rows[boot["started"]] = {
            **boot,
            "pack_version": version_file.read_text(encoding="utf-8").strip() if version_file.exists() else "",
            "commit": git_head(),
            "stages": stages,
        }
        added += 1

    ordered = sorted(rows.values(), key=lambda r: r["started"])
    with HISTORY.open("w", newline="", encoding="utf-8") as f:
        writer = csv.DictWriter(f, fieldnames=COLUMNS)
        writer.writeheader()
        writer.writerows(ordered)

    print(f"{len(ordered)} boots recorded ({added} new) -> {HISTORY.relative_to(INSTANCE)}\n")
    print(f"{'started':<20}{'startup':>9}{'mods':>6}  {'cleanroom':<14}{'java':<6}{'xmx':<7}commit")
    for r in ordered[-args.last:]:
        print(f"{r['started']:<20}{r['startup_s']:>8}s{r['mods']:>6}  {r['cleanroom']:<14}{r['java']:<6}"
              f"{r['xmx']:<7}{r['commit']}")

    times = [float(r["startup_s"]) for r in ordered[-10:]]
    if times:
        print(f"\nlast {len(times)} boots: median {sorted(times)[len(times) // 2]:.1f}s, "
              f"min {min(times):.1f}s, max {max(times):.1f}s")

    last = ordered[-1] if ordered else None
    if last and last["stages"]:
        stages = [(s.split("=")[0], int(s.split("=")[1])) for s in last["stages"].split()]
        total = sum(ms for _, ms in stages)
        print(f"\nstages of the {last['started']} boot (Cleanroom loading bar, {total / 1000:.1f}s):")
        for name, ms in sorted(stages, key=lambda s: -s[1]):
            if ms >= 100:
                print(f"  {name:<24}{ms / 1000:7.1f}s  {100 * ms / total:5.1f}%")


if __name__ == "__main__":
    main()
