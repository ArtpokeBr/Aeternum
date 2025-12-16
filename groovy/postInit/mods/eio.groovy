
//Ender Dust Unification ==========================================================================

mods.enderio.sag_mill.removeByInput(item('railcraft:dust', 6))
mods.thermalexpansion.factorizer.removeByOutput(item('enderio:item_material', 28))
mods.thermalexpansion.factorizer.removeByInput(item('enderio:item_material', 28))
mods.jei.ingredient.hide(item('enderio:item_material', 28))
mods.enderio.alloy_smelter.remove(item('minecraft:ender_pearl'))
mods.tconstruct.melting.removeByInput(item('enderio:item_material', 28))
crafting.removeByOutput(item('railcraft:dust', 6))

//Readding Enderios Recipe
crafting.removeByOutput(item('enderio:item_ender_food'))
crafting.shapedBuilder()
    .output(item('enderio:item_ender_food'))
    .shape('BM',
           'WE')
    .key([B: item('minecraft:bowl'), M: fluid('milk') * 1000, W: ore('cropWheat'), E: ore('dustEnderPearl')])
.register()

//=================================================================================================

//Silicon Unification =============================================================================

mods.jei.ingredient.hide(item('enderio:item_material', 5))
ore_dict.remove('itemSilicon', item('enderio:item_material', 5))

//=================================================================================================