# Aeternum (1.12.2 Cleanroom Modpack)

Personal 1.12.2 kitchen-sink modpack ("Artpoke Presents: Aeternum") on [CurseForge](https://www.curseforge.com/minecraft/modpacks/artpoke-presents-aeternum). This repo is the instance folder, versioned in git and pushed to CurseForge as pack updates.

- Minecraft 1.12.2, Forge 14.23.5.2860, ~300 mods in `mods/` (flat). The `!`-prefixed jars load first: Red-Core, MixinBooter, ConfigAnytime, Cleanroom Relauncher.
- Cleanroom Relauncher (CurseForge project 1214490) relaunches the client on the [Cleanroom](https://cleanroommc.com/wiki/) loader; version and Java target in `config/relauncher.json` (`selectedVersion`, currently `0.7.12-alpha`, Java 25). The dedicated server installs Cleanroom directly.

## Layout

- `config/`: one config per mod. `scripts/`: [CraftTweaker](https://docs.blamejared.com/1.12/en/) (ZenScript), with `mods/`, `loot/`, `jei/`, `nc_script_addons/`. `groovy/`: [GroovyScript](https://cleanroommc.com/groovy-script/), with `preInit/`, `postInit/`, `assets/`.
- `patchouli_books/`: the in-game guidebook. `resourcepacks/`, `shaderpacks/`: client assets. `saves/`: local worlds, irrelevant.
- `metadata/changelog.md`: changelog shown in game by Ender Modpack Tweaks, all versions newest first; add lines with `changelog_add.py`, only ever to the top block. `metadata/pack_version.txt`: version string.
- `serverpack/`: dedicated server pack sources and `build.py`.
- `minecraftinstance.json`: CurseForge app metadata, one 1.5 MB line. **Never read or grep it**; use `python serverpack/modinfo.py <query>`.

## Doc map

Detail lives next to the code. A folder's `CLAUDE.md` loads once a file in that folder is read; **when starting new work in a folder, read its doc first.**

| Task | Read |
|---|---|
| Any recipe, alloy or machine recipe (which language, which API) | `aeternum-recipes` skill |
| CraftTweaker mixins, server-only crashes | `scripts/mods/mixins/CLAUDE.md` |
| Unification, plate/gear templates | `config/unidict/CLAUDE.md` |
| Custom JEI tabs (Requious) | `scripts/jei/CLAUDE.md` |
| Lang, models, textures for custom items | `groovy/assets/CLAUDE.md` |
| Biomes O'Plenty biome configs | `config/biomesoplenty/CLAUDE.md` |
| Server pack, client-only mods, server testing and profiling | `serverpack/CLAUDE.md` |
| Log noise, `config/sasit.cfg` | `docs/claude/log-filtering.md` |
| Performance mods (Celeritas, LoliASM, VintageFix, UT), StellarCore options | `docs/claude/performance-mods.md`, `docs/claude/stellarcore-conflicts.md` |
| Changelog entries (layout, which version block, style) | `metadata/CLAUDE.md` |

At the end of a change that alters how the pack works, run the `docs-keeper` agent with the base commit (or "uncommitted") and the feature names.

## Hazards

- **Never name a client-only class in a `#loader mixin` script**, even a `#sideonly server` one: Cleanroom blacklists the class and an unrelated mod crashes later. Recipe and mixin code must also avoid client-only calls (`Minecraft.getMinecraft()` etc.) or the dedicated server crashes.
- Changing `config/relauncher.json` `selectedVersion` means updating `installerUrl` and `startFile` in `serverpack/server-setup-config.yaml`.
- A new client-only mod needs its CurseForge project ID in `ignoreProject` in `serverpack/server-setup-config.yaml`.
- New files under `config/`, `scripts/`, `groovy/` must be tracked in git to ship; `serverpack/export.py` warns about untracked ones.
- Bumping the pack version: version and changelog strings (Ender Modpack Tweaks, FancyMenu main menu) must stay in sync.

## Conventions

- Commit prefixes: `📈 Mod Addition: <name>`, `✏️ Config Tweak: <what>`, `📄 Recipe Tweak: <what>`, `🛠️ Refactor: <what>` (fixing something added earlier), `✨ New Feature: <name>`, `🔧 Internal: <what>` (packaging, versioning, tooling), `⚙️ Bump: <what>`. One logical change per commit.
- Branches: `main` is the released version (what players and the server run). Work happens on `dev`, which carries the next version (bumped in `pack_version.txt` with a new changelog section right after a release) and merges into `main` when it ships.
- **Kitchen-sink, not expert:** a chill experience with mods talking to each other. Don't port balancing, progression gating or difficulty recipe replacements from expert packs; port only genuine cross-mod integrations and bug/exploit fixes.
- [Enigmatica2Expert-Extended](https://github.com/Krutoy242/Enigmatica2Expert-Extended) is a good reference for cross-mod recipes, mixins and JEI tabs, but much of it is expert gating or relies on E2EE-only mods and data. Verify every mod, item and ore-dict tag exists in this pack's jars (moddig) before porting, and don't trust its config values: some "default" cfgs (Astral Sorcery's `mineralis_ritual.cfg`) are templates never checked against installed mods.
- Unification goes through UniDict config first; block drops through Dropt.
- Major mods: Thermal series, Mekanism, AE2, Industrial Foregoing, IC2, Draconic Evolution, Astral Sorcery, Thaumcraft (+ addons), Botania, Tinkers' Construct (Antique fork), Forestry/Gendustry/Binnie, Pam's HarvestCraft, RFTools, Flux Networks.
