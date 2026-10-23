# Alloying methods

To add an alloy everywhere, cover every row. Worked example: `groovy/postInit/mods/projectred.groovy` (Red Alloy / Electrotine Alloy loop) + `scripts/mods/projectred.zs` (NuclearCraft part).

| Machine | Language | API | Notes |
|---|---|---|---|
| IE Alloy Kiln | Groovy | `mods.immersiveengineering.alloy_kiln.recipeBuilder().input(a, b).output(x).time(200)` | Exactly 2 inputs, counts allowed (`ore('dustRedstone') * 4`). IE's own ore-dict alloys add both `ingot` and `dust` base variants. |
| IE Arc Furnace | Groovy | `mods.immersiveengineering.arc_furnace.recipeBuilder().mainInput(a).input(additives...).output(x).time(100).energyPerTick(512).alloying()` | `.alloying()` sets IE's "Alloying" recipe type (what IE uses for its own alloys); `.slag(stack)` optional. |
| Ender IO Alloy Smelter | Groovy | `mods.enderio.alloy_smelter.recipeBuilder().input(a, b, c).output(x).energy(2000).tierAny()` | Up to 3 inputs; tier `tierSimple/Normal/Enhanced/Any`. |
| Thermal Induction Smelter | Groovy | `mods.thermalexpansion.smelter.recipeBuilder().input(a, b).output(x).energy(4000)` | 2 inputs; default energy 4000. Registers one recipe per matching-stack pair of an ore input. |
| Mekanism Metallurgic Infuser | Groovy | `mods.mekanism.metallurgic_infuser.recipeBuilder().input(a).infuse('REDSTONE').amount(40).output(x)` | 1 item + an infuse type (upper-case name, e.g. `REDSTONE`, `CARBON`, `ELECTROTINE`); 1 Redstone dust = 10 infuse. Add a source item with `mods.mekanism.infusion.add('TYPE', amount, ingredient)`. |
| NuclearCraft Alloy Furnace | CraftTweaker | `mods.nuclearcraft.AlloyFurnace.addRecipe(in1, in2, out, timeMult, powerMult, radiation)` | No GroovyScript compat. Ore-dict inputs work here (the ore-dict bug is Manufactory-only). |
| Rockhounding Metal Alloyer | CraftTweaker | `mods.rockhounding_chemistry.MetalAlloyer.add(["dustA", "dustB"], [80, 20], <out>)` | Percentage-based dust compositions; not used for ProjectRed alloys (user's call). |
| Tinkers' Smeltery | Groovy | `mods.tconstruct.alloying` | Fluid-only: needs a molten fluid for each input and the output, so it can't make alloys with no fluid (e.g. Red Alloy). |
| Vanilla furnace | Groovy | `furnace.recipeBuilder()` | ProjectRed's own route: Red Iron Compound (Iron Ingot + 8 Redstone) and Electrotine Iron Compound (Iron Ingot + 8 Electrotine) smelt into the alloys. |

No other item alloyer is installed: Embers' Mixer Centrifuge mixes fluids only, and IC2 Experimental has no alloy smelter.

## New Mekanism infuse types

Don't use `mods.mekanism.infusion.addType()`: it calls `Minecraft.getMinecraft()` and crashes the dedicated server. Register with `InfuseRegistry.registerInfuseType(new InfuseType(...))` in `groovy/preInit/mekanism.groovy` (Mekanism's stitch handler sets the icon) and add an `infuse.<name>` lang key in `groovy/assets/aeternum/lang/en_us.lang`.

Match Mekanism's own source values so a new type feels like the built-in ones. Redstone: dust 10, Compressed Redstone 80 (Enrichment Chamber, 1 dust -> 1), block 90; Carbon: coal 10, charcoal 20, Compressed Carbon 80, coal block 90 (`mekanism.common.Mekanism`, `registerInfuseObject` calls). The `ELECTROTINE` type follows Redstone: Electrotine Dust 10, `aeternum:compressed_electrotine` 80 (made from 1 dust in the Enrichment Chamber), `blockElectrotine` 90, all in `groovy/postInit/mods/projectred.groovy`. Its icon is `groovy/assets/aeternum/textures/blocks/infuse/electrotine.png`, Mekanism's `infuse/redstone.png` hue-shifted to Electrotine blue (#0e73aa); the compressed item's texture is `compressedredstone.png` shifted the same way.
