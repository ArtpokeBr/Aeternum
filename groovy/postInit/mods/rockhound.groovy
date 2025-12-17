//Cobalt Unification ==============================================================================

furnace.removeByOutput(item('tconstruct:ingots'))
mods.thermalexpansion.furnace.removeByOutput(item('tconstruct:ingots'))
crafting.removeByOutput(item('tconstruct:ingots'))

crafting.shapedBuilder()
    .output(item('rockhounding_chemistry:metal_items', 10))
    .shape('CCC',
           'CCC',
           'CCC')
    .key([C: ore('nuggetCobalt')])
.register()

furnace.recipeBuilder()
    .input(ore('dustCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10))
    .exp(0.5)
.register()
furnace.recipeBuilder()
    .input(ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10))
    .exp(0.5)
.register()

mods.thermalexpansion.furnace.recipeBuilder()
    .input(ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10))
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('sand'), ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 2, item('thermalfoundation:material', 865))
    .chance(5)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('sand'), ore('dustCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 1, item('thermalfoundation:material', 864))
    .chance(25)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(item('thermalfoundation:material', 865), ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 3, item('thermalfoundation:material', 864))
    .chance(75)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(item('thermalfoundation:material', 866), ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 3, item('thermalfoundation:material', 865))
    .chance(75)
    .energy(4000)
.register()

//=================================================================================================