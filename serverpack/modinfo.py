"""Look up installed mods in minecraftinstance.json without reading the 1.5 MB file.

Usage:
    python serverpack/modinfo.py <query> [<query> ...]

A query matches a mod's name or jar file name (case-insensitive substring), or its
CurseForge project ID exactly. Prints one line per match: project ID, file ID, whether
the server pack ignores it (ignoreProject), jar name and mod name.
"""

import argparse
import sys

from build import ignored_projects, load_installed_files


def matches(addon, query):
    if query.isdigit():
        return addon["addonID"] == int(query)
    q = query.lower()
    return q in addon["name"].lower() or q in addon["installedFile"]["fileName"].lower()


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("queries", nargs="+", help="mod name, jar name or project ID")
    args = parser.parse_args()

    installed = load_installed_files()
    ignored = ignored_projects()
    found = False
    for query in args.queries:
        for addon in sorted(installed.values(), key=lambda a: a["name"].lower()):
            if not matches(addon, query):
                continue
            found = True
            file = addon["installedFile"]
            side = "client-only" if addon["addonID"] in ignored else "server"
            print(f"{addon['addonID']}\t{file['id']}\t{side}\t{file['fileName']}\t{addon['name']}")
    if not found:
        sys.exit("no installed mod matches")


if __name__ == "__main__":
    sys.stdout.reconfigure(encoding="utf-8")
    main()
