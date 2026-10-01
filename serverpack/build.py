"""Build the Aeternum server pack from a CurseForge client export.

Usage:
    python serverpack/build.py <path/to/Aeternum-export.zip> [--version 1.6.0]

Output: serverpack/build/Aeternum-<version>-server.zip, containing the ServerStarter
files from this folder plus Aeternum-modpack.zip (the export's manifest + overrides).

ServerStarter 2.4.0 used to resolve CurseForge mod files through cursemeta.dries007.net,
which is shut down. It still honours a per-file "downloadUrl" in manifest.json, so this
script fills it in from minecraftinstance.json (the CurseForge app's record of the
installed files) before packing.
"""

import argparse
import io
import json
import re
import sys
import zipfile
from pathlib import Path

HERE = Path(__file__).resolve().parent
INSTANCE = HERE.parent

# Files from this folder shipped at the root of the server zip.
SERVER_FILES = [
    "server-setup-config.yaml",
    "server.properties",
    "start-server.bat",
    "start-server.sh",
    "SERVER_GUIDE.md",
]

# Override folders that are client-only and are left out of the modpack zip.
CLIENT_ONLY_OVERRIDES = [
    "overrides/resourcepacks/",
    "overrides/resources/",
]

MODPACK_ZIP_NAME = "Aeternum-modpack.zip"


def cdn_url(file_id, file_name):
    s = str(file_id)
    return f"https://edge.forgecdn.net/files/{s[:4]}/{int(s[4:])}/{file_name}"


def load_installed_files():
    with open(INSTANCE / "minecraftinstance.json", encoding="utf-8") as f:
        instance = json.load(f)
    return {a["addonID"]: a for a in instance["installedAddons"]}


def enrich_manifest(manifest, installed):
    errors = []
    for entry in manifest["files"]:
        addon = installed.get(entry["projectID"])
        if addon is None:
            errors.append(f"project {entry['projectID']} is in the export but not installed in this instance")
            continue
        file = addon["installedFile"]
        if file["id"] != entry["fileID"]:
            errors.append(
                f"{addon['name']}: export has file {entry['fileID']}, instance has {file['id']} "
                "(re-export after updating mods)"
            )
            continue
        entry["___name"] = addon["name"]
        entry["downloadUrl"] = file.get("downloadUrl") or cdn_url(file["id"], file["fileName"])
    return errors


def ignored_projects():
    text = (HERE / "server-setup-config.yaml").read_text(encoding="utf-8")
    block = text.split("ignoreProject:", 1)[1].split("baseInstallPath:", 1)[0]
    return {int(m) for m in re.findall(r"^\s*-\s*(\d+)", block, re.M)}


def check_scripts(src):
    """Warn when the export's scripts differ from the instance (a stale export desyncs recipes)."""
    exported = {i.filename[len("overrides/"):] for i in src.infolist() if i.filename.startswith("overrides/")}
    for folder in ("scripts", "groovy"):
        for path in (INSTANCE / folder).rglob("*"):
            if not path.is_file():
                continue
            rel = path.relative_to(INSTANCE).as_posix()
            if rel not in exported:
                print(f"warning: {rel} is missing from the export", file=sys.stderr)
            elif src.read("overrides/" + rel) != path.read_bytes():
                print(f"warning: {rel} differs from the export (re-export?)", file=sys.stderr)


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("export", type=Path, help="CurseForge client export zip")
    parser.add_argument("--version", help="pack version (default: metadata/pack_version.txt)")
    args = parser.parse_args()

    version = args.version or (INSTANCE / "metadata" / "pack_version.txt").read_text(encoding="utf-8").strip()
    installed = load_installed_files()

    with zipfile.ZipFile(args.export) as src:
        manifest = json.loads(src.read("manifest.json"))
        manifest["version"] = version

        errors = enrich_manifest(manifest, installed)
        if errors:
            print("Export does not match the instance:", *errors, sep="\n  ", file=sys.stderr)
            sys.exit(1)

        check_scripts(src)

        stale = ignored_projects() - {e["projectID"] for e in manifest["files"]}
        for pid in sorted(stale):
            print(f"warning: ignoreProject {pid} is not in the manifest (mod removed?)", file=sys.stderr)

        modpack = io.BytesIO()
        with zipfile.ZipFile(modpack, "w", zipfile.ZIP_DEFLATED) as dst:
            dst.writestr("manifest.json", json.dumps(manifest, indent=2, ensure_ascii=False))
            for info in src.infolist():
                name = info.filename
                if not name.startswith("overrides/") or info.is_dir():
                    continue
                if any(name.startswith(p) for p in CLIENT_ONLY_OVERRIDES):
                    continue
                dst.writestr(info, src.read(info))

    out_dir = HERE / "build"
    out_dir.mkdir(exist_ok=True)
    out = out_dir / f"Aeternum-{version}-server.zip"
    with zipfile.ZipFile(out, "w", zipfile.ZIP_DEFLATED) as dst:
        for name in SERVER_FILES:
            info = zipfile.ZipInfo.from_file(HERE / name, name)
            if name.endswith(".sh"):
                info.external_attr = 0o100755 << 16
            info.compress_type = zipfile.ZIP_DEFLATED
            dst.writestr(info, (HERE / name).read_bytes())
        dst.writestr(MODPACK_ZIP_NAME, modpack.getvalue())

    mods = len(manifest["files"])
    skipped = len(ignored_projects() & {e["projectID"] for e in manifest["files"]})
    print(f"Built {out.relative_to(INSTANCE)} ({out.stat().st_size / 1e6:.1f} MB): "
          f"{mods} mods in manifest, {skipped} client-only skipped on the server")


if __name__ == "__main__":
    main()
