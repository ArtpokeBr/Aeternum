# Server pack

Modelled on Enigmatica 2 Expert Extended's server pack: a small zip driven by [ServerStarter 2.4.0](https://github.com/EnigmaticaModpacks/ServerStarter). `start-server.bat`/`.sh` download `serverstarter-2.4.0.jar`, which reads `server-setup-config.yaml`. On first launch it installs Cleanroom via `installerUrl` (`cleanroom-<ver>-installer.jar --installServer`, launched as `startFile: cleanroom-<ver>.jar`), unpacks the bundled `Aeternum-modpack.zip` (`modpackUrl: file://Aeternum-modpack.zip`, relative to the server folder) and downloads every mod in its manifest except the CurseForge project IDs under `ignoreProject`. The server skips Cleanroom Relauncher.

## Release flow

`python serverpack/export.py` builds `serverpack/build/Aeternum-<ver>.zip` (client export) and then the server zip `Aeternum-<ver>-server.zip` (both gitignored); upload them to CurseForge. `--no-server` skips the server zip. The version comes from `metadata/pack_version.txt` (or `--version`). Exporting from the CurseForge app is no longer needed.

## `export.py`

- Same layout as the app's export: `manifest.json`, `modlist.html`, `profileImage/`, `overrides/`. The manifest is built from `minecraftinstance.json` (`installedAddons` of `categoryClassID` in `manifestCategoryClasses`, i.e. mods; `isEnabled` becomes `required`). It aborts if a jar in `mods/` has no CurseForge record and doesn't match `unmanagedJars` (e.g. the Celeritas dev jar), or if a recorded jar is missing on disk.
- Overrides are the **git-tracked** files under `include` in `serverpack/export.json`, minus `exclude` (fnmatch globs; `*/CLAUDE.md` and `config/brandon3055/ProjectIntelligence/manifest.json` are excluded). So a file ships only once it is `git add`ed. It warns about untracked files under `include` (left out), tracked files with uncommitted changes (the working tree is exported), and tracked files missing on disk.
- `export.json` also holds pack `name`, `author`, `recommendedRam`, and `image` (`serverpack/icon_256.png`). A new shipped top-level folder must be added to `include`.
- It imports `build.py` and calls `build_server(export, version)`.

## `build.py`

- Can still be run by hand on an app export: `python serverpack/build.py <export.zip> [--version X]`.
- ServerStarter's CurseForge lookup service (`cursemeta.dries007.net`) is shut down, so a stock export manifest can't be resolved. ServerStarter still honours a per-file `downloadUrl` in `manifest.json` (`ModEntryRaw`); `build.py` fills it from `minecraftinstance.json` → `installedFile.downloadUrl` (direct `edge.forgecdn.net` links, as E2EE does).
- Strips `overrides/resources` and `overrides/resourcepacks`, and every `CLAUDE.md`.
- Aborts if an export file ID doesn't match the installed one. Warns about `ignoreProject` IDs no longer in the manifest, and about any git-tracked file under `config/`, `scripts/`, `groovy/` missing from or differing from the export (a safety net for app exports). `CLAUDE.md` files are exempt. `config/brandon3055/ProjectIntelligence/manifest.json` is deliberately left out of the export and always warns.

## Finding a mod's CurseForge IDs

`python serverpack/modinfo.py <name, jar or project id>` prints project ID, file ID, jar name and name for each match. Don't read or grep `minecraftinstance.json`: it is a single 1.5 MB line.

## Keep in sync

- `config/relauncher.json` → `selectedVersion` changes: update `installerUrl` and `startFile` in `server-setup-config.yaml`.
- A new client-only mod: add its project ID to `ignoreProject`, in the right group (explicit `@Mod(clientSideOnly = true)` vs client-only by function). Cleanroom Relauncher is always ignored.
- `mods/celeritas-forge-*-dev.jar` is a dev build from [Celeritas-auto-build](https://github.com/kappa-maintainer/Celeritas-auto-build/releases), deliberately not in the export (client-only; Celeritas Extra and Dynamic Lights no-op without it). File Director (`mods/!mod-director-launchwrapper-*.jar`, ignored on the server) downloads it on the client from `config/mod-director/celeritas.bundle.json` as an optional, pre-ticked choice. Celeritas only takes effect after a restart, because its coremod can't be loaded in the launch that downloads it: VintageFix's early `classExists` probe makes Cleanroom's class loader cache `CeleritasLoadingPlugin` as invalid. So the entry has `"inject": false`, which skips File Director's late load and its ERROR trace. File Director treats a file as installed by its exact name. To bump Celeritas: set the new release `url`, set `fileName` to the jar name without the `01-` prefix, set `metadata.hash.MD5` (`md5sum`; MD5 because File Director pads hashes to only 32 hex chars), and set `installationPolicy.supersede` to the previous `fileName`, or players end up with two Celeritas jars.
- `server.properties` has `level-type=rtgc`: RTG's world type is `rtgc`, and `config/RTGC/rtgc.cfg` `defaultWorldType=true` only affects the singleplayer world screen.

## Classifying client-only mods

- Parse the `@Mod` annotation for `clientSideOnly = true`. A plain string search for `clientSideOnly` gives false hits (CraftTweaker's `ctgui` submod, Sound Physics' paired server mod).
- For jars without it: mods whose mixin configs have only `client` entries and that register no items, blocks or packets are client-only (Entity Culling, RenderLib, FancyMenu + Konkrete, Lumenized, …).
- JEI addons are not automatically client-only. Just Enough Drags sends a server-bound packet to set the dropped slot, and without it on the server, drags looked set but never saved.
- Read `mcmod.info` before trusting a name: Stuff A Sock In It is the pack's **log filter** and must stay on the server.
- When unsure, keep the mod on the server: an extra mod that boots is harmless, a missing one isn't.

## Testing a build

- Unzip the built zip into an empty scratch folder, add `serverstarter-2.4.0.jar`, run `java -jar serverstarter-2.4.0.jar`. It stops at the EULA on first boot; accepting it is the user's call.
- Then iterate with `java -Xmx6G -jar cleanroom-<ver>.jar nogui`. Piping a file into stdin (`tail -f console.in | java …`) gives a console for `stop`, `op`, `/flare …`.
- A random seed only exercises some worldgen; a structure-generation crash can hide behind a lucky seed. The final check is a real join, exploring new chunks, and visiting the Nether and End.
- Server-only crashes from client-only classes: see `scripts/mods/mixins/CLAUDE.md` → *Server-safe code* (Foundation blacklisting, `-Dfoundation.verbose=true`, Just Enough Drags' XNet probe).
- A GroovyScript "Found N failing members. GroovyScript will add these to sideOnlyGenerated.json" line in the server log means `groovy/sideOnlyGenerated.json` on that server is poisoned (it then strips real members, e.g. `NoSuchFieldError` in Mekanism's preInit). Clean the server's copy and declare the client-only members in the tracked `groovy/sideOnly.json`; details in `.claude/skills/aeternum-recipes/references/alloying.md` → *New Mekanism infuse types*.- **Local Cleanroom 0.7 server (no ServerStarter), worked with 0.7.12-alpha:** 0.7 installers no longer accept `--installServer`; run `java -jar cleanroom-0.7.12-alpha-installer.jar server --dir <dir> --yes --no-gui`. Copy in the server-side mods. Build a manifest-only `launcher.jar` whose `Class-Path` lists the `-cp` entries of `libraries/com/cleanroommc/cleanroom/0.7.12-alpha/win_args.txt` (ZenUtils needs `java.class.path` to be a single jar), then run `java -Xmx6G -jar launcher.jar --tweakClass net.minecraftforge.fml.common.launcher.FMLServerTweaker --gameDir . nogui`.
- Profiling: `flare sampler stop --save-to-file` keeps profiles local (the default uploads to the public spark.lucko.me). `.sparkprofile` is protobuf `SamplerData`: thread node children = field 3, times = 4, child refs = 5; stack node class/method/times/refs = 3/4/8/9; class→mod map = top-level field 3.
- **Baseline (1.6.0):** ~10 ms median tick with one player. Steady costs: Thaumic Augmentation `WardEventHandler.onWorldTick` (ward capability lookup on every loaded chunk each tick) and Extra Utilities 2 `PlayerPowerManager.tick` (scans inventories and baubles every tick, scales with players). 300–550 ms spikes are vanilla autosave (every 45 s) and new-chunk generation (Recurrent Complex decoration is the largest mod share).
