# Item + fluid -> item machines

Machines that take one item and a fluid and give an item. A recipe "ported to every infuser" picks rows here. Working example: `scripts/mods/thaumcraft.zs` ("Cinnabar Ore Processing") ports Thermal's Transposer recipe, Cinnabar Ore + 200 mB Cryotheum -> 4 Thermal Cinnabar, to the NC Infuser.

| Machine | API | Notes |
|---|---|---|
| Thermal Transposer (fill) | `mods.thermalexpansion.transposer_fill` | `transposer_extract` is the reverse. |
| NuclearCraft Infuser | `mods.nuclearcraft.Infuser.addRecipe(item, fluid, out[, time, power, rad])` (CraftTweaker) | Ore-dict inputs work here (only the Manufactory rejects them). Example with 3 args in `scripts/mods/ic2.zs`. |
| IE Bottling Machine | `mods.immersiveengineering.bottling_machine` | |
| Tinkers' Casting | `mods.tconstruct.casting_table`, `mods.tconstruct.casting_basin` | Item is the cast. |
| Integrated Dynamics | `mods.integrateddynamics.drying_basin`, `mods.integrateddynamics.mechanical_drying_basin` | |

Not this kind of machine: Mekanism's Injection, Purification and Dissolution chambers and Chemical Infuser use **gases**, not fluids. Ender IO and Industrial Foregoing have none. Blood Magic's Alchemy Table takes items only.

## Fluids

- `<liquid:cryotheum>` is Thermal's. NuclearCraft registers its own `cryotheum` only when Thermal's is missing (`NCFluids.registerCoFHAlt()`), so the name is the same either way.
- Mercury/quicksilver: QMD registers `mercury` and `hot_mercury` (and `qmd:ingot2:2`, `ingotMercury`, which melts to 144 mB). Thaumic Wonders registers `fluid_quicksilver` (bucket <-> 8 Quicksilver by crafting).
