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
  - `scripts/mods/`, `scripts/loot/`, `scripts/nc_script_addons/` — the pack also has the `MoreTweaker` mod installed for extra CraftTweaker compat; see [its wiki](https://bitbucket.org/noeppi_noeppi/moretweaker/wiki/Home) for syntax not covered by the main CraftTweaker docs. `Roids Tweaker` is another mod providing extra CraftTweaker compat; see [its wiki](https://github.com/roidrole/Roids-Tweaker/wiki) for reference
  - `groovy/preInit/`, `groovy/postInit/`
- [patchouli_books/](patchouli_books/) — custom in-game Patchouli guidebook content
- [resourcepacks/](resourcepacks/), [shaderpacks/](shaderpacks/) — client assets
- [saves/](saves/) — local worlds (not relevant to pack development)
- `minecraftinstance.json` — CurseForge/Overwolf app instance metadata (Forge version, mod list)
- [metadata/changelog.md](metadata/changelog.md) — pack changelog shown in-game via Ender Modpack Tweaks; update it as features/changes are approved and implemented
- `metadata/pack_version.txt` — current pack version string
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
- `UniDict` is the mod used for item/ore-dict unification across mods. For any unification request, check and tweak its config first; if UniDict can't handle the case, fall back to a Groovy/CraftTweaker script instead.
- `Dropt` is the mod used to tweak block drops; see its [docs](https://dropt.readthedocs.io/en/latest/) for syntax reference.
- Aeternum is a **kitchen-sink** pack, not an expert pack — chill experience, mods talking to each other. Don't port aggressive balancing/progression-gating/recipe-replacement-for-difficulty content from expert packs; only port genuine cross-mod integrations or bug/exploit fixes.
- [Enigmatica2Expert-Extended](https://github.com/Krutoy242/Enigmatica2Expert-Extended) (E2EE) is a useful reference for cross-mod recipes/mixins/JEI tabs, but most of its content is expert-pack balance gating (skip) or tied to E2EE-exclusive addon mods/data (FluidIntegrationTweaker, PotionCore, custom `scripts.mods.*` structures) that don't exist here — always verify the referenced mod/item/oreDict tag actually exists in this pack's installed jars before porting; don't trust E2EE's config values blindly (e.g. some mod "default" cfgs, like Astral Sorcery's `mineralis_ritual.cfg`, are static templates never validated against what's actually installed — `javap`/`unzip` the real jar to confirm a tag/item exists before using it in a script).

### Biomes O'Plenty config gotcha

`config/biomesoplenty/biomes/defaults/**` is **regenerated and deleted by BOP itself on every boot** — edits there are silently discarded. The real, persistent per-biome override path is `config/biomesoplenty/biomes/<namespace>/<biome>.json` (same relative structure, just without the `defaults` segment), as a partial JSON (only the keys you want to change — BOPConfig falls back to built-in defaults for anything omitted).

### Requious Frakto (custom JEI tabs)

Each block type's variants are defined in `config/requious/<machine>.json` (e.g. `assembly.json`) by `resourceName`; CraftTweaker scripts reference them via `<assembly:resourceName>` and the `mods.requious.Assembly`/`AssemblyRecipe`/`SlotVisual` API (`addJEICatalyst`, `addJEIRecipe`, `setJEIItemSlot`/`setJEIFluidSlot`/`setJEIDurationSlot`). A shared helper library lives at `scripts/jei/requious.zs` — cross-script calls like `scripts.jei.requious.add(...)` require mirroring that exact folder path (CraftTweaker resolves `scripts.X.Y.Z` calls by file path, dots-for-slashes). JEI tab titles come from lang key `requious.jei.recipe.<resourceName>` (lang keys are global across all loaded lang files regardless of asset folder/modid, so these can live anywhere, e.g. `groovy/assets/aeternum/lang/en_us.lang`). Ported tabs live under `scripts/jei/mod/<sourceMod>.zs`.
- `Roids Tweaker` (RoidTweaker) is required for the Garden Cloche liquid-fertilizer tab (`mods.roidtweaker.immersiveengineering.GardenCloche`).

### CraftTweaker-coded mixins

Java-level bug/performance fixes (not just recipes) can be written as CraftTweaker scripts via the ZenUtils mixin loader — `scripts/mods/mixins/<modid>.zs` files starting with `#modloaded <modid>` + `#loader mixin`, then `#mixin {targets: "fully.qualified.ClassName"} zenClass MixinName { ... }` blocks using `#mixin Overwrite`/`Redirect`/`Inject`/`ModifyConstant` annotations (SRG method names in the `target:`/`method:` bytecode descriptors, MCP/friendly names in the actual function body). Used so far for EnderIO/Forestry/ThermalDynamics/VillageNames perf fixes, Immersive Engineering's slow Arc Furnace recipe scan + villager trade gen + Engineer's Manual recipe-grid previews, and a TheOneProbe (TheOneSmeagle fork) integration for Rustic's Apiary (`scripts/mods/mixins/rustic.zs`). **Always decompile the actual installed jar with `javap`** to confirm field/method names before writing one — a close copy of an E2EE mixin broke once (`scripts/mods/mixins/chisel.zs`, since reverted) because the assumed field didn't exist in this build.

**Adding a new interface to a target class:** ZenScript class syntax uses `extends`, not `implements`, for both superclasses *and* interfaces — `zenClass MixinFoo extends SomeInterface { ... }` (the compiler classifies each `extends`-listed native type as superclass vs. interface via `isInterface()`). `#mixin {targets: "..."}` only supports **one** target class per `zenClass` block — you cannot shadow a field on class A and merge an interface into class B in the same block; that needs two separate mixins (or, better, see below).

**Avoid Shadow-field-+-getter for cross-class TE/Block access — prefer the public Forge Capability API.** The natural approach for a Block-level provider (e.g. TheOneProbe's `IProbeInfoAccessor`, detected only on the `Block` instance, not the TileEntity) that needs a TileEntity's private inventory field is: mixin `#mixin Shadow` the private field on the TE class, add a small getter method, then call that getter from the Block's mixin. **This reliably crashes with `VerifyError: Bad type on operand stack`** — ZenUtils' reflection-based native type resolution can't see a method that another, separately-compiled mixin class injects (chicken-and-egg: the ZenScript script compiles before Sponge Mixin transforms the real class), so the call site falls back to dynamic typing and emits broken bytecode for the later `as Type` cast / overload resolution. **Instead, decompile the TE with `javap` to check if it exposes the same data through `hasCapability`/`getCapability`** (very common for `IItemHandler` inventories, keyed by `EnumFacing` side) — that's a real, fully-reflectable native API call ZenUtils binds correctly, and skips the Shadow/getter dance entirely. This is how the Rustic Apiary TOP integration was eventually written: `tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN) as IItemHandler` instead of shadowing `honeyCombItemStackHandler`.

**`val x as Type = expr;` does NOT pin the static type** for later member resolution in this ZenUtils build — cast the right-hand expression instead: `val x = expr as Type;`. Even then, native-typed locals derived from a generic/erased call (e.g. `IItemHandler.getStackInSlot(int)`) can still misresolve **overloaded** method calls afterward (seen: `ItemStack.isEmpty()` failing with "matched method isEmpty, but the argument types do not match" — the dynamic dispatch picks the wrong overload across unrelated types). When that happens, swap the ambiguous call for an unambiguous one (e.g. `stack.getCount() > 0` instead of `!stack.isEmpty()`).

### GroovyScript asset overlay

`groovy/assets/<modid>/{lang,models,textures}/...` ships custom lang/model/texture resources without a separate resourcepack (used for `aeternum:copper_chipset`'s lang/model and the Requious JEI tab titles). Lang keys merge globally regardless of which folder/modid they're declared under.
