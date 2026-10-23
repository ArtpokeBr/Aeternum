# Plan: split the pack docs and add a docs-keeper agent

## Why

`CLAUDE.md` is 26.5 KB (about 6.5k tokens). It loads in full at the start of every session, and again after
every compaction, whatever the task is. Most of it covers one subsystem each (mixins, the server pack,
UniDict, alloying), and a typical session touches one of them or none.

Claude Code also loads a `CLAUDE.md` placed in a subfolder, but only once a file in that folder (or below it)
is read. Artech already works this way: a 11.5 KB root that holds only what every session needs, package
`CLAUDE.md` files for the rest, and a `docs-keeper` agent (Sonnet) that keeps them true after each change.

Target: a root of about 5 KB (roughly 80% less loaded up front), with no knowledge lost.

## 1. Where each section goes

Sizes are the current sections.

| Section | Size | New home | Loads when |
|---|---|---|---|
| Header, Layout, Conventions | 5.5 KB | root `CLAUDE.md`, trimmed to ~3.5 KB | always |
| UniDict resources & templates | 2.3 KB | `config/unidict/CLAUDE.md` | a UniDict config is read |
| Biomes O'Plenty gotcha | 0.5 KB | `config/biomesoplenty/CLAUDE.md` | any BOP config is read, `defaults/` included |
| Requious Frakto | 1.1 KB | `scripts/jei/CLAUDE.md` | a JEI tab script is read |
| CraftTweaker-coded mixins | 3.9 KB | `scripts/mods/mixins/CLAUDE.md` | a mixin script is read |
| Server-safe code | 1.8 KB | same file as the mixins, plus one hazard line in root | as above |
| GroovyScript asset overlay | 0.3 KB | `groovy/assets/CLAUDE.md` | an overlay asset is read |
| NuclearCraft (no Groovy, Manufactory ore-dict bug) | 2.4 KB | project skill `aeternum-recipes`, `references/nuclearcraft.md` | the skill is invoked |
| Alloying methods | 3.0 KB | same skill, `references/alloying.md` | the skill is invoked |
| Server pack | 4.8 KB | `serverpack/CLAUDE.md` | anything in `serverpack/` is read |
| Log filtering (sasit) | 1.0 KB | `docs/claude/log-filtering.md`, pointed to from root | read on demand |

Notes on the choices:

- **Folder docs** suit knowledge tied to one folder. Ancestor folders load too, so nothing goes in
  `scripts/CLAUDE.md`, `scripts/mods/CLAUDE.md` or `config/CLAUDE.md`: those would load for nearly every task.
- **A project skill** suits recipe work, which spans `groovy/` and `scripts/` and is task-shaped ("add an alloy",
  "add an NC recipe"). Only its description (~300 bytes) is always loaded. `SKILL.md` holds the language rule
  (Groovy first, CraftTweaker only where GroovyScript 1.4.3 has no compat) and points at the references.
- **Lazy loading has one gap:** a folder doc loads only after a file there is read. Writing a new mixin without
  opening an existing one would miss it. The root keeps a doc map (below) that says which file to read first.

## 2. The new root `CLAUDE.md`

Keep:

- Pack facts: MC/Forge/Cleanroom versions, relauncher and `selectedVersion`, Ender Modpack Tweaks version sync.
- A shorter Layout (folders whose purpose isn't obvious from `ls`).
- Conventions: commit prefixes, one change per commit, `dev`/`main` flow, kitchen-sink policy, UniDict and
  Dropt as first choices, the E2EE porting caution (two sentences, not a paragraph).
- Cross-cutting hazards, one line each:
  - Never name a client-only class in a `#loader mixin` script, even a `#sideonly server` one.
  - Changing `config/relauncher.json` `selectedVersion` means updating `serverpack/server-setup-config.yaml`.
  - A new client-only mod gets its CurseForge ID in `ignoreProject`.
  - Never read or grep `minecraftinstance.json` directly (one 1.5 MB line); use the helper from step 5.
- A **doc map**: topic → file, with "read this before starting" wording.
- One line: at the end of a change that alters how the pack works, run the `docs-keeper` agent.

Drop: narrative history ("tried, failed", "red herring") wherever the conclusion alone is enough. Keep a
one-line "why" when it prevents a retry of a dead end (UniDict custom resources, the Shadow-field VerifyError).

## 3. `docs-keeper` agent (`.claude/agents/docs-keeper.md`)

Project-specific, so it lives in this repo's `.claude/`, not in Personal Agents and Skills. Adapted from Artech's:

- `model: sonnet`, tools `Read, Edit, Write, Grep, Glob, Bash` (Bash for read-only git only).
- **Briefed with a diff range and feature names only**, never a pasted diff. It runs `git diff --stat <base>`,
  then reads only the hunks it needs.
- Carries the section-to-file map from step 1 and these rules:
  - Root stays under 6 KB; changes there only for a new cross-cutting hazard, a new doc map entry or a
    changed convention.
  - A new subsystem gets its own folder doc (or a skill reference if it spans folders) plus a doc map line.
  - Present tense, describes how things work now, removes statements the diff made false.
  - Records what code can't tell: why, values that must match across files, side (client/server), pitfalls hit.
- Edits only `CLAUDE.md` files, `docs/claude/*.md` and `.claude/skills/aeternum-recipes/**`. Never touches
  `metadata/changelog.md` (that's `changelog_add.py`), code or configs, and never stages or commits.
- Reports the files changed and anything in the docs the diff contradicts that it couldn't resolve.

## 4. `serverpack/build.py`

`check_overrides` walks every git-tracked file under `config/`, `scripts/` and `groovy/`. New `CLAUDE.md` files
there would warn as "missing from the export" on every build. Skip names ending in `CLAUDE.md` in that check,
and strip them from the server zip's overrides. They are harmless if a client export happens to include them.

## 5. Other token savings

1. **`minecraftinstance.json` helper.** Add `serverpack/modinfo.py <query>`: prints name, CurseForge project ID,
   file ID, filename and side for matching mods. Today, finding an ID for `ignoreProject` means grepping a 1.5 MB
   single-line file. `build.py` already parses it, so the helper can share that code.
2. **Read deny rules** in `.claude/settings.json`: `minecraftinstance.json`, `logs/**`, `*.log`, `llibrary/**`,
   `saves/**`, `dumps/**`. Bash scripts (`/log-sweep`, `moddig`) still work. Check in a fresh session whether
   the deny also covers the Grep tool; if not, add a note to the root hazards.
3. **Cleaner `git status`.** It is pasted into every session's context. `llibrary/debug/*.class` shows as
   modified every time. Untracking it (`git rm -r --cached llibrary/debug` plus a `.gitignore` line) removes the
   noise. This removes files from the index, so it needs your OK first. Also decide on the untracked
   `config/brandon3055/contributors.json` and the Classic Faithful zip (ignore or commit).
4. **Trim the global `CLAUDE.md` routing table** (in Personal Agents and Skills): several rows are NeoForge-only
   (`/datapack-override`, `.build.json` pinning) and cost tokens in every 1.12 session. A one-word "(NeoForge)"
   tag keeps them correct without a second file. Optional; it affects every project.

## Order

1. Create the folder docs, skill and `docs/claude/log-filtering.md`, moving text verbatim first.
2. Rewrite the root (doc map, hazards, trimmed conventions).
3. Edit pass on the moved text: present tense, cut history.
4. `build.py` skip and `modinfo.py`.
5. `docs-keeper` agent.
6. `.claude/settings.json` deny rules; `llibrary/debug` untrack after your OK.
7. Check: every heading of the old file maps to a new home (count old vs new); in a fresh session, `/context`
   shows the smaller root and reading a mixin script loads `scripts/mods/mixins/CLAUDE.md`; run `build.py` on
   the last export and see no `CLAUDE.md` warnings.

Each step is its own commit (`🔧 Internal: ...`), so any one can be reverted.
