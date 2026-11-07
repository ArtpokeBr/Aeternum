# metadata/

`changelog.md` is shown in game by Ender Modpack Tweaks. `pack_version.txt` holds the version string.

## changelog.md layout

One file holds **every** version, **newest at the top**. Each version is a block:

```
# Aeternum 1.6.3

### Mod Changes

- Added: Some Mod.
- Removed: Other Mod.

### Changed

- Full sentence describing what the player sees.

### Fixed

- Full sentence.

---

# Aeternum 1.6.2
...
```

- Only ever edit the **first** block, which is the version in `pack_version.txt` (the one `dev` is building). Older blocks are released history: never add to them. Section names like `### Changed` repeat in every version, so matching on the heading alone finds the wrong one.
- Sections in use: `Mod Changes`, `Changed`, `Fixed`, sometimes `Other`. Mod additions and removals go under `Mod Changes` as `Added: <Mod>.` / `Removed: <Mod>.`; recipe and config tweaks go under `Changed`.
- Entries are full, player-facing sentences (mod and item names capitalised, as in the existing lines) and **end with a full stop**.
- A version bump leaves empty `- ` placeholder bullets; the first real entry replaces the placeholder. Remove any still empty at release.
- Keep CRLF line endings.

## Adding a line

```bash
python ~/.claude/tools/pack-changelog/changelog_add.py --section Changed "Electrotine Ore now processes like Redstone Ore"
```

The tool only touches the newest block, adds the full stop, replaces placeholders and keeps CRLF. Run `--show` first if unsure; it should list the current version's sections only. After adding, check `git diff metadata/changelog.md`: the new lines must sit near the top of the file, under the current version heading.

Before 2026-10-23 the tool picked the **last** matching heading in the file, which put 1.6.3 entries into 1.1.0's `### Changed` section at the bottom (commits a758325, da5cdcc). If a hunk header in the diff shows a line number far down the file, the entry went into an old version.
