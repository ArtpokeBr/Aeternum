# Aeternum (1.12.2 Cleanroom Modpack)

A personal Minecraft 1.12.2 modpack ("Artpoke Presents: Aeternum"), published on
[CurseForge](https://www.curseforge.com/minecraft/modpacks/artpoke-presents-aeternum).
This repo is the pack's working directory (instance folder), tracked in git so changes to
mods/configs/scripts can be versioned and pushed to CurseForge as pack updates.

- Minecraft: 1.12.2
- Forge: 14.23.5.2860
- Loader/launch helpers: Red-Core, MixinBooter, ConfigAnytime, Improved Relauncher (the `!`-prefixed jars in `mods/`, loaded first)
- ~290 mods in [mods/](mods/)
- The Modpack is relaunched with Cleanroom: Improved Relauncher (`!improved-relauncher`) relaunches the pack using the [Cleanroom](https://cleanroommc.com/wiki/) mod loader instead of stock Forge

## Layout

- [mods/](mods/) — all mod jars (flat directory, no profiles)
- [config/](config/) — per-mod `.cfg`/`.ini`/`.json` config files, one entry per mod
- [scripts/](scripts/) and [groovy/](groovy/) — [CraftTweaker](https://docs.blamejared.com/1.12/en/) (legacy ZenScript) and [GroovyScript](https://cleanroommc.com/groovy-script/) recipe/loot scripts
  - `scripts/mods/`, `scripts/loot/`, `scripts/nc_script_addons/` — the pack also has the `MoreTweaker` mod installed for extra CraftTweaker compat; see [its wiki](https://bitbucket.org/noeppi_noeppi/moretweaker/wiki/Home) for syntax not covered by the main CraftTweaker docs
  - `groovy/preInit/`, `groovy/postInit/`
- [patchouli_books/](patchouli_books/) — custom in-game Patchouli guidebook content
- [resourcepacks/](resourcepacks/), [shaderpacks/](shaderpacks/) — client assets
- [saves/](saves/) — local worlds (not relevant to pack development)
- `minecraftinstance.json` — CurseForge/Overwolf app instance metadata (Forge version, mod list)
- `README.md` — just a pointer to the CurseForge page

## Conventions

- Commit messages use an emoji + category prefix, e.g.:
  - `📈 Mod Addition: <name>` — new mod added
  - `✏️ Config Tweak: <what changed>` — config file edit
  - `📄 Recipe Tweak: <what changed>` — CraftTweaker/Groovy script change
  - `🛠️ Refactor: <what changed>` — fixing something previously added
  - `✨ New Feature: <name>` — new pack feature (e.g. custom main menu)
  - `🔧 Internal: <what changed>` — packaging/pack-updater/versioning plumbing
  - `⚙️ Bump: <what>` — version/changelog bumps
- Keep one logical change (one mod, one config, one recipe tweak) per commit — matches existing history.
- This pack uses Ender Modpack Tweaks (`endermodpacktweaks`) for in-game pack version/changelog display and a FancyMenu-based custom main menu — version/changelog strings need to stay in sync when bumping pack version.
- Notable overhaul/tech mods present: Thermal series, Mekanism, Applied Energistics 2, Industrial Foregoing, IndustrialCraft 2, Draconic Evolution, Astral Sorcery, Thaumcraft (+ addons), Botania, Tinkers' Construct (Antique fork), Forestry/Gendustry/Binnie, Pam's HarvestCraft, RFTools, Flux Networks.
