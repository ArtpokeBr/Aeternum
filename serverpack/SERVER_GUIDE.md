## Requirements

- **Java 21 or newer.** The server runs on [Cleanroom](https://cleanroommc.com/wiki/end-user-guide/installation/install-server), not stock Forge.
  If `java` on your PATH is older, set `forcedJavaPath` in `server-setup-config.yaml`.
- About 6 GB of RAM for the server (`maxRam` / `minRam` in `server-setup-config.yaml`).

## Installation

1) **Download** the server pack zip from the CurseForge page (`Files` → `Additional Files`).

2) **Extract** it into an empty folder.

3) Launch the server with **start-server.bat** (Windows) or **start-server.sh** (Linux, run it as `./start-server.sh`, not through `sh`).
    > On first launch, the `serverstarter` script installs Cleanroom, unpacks the bundled
    > `Aeternum-modpack.zip` (configs and scripts) and downloads every server-side mod
    > straight from the CurseForge CDN. Later launches skip this.

4) Accept the EULA when prompted (or set `eula=true` in `eula.txt`), then start again.

5) Players join with the normal Aeternum client from CurseForge, nothing extra to install.
    > Client-only mods (shaders, HUD, JEI addons, menus, ...) are skipped on the server automatically;
    > they are listed under `ignoreProject` in `server-setup-config.yaml`.

> [!NOTE]
> The default world type is **Realistic Terrain Generation** (`level-type=rtgc` in `server.properties`).
> For plain Biomes O' Plenty terrain use `level-type=BIOMESOP`, or `DEFAULT` for vanilla.

## Performance

- Normal ticks are light (around 10 ms of the 50 ms budget with a player online). Occasional
  `Can't keep up!` warnings come from generating new terrain (Recurrent Complex structures, RTG)
  and from the vanilla autosave every 45 seconds, not from a broken mod.
- To cut most of the world generation spikes, pregenerate the area around spawn before opening
  the server, for example with a pregenerator mod. Run the server on its own machine if you can.
- The pack includes Flare (Spark for 1.12):
  `/flare tps`, `/flare health` and `/flare sampler start` / `/flare sampler stop` profile the server
  if something does lag.
- Harmless startup noise (advancement parsing, BuildCraft facades, AE2 config entries) is hidden from
  the log by Stuff A Sock In It (`config/sasit.cfg`). FML's jar signature warnings appear before that
  filter loads, so they always show and can be ignored.

## Hosting

For hosting services with control panels like `Pterodactyl`:

1. Upload and **unzip** the server pack on your server.
2. Download [serverstarter-2.4.0.jar](https://github.com/EnigmaticaModpacks/ServerStarter/releases/tag/v2.4.0) and upload it next to the other files.
3. Set `serverstarter-2.4.0.jar` as the startup file.

## Updating

1) **Back up** your world.

2) **Back up** any configuration you changed (including `server.properties`).

3) **Delete** the `serverstarter.lock` file.
    > This tells the `serverstarter` script to reinstall the modpack.

4) **Extract** the new server pack zip into the server folder, overwriting files.

5) **Restore** your custom configuration and start the server.

## Adding Mods

ServerStarter re-downloads the mod list on every reinstall, so a mod dropped into `mods/` by hand is
lost when you update. Add server-side mods under `additionalFiles` in `server-setup-config.yaml`
(a direct download URL plus a `mods/` destination) to keep them across updates.
