# Performance mod config audit (LoliASM, VintageFix, Universal Tweaks, Celeritas and friends)

How the optimisation mods fit together and why their options are set as they are. StellarCore's own option choices are in `docs/claude/stellarcore-conflicts.md`.

## Celeritas is optional

- Players install the Celeritas core jar (`celeritas-forge-*-dev.jar`) by hand. Celeritas Extra and Celeritas Dynamic Lights ship through CurseForge and must keep working without it.
- Celeritas Extra only references Celeritas in its options GUI; its other mixins target vanilla classes. So mods that matter only on the vanilla renderer stay in the pack.
- Celeritas reads `config/embeddium-options.json`.
- **ChunkAnimator** ASM-patches vanilla `RenderChunk`/`ChunkRenderContainer`, which Celeritas bypasses, so it does nothing for Celeritas players. Celeritas' `chunk_fade_in_duration` is 1000 ms to match ChunkAnimator's `AnimationDuration=1000`.
- **Better Biome Blend** (radius 10) and Celeritas both `@Overwrite` `BiomeColorHelper.getColorAtPos`; Celeritas (priority 1200) wins. Its terrain mesher uses its own BiomeColorCache with `legacy_biome_blend_radius` (slider 0-14). Radius 0 gave Celeritas players no blending; it is 3. Better Biome Blend still blends without Celeritas.
- Celeritas Extra also `@Inject`s into `getGrass`/`Water`/`FoliageColorAtPos`, but only forces default colours when its `biomeColors` option is false. It is true.
- Celeritas `use_entity_culling` and the EntityCulling mod complement each other (section/frustum visibility vs occlusion). Both stay on.
- `config/nothirium.cfg` is stale: Nothirium isn't installed (the Celeritas addons support it as an alternative renderer). Harmless, left alone. Alfheim and Fixeroo have no config files and run on defaults.

## LoliASM and VintageFix

- VintageFix `mixin.searchtree=false`. LoliASM `replaceSearchTreeWithJEISearching=true` (its `mixins.searchtree_vanilla` patches `Minecraft.populateSearchTreeManager`) already swaps the creative search for HEI's, so VintageFix's lazy SearchTree did nothing useful. VintageFix doesn't auto-disable any feature when LoliASM is present.

## StellarCore extras

- `VanillaModelDiskCache` writes a machine-specific `config/stellarcore_vanilla_model_cache_<fingerprint>.dat` (~1.7 MB, 16k entries) on first launch. It is gitignored (with `config/cleanroom_load_timings.dat`); never tick it in the CurseForge export. It logged three fingerprints in one startup and saved under the last; a cache hit on the next launch is not yet confirmed.
- `HudCaching` stays off: Gnetum already batches HUD rendering and patches the same `GuiIngameForge`, `Framebuffer` and `GlStateManager` methods.
- Universal Tweaks and StellarCore share no mod-integration target classes, and on shared vanilla classes they patch different methods, so their fixes coexist. StellarCore's comments say its Thermal satchel fix and BetterChat compat probably duplicate UT; harmless.

## Re-audit

Facts come from jar decompiles and `logs/debug.log` mixin lines (`Mixing X from Y into Z`). Re-check after updating any of these mods.
