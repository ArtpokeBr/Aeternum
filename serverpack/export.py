"""Build the Aeternum CurseForge client export from the instance, then the server pack.

Usage:
    python serverpack/export.py [--version 1.6.3] [--no-server]

Output: serverpack/build/Aeternum-<version>.zip, the same layout the CurseForge app's
export writes (manifest.json, modlist.html, profileImage/, overrides/), and then the
server zip from build.py.

The manifest comes from minecraftinstance.json (the CurseForge app's record of the
installed files). Overrides are the git-tracked files matching serverpack/export.json
"include" and not "exclude", so a file ships only once it is tracked: the export
replaces ticking files in the app's export dialog.
"""

import argparse
import fnmatch
import json
import subprocess
import sys
import zipfile
from pathlib import Path

from build import HERE, INSTANCE, build_server, load_instance, pack_version


def git_paths(command, *args):
    out = subprocess.run(["git", command, "-z", *args], cwd=INSTANCE, capture_output=True, check=True).stdout
    return [p for p in out.decode("utf-8").split("\0") if p]


def excluded(path, patterns):
    return any(fnmatch.fnmatch(path, p) for p in patterns)


def build_manifest(instance, config, version):
    errors = []
    mods_dir = INSTANCE / "mods"
    addons = sorted(instance["installedAddons"], key=lambda a: a["name"].lower())

    managed = {a["fileNameOnDisk"] for a in addons}
    for jar in sorted(p.name for p in mods_dir.iterdir() if p.suffix in (".jar", ".disabled")):
        if jar not in managed and not excluded(jar, config["unmanagedJars"]):
            errors.append(f"mods/{jar} has no CurseForge record and is not in unmanagedJars")

    files = []
    for addon in addons:
        if addon["categoryClassID"] not in config["manifestCategoryClasses"]:
            continue
        if not (mods_dir / addon["fileNameOnDisk"]).is_file():
            errors.append(f"{addon['name']}: mods/{addon['fileNameOnDisk']} is missing (minecraftinstance.json stale?)")
        files.append({
            "projectID": addon["addonID"],
            "fileID": addon["installedFile"]["id"],
            "required": addon["isEnabled"],
            "isLocked": addon["isLocked"],
        })

    manifest = {
        "minecraft": {
            "version": instance["gameVersion"],
            "modLoaders": [{"id": instance["baseModLoader"]["name"], "primary": True}],
            "recommendedRam": config["recommendedRam"],
        },
        "manifestType": "minecraftModpack",
        "manifestVersion": 1,
        "name": config["name"],
        "version": version,
        "author": config["author"],
        "files": files,
        "overrides": "overrides",
        "image": "profileImage/" + Path(config["image"]).name,
    }
    return manifest, errors


def modlist_html(instance, manifest):
    by_id = {a["addonID"]: a for a in instance["installedAddons"]}
    lines = ["<ul>"]
    for entry in manifest["files"]:
        a = by_id[entry["projectID"]]
        lines.append(f'<li><a href="{a["webSiteURL"]}">{a["name"]} (by {a["primaryAuthor"]})</a></li>')
    lines.append("</ul>")
    return ("﻿" + "\r\n".join(lines) + "\r\n").encode("utf-8")


def override_paths(config):
    include, exclude = config["include"], config["exclude"]
    tracked = [p for p in git_paths("ls-files", "--", *include) if not excluded(p, exclude)]

    untracked = [p for p in git_paths("ls-files", "--others", "--exclude-standard", "--", *include)
                 if not excluded(p, exclude)]
    for p in untracked:
        print(f"warning: {p} is not tracked in git and is left out (git add it to ship it)", file=sys.stderr)
    modified = [p for p in git_paths("diff", "--name-only", "HEAD", "--", *include) if not excluded(p, exclude)]
    if modified:
        print(f"warning: {len(modified)} included files have uncommitted changes; the export uses the working tree:",
              *modified, sep="\n  ", file=sys.stderr)

    missing = [p for p in tracked if not (INSTANCE / p).is_file()]
    for p in missing:
        print(f"warning: {p} is tracked but missing on disk, left out", file=sys.stderr)
    return [p for p in tracked if p not in missing]


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--version", help="pack version (default: metadata/pack_version.txt)")
    parser.add_argument("--no-server", action="store_true", help="skip building the server zip")
    args = parser.parse_args()

    version = args.version or pack_version()
    config = json.loads((HERE / "export.json").read_text(encoding="utf-8"))
    instance = load_instance()

    manifest, errors = build_manifest(instance, config, version)
    if errors:
        print("Instance is not ready to export:", *errors, sep="\n  ", file=sys.stderr)
        sys.exit(1)
    overrides = override_paths(config)

    out_dir = HERE / "build"
    out_dir.mkdir(exist_ok=True)
    out = out_dir / f"Aeternum-{version}.zip"
    with zipfile.ZipFile(out, "w", zipfile.ZIP_DEFLATED) as dst:
        dst.writestr("manifest.json", json.dumps(manifest, indent=2, ensure_ascii=False))
        dst.writestr("modlist.html", modlist_html(instance, manifest))
        dst.write(INSTANCE / config["image"], manifest["image"])
        for rel in overrides:
            dst.write(INSTANCE / rel, "overrides/" + rel)

    print(f"Built {out.relative_to(INSTANCE)} ({out.stat().st_size / 1e6:.1f} MB): "
          f"{len(manifest['files'])} mods in manifest, {len(overrides)} override files")

    if not args.no_server:
        build_server(out, version)


if __name__ == "__main__":
    main()
