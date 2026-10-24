# Server pack

Modelled on Enigmatica 2 Expert Extended's server pack: a small zip driven by [ServerStarter 2.4.0](https://github.com/EnigmaticaModpacks/ServerStarter). `start-server.bat`/`.sh` download `serverstarter-2.4.0.jar`, which reads `server-setup-config.yaml`. On first launch it installs Cleanroom via `installerUrl` (`cleanroom-<ver>-installer.jar --installServer`, launched as `startFile: cleanroom-<ver>.jar`), unpacks the bundled `Aeternum-modpack.zip` (`modpackUrl: file://Aeternum-modpack.zip`, relative to the server folder) and downloads every mod in its manifest except the CurseForge project IDs under `ignoreProject`. The server skips Cleanroom Relauncher.

## Release flow

Export the client from the CurseForge app → `python serverpack/build.py <export.zip>` → upload `serverpack/build/Aeternum-<ver>-server.zip` (gitignored) as the CurseForge server file. The version comes from `metadata/pack_version.txt` (or `--version`).

## `build.py`

- ServerStarter's CurseForge lookup service (`cursemeta.dries007.net`) is shut down, so a stock export manifest can't be resolved. ServerStarter still honours a per-file `downloadUrl` in `manifest.json` (`ModEntryRaw`); `build.py` fills it from `minecraftinstance.json` → `installedFile.downloadUrl` (direct `edge.forgecdn.net` links, as E2EE does).
- Strips `overrides/resources` and `overrides/resourcepacks`, and every `CLAUDE.md`.
- Aborts if an export file ID doesn't match the installed one. Warns about `ignoreProject` IDs no longer in the manifest, and about any git-tracked file under `config/`, `scripts/`, `groovy/` missing from or differing from the export (the CurseForge export checklist doesn't auto-tick new files). `CLAUDE.md` files are exempt. `config/brandon3055/ProjectIntelligence/manifest.json` is deliberately left out of the export and always warns.

## Finding a mod's CurseForge IDs

`python serverpack/modinfo.py <name, jar or project id>` prints project ID, file ID, jar name and name for each match. Don't read or grep `minecraftinstance.json`: it is a single 1.5 MB line.

## Keep in sync

- `config/relauncher.json` → `selectedVersion` changes: update `installerUrl` and `startFile` in `server-setup-config.yaml`.
- A new client-only mod: add its project ID to `ignoreProject`, in the right group (explicit `@Mod(clientSideOnly = true)` vs client-only by function). Cleanroom Relauncher is always ignored.
- `mods/celeritas-forge-*-dev.jar` is a manual dev build from [Celeritas-auto-build](https://github.com/kappa-maintainer/Celeritas-auto-build/releases), deliberately not in the export (client-only; Celeritas Extra and Dynamic Lights no-op without it).
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
- Server-only crashes from client-only classes: see `scripts/mods/mixins/CLAUDE.md` → *Server-safe code* (Foundation blacklisting, `-Dfoundation.verbose=true`).
- Profiling: `flare sampler stop --save-to-file` keeps profiles local (the default uploads to the public spark.lucko.me). `.sparkprofile` is protobuf `SamplerData`: thread node children = field 3, times = 4, child refs = 5; stack node class/method/times/refs = 3/4/8/9; class→mod map = top-level field 3.
- **Baseline (1.6.0):** ~10 ms median tick with one player. Steady costs: Thaumic Augmentation `WardEventHandler.onWorldTick` (ward capability lookup on every loaded chunk each tick) and Extra Utilities 2 `PlayerPowerManager.tick` (scans inventories and baubles every tick, scales with players). 300–550 ms spikes are vanilla autosave (every 45 s) and new-chunk generation (Recurrent Complex decoration is the largest mod share).
