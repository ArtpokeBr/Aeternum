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

> [!NOTE]
> The default world type is **Realistic Terrain Generation** (`level-type=rtgc` in `server.properties`).
> For plain Biomes O' Plenty terrain use `level-type=BIOMESOP`, or `DEFAULT` for vanilla.

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
