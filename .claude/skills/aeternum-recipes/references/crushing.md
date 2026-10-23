# Crushing machines

Every machine in the pack that crushes, grinds or mills an item into another, with its API. A recipe added "to every crusher" covers every row of the first two tables. Working examples:

- `groovy/postInit/mods/thaumcraft.groovy` and `scripts/mods/thaumcraft.zs` ("Crystal Cluster Crushing"): one plain item -> item recipe in every automatable crusher, NBT output included.
- `groovy/postInit/mods/projectred.groovy` ("Electrotine Ore Processing"): ore recipes with byproducts and chances, plus the `removeByInput` quirks.

## GroovyScript (automatable)

| Machine | API | Notes |
|---|---|---|
| Thermal Pulverizer | `mods.thermalexpansion.pulverizer.recipeBuilder()` `.input .output(main, secondary) .chance(%) .energy` | Secondary chance is an int percent. |
| Mekanism Crusher | `mods.mekanism.crusher.recipeBuilder()` `.input .output` | Single output. Ore doubling/tripling is the Enrichment Chamber (`mods.mekanism.enrichment_chamber`), not the Crusher. |
| IE Crusher | `mods.immersiveengineering.crusher.recipeBuilder()` `.input .output .secondaryOutput(stack, float) .energy` | Multiblock. |
| Actually Additions Crusher | `mods.actuallyadditions.crusher.recipeBuilder()` `.input .output` | `removeByInput` casts to ItemStack: no `ore()`. |
| Extra Utilities 2 Crusher | `mods.extrautils2.crusher.recipeBuilder()` `.input .output .energy .time` | |
| Ender IO SAG Mill | `mods.enderio.sag_mill.recipeBuilder()` `.input .output(stack[, chance]) .energy .tierAny() .bonusTypeMultiply()/Chance()/None()` | Up to 4 outputs. Grinding balls scale outputs unless `bonusTypeNone()`; use it where extra output would be a dupe. Stock recipes: PDFs in `config/enderio/recipes/`. |
| IC2 Macerator | `mods.ic2.macerator.add(output, input)` | **Output first**, unlike `mods.ic2.extractor.add(input, output)`. |
| AE2 Quartz Grindstone | `mods.appliedenergistics2.grinder.recipeBuilder()` `.input .output(up to 3) .turns .chance1/.chance2` | Hand-cranked or Wooden Gear/crank automation. |
| Astral Sorcery Grindstone | `mods.astralsorcery.grindstone.recipeBuilder()` `.input .output .weight .secondaryChance(0-1)` | Manual; its generic ore loop gives most ores only 1 dust. |
| Integrated Dynamics Squeezer | `mods.integrateddynamics.squeezer.recipeBuilder()` `.input .output` | Jump-powered; the pack uses it as a crusher (dyes, Ender Eye Dust). |
| ID Mechanical Squeezer | `mods.integrateddynamics.mechanical_squeezer.recipeBuilder()` `.input .output(stack[, chance])` | Powered. |

## CraftTweaker (no GroovyScript compat)

| Machine | API | Notes |
|---|---|---|
| NuclearCraft Manufactory | `mods.nuclearcraft.Manufactory.addRecipe(in, out, timeMult, powerMult, radiation)` | Direct item ids only, see `nuclearcraft.md`. |
| NuclearCraft Rock Crusher | `mods.nuclearcraft.RockCrusher.addRecipe(in, out1, out2, out3, time, power, radiation)` | Stone into several dusts; not used for single-output crushing yet. |
| Railcraft Rock Crusher | `moretweaker.railcraft.RockCrusher.add(in, [outputs], [chances 0-1], ticks)` | MoreTweaker. Multiblock. |
| Rockhounding Mineral Sizer | `mods.rockhounding_chemistry.MineralSizer.add(in, out)` or `.add(in, [outputs], [comminution levels])` | Not used by the pack yet; output depends on the gear level, check before using the array form. |

## Manual or ritual (not automation)

- Roots Pestle & Mortar: `mods.roots.mortar.recipeBuilder()` (commented example in `groovy/postInit/mods/botania.groovy`).
- Atum Quern: `mods.atum.quern.recipeBuilder()` `.input .output .rotations(n)`.
- Blood Magic Alchemy Table with Cutting Fluid: ore doubling, `mods.bloodmagic.alchemy_table` (see the Electrotine block).
- IE Engineer's Hammer and the like are crafting-table recipes.

GroovyScript 1.4.3 also has crushers for mods not in the pack (Better With Mods, Magneticraft, TechReborn, Horse Power, ...); ignore those.

## Thaumcraft Vis Crystals as output

- GroovyScript: `crystal('aer')` is the Vis Crystal item stack. CraftTweaker: `<thaumcraft:crystal_essence>.withTag({Aspects: [{key: "aer", amount: 1}]})`.
- Crystal cluster blocks are `thaumcraft:crystal_<aspect>` (`aer`, `aqua`, `ignis`, `ordo`, `perditio`, `terra`, `vitium`). Broken, they drop growth + 1 crystals (meta & 3), so recipes take meta 0 only.
