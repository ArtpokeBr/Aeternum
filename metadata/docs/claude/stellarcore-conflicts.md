# StellarCore option choices (`config/stellar_core.cfg`)

Why StellarCore options are on or off, given the other optimisation mods. Checked against StellarCore 1.6.5, LoliASM 5.34, VintageFix and Universal Tweaks (UT).

**Policy:** when StellarCore and LoliASM, VintageFix or UT optimise the same thing, keep the other mod's version and turn StellarCore's off.

LoliASM is `mods/chibi-5.34.jar` (modid `loliasm`), easy to miss in a grep for "loli". StellarCore detects it via class `zone.rong.loliasm.core.LoliLoadingPlugin`.

## Off because of a conflict

- `ResourceLocationCanonicalization`: LoliASM `resourceLocationCanonicalization` ASM-rewrites the same `toLowerCase` call in `ResourceLocation.<init>` before mixins apply, so StellarCore's `@Redirect` matched nothing.
- `NBTTagImprovements`: conflicts with LoliASM `optimizeNBTTagCompoundBackingMap` / `nbtBackingMapStringCanonicalization`. StellarCore auto-disabled it with a warning banner in `latest.log`; it is off explicitly now.
- `StateMapperBaseImprovements`: LoliASM `optimizeRegistries` swaps the map in `StateMapperBase.<init>`; StellarCore replaced it again afterwards.
- `ASMModParserImprovements`: `ASMModParser` is already loaded (MixinBooter patches it) before StellarCore's mixin can apply, so it logged "Critical problem … loaded too early" every launch and never ran.
- `PropertyEnumHashCodeCache`: VintageFix `mixin.blockstates` and StellarCore both `@Overwrite` `PropertyEnum.hashCode()`.
- `ResourceExistStateCache`: VintageFix `mixin.resourcepacks` caches the same lookups (`AbstractResourcePack`, `DefaultResourcePack`, `FileResourcePack.hasResourceName`, `ResourceLoader`). Options that do nothing without it are off too: `ArchiveResourcePackIndex`, `DirectoryResourcePackIndex`, libnine `L9ModelsIsOfTypeCache`, tconstruct `ParallelMaterialTextureGen`.
- `ChunkTileEntityMapImprovements`: UT's "Tile Entity Map" (`LINKED_HASHMAP`) owns that field. Must stay off.
- `ParallelModelLoader`, `StitcherCache`: incompatible with VintageFix DynamicResources. Must stay off.
- `AsyncItemStackCapabilityInit`: conflicts with LoliASM `delayItemStackCapabilityInit`. Must stay off.
- nuclearcraftoverhauled `BasicRecipeSearchImprovements`: it only speeds up NuclearCraft's simple slot check, which runs when `smart_processor_input=false`. The pack keeps `smart_processor_input=true` (`config/nuclearcraft.cfg`) so pipes can't jam processors with mismatched inputs, so the option would never run.

## Checked and left on

- `OreDictionaryPrimitiveCollections`: UniDict/WanionLib, CraftTweaker and GroovyScript reach OreDictionary's fields only through `Map`/`List` interfaces, and GroovyScript's `List<Integer>.remove(Integer)` works on fastutil `IntList`.
- ChunkTileEntity Cache/Queue, `EntityTrackerImprovements`, WorldServer/World tweaks, Vanilla/OBJ model caches: share classes with VintageFix or UT but patch different methods or fields.
- `BoundingBoxIntersectCheck` is skipped automatically on Cleanroom.

## Open

- JourneyMap `MapperOptimizations` only loads on JourneyMap 6.0+; the pack has 5.7.1, so it does nothing.

## Re-audit

Run `moddig mixins <jar> --json` for every jar and intersect the targets with StellarCore's. UT and VintageFix register mixins in code, so take theirs from the `Mixing X from Y into Z` lines in `logs/debug.log`. Re-run after updating StellarCore, LoliASM, VintageFix or UT, since new StellarCore versions add options (1.6.5 added several).
