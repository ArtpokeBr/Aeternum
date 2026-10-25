# Crushing machines

Every machine in the pack that crushes, grinds or mills an item into another, with its API. A recipe added "to every crusher" covers every row of the first two tables. Working examples:

- `groovy/postInit/mods/thaumcraft.groovy` and `scripts/mods/thaumcraft.zs` ("Crystal Cluster Crushing"): one plain item -> item recipe in every automatable crusher, NBT output included.
- `groovy/postInit/mods/projectred.groovy` ("Electrotine Ore Processing"): ore recipes with byproducts and chances, plus the `removeByInput` quirks.
- `groovy/postInit/mods/thaumcraft.groovy` and `scripts/mods/thaumcraft.zs` ("Cinnabar Ore Processing"): an ore with no dust gets a custom one (`aeternum:quicksilver_dust`) at Iron Ore's yields in every machine, plus dust smelting, the Mekanism Combiner back to ore, and ingot-style item -> dust recipes.

## Iron Ore yields (metal-ore parity)

Checked in the jars and the SAG Mill PDFs. Copy these when giving a modded ore "normal" doubling:

| Machine | Ore | Ingot -> dust |
|---|---|---|
| Thermal Pulverizer | 2 dust + 10% related, 4000 RF | 1 dust, 2000 RF |
| Induction Smelter | + Sand: 2 ingots + 5% Rich Slag; + Rich Slag: 3 + 75% Slag; + Cinnabar: 3 + 100% related (75% Rich Slag if none; the Cinnabar Ore recipe uses 75% Rich Slag); 4000 RF | dust + Sand: 1 ingot + 25% Slag, 1000 RF |
| IE Crusher | 2 dust + secondary, 6000 RF | 3600 RF |
| SAG Mill | 2 dust + 5% and 10% byproducts + 15% Cobblestone, 3600 RF | 2400 RF, `bonus="none"` |
| Mekanism | Enrichment 2 dust; Combiner 8 dust + Cobblestone -> ore | Crusher |

## Ore loops miss ores with no matching dust

Every generic ore loop (Thermal, IE, Mekanism, Actually Additions, XU2, SAG Mill, Blood Magic Cutting Fluid, Astral Sorcery Grindstone, NC Manufactory, IC2) pairs `ore<X>` with `dust<X>`/`ingot<X>`/`gem<X>`. An ore without those, like Thaumcraft's `oreCinnabar`, gets no recipe anywhere, so write each machine's recipe explicitly. When the pack adds a dust for such an ore, name its ore-dict entry after the product (`dustQuicksilver`), not `dust<OreName>`, or the loops add flat recipes of their own next to the explicit ones.

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
