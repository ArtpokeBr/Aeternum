
//Ender Dust Unification ==========================================================================

mods.enderio.sag_mill.removeByInput(item('railcraft:dust', 6))
mods.thermalexpansion.factorizer.removeByOutput(item('enderio:item_material', 28))
mods.thermalexpansion.factorizer.removeByInput(item('enderio:item_material', 28))
mods.jei.ingredient.hide(item('enderio:item_material', 28))
mods.enderio.alloy_smelter.remove(item('minecraft:ender_pearl'))
mods.tconstruct.melting.removeByInput(item('enderio:item_material', 28))
//crafting.removeByOutput(item('railcraft:dust', 6))

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

//Lapis Dust Unification ==========================================================================

mods.jei.ingredient.hide(item('enderio:item_material', 32))
ore_dict.remove('dustLapis', item('enderio:item_material', 32))

//=================================================================================================

//Tin Dust Unification ============================================================================

mods.jei.ingredient.hide(item('enderio:item_material', 27))
ore_dict.remove('dustTin', item('enderio:item_material', 27))

//=================================================================================================

//Copper Dust Unification =========================================================================

mods.jei.ingredient.hide(item('enderio:item_material', 26))
ore_dict.remove('dustCopper', item('enderio:item_material', 26))

//=================================================================================================

//Ender Utilities Ender Alloy Smelting ============================================================

crafting.removeByOutput(item('enderutilities:enderpart', 0))
mods.enderio.alloy_smelter.recipeBuilder()
    .input(ore('ingotIron') * 4, item('minecraft:ender_pearl'))
    .output(item('enderutilities:enderpart', 0) * 4)
    .energy(3200)
    .tierSimple()
.register()

crafting.removeByOutput(item('enderutilities:enderpart', 1))
mods.enderio.alloy_smelter.recipeBuilder()
    .input(ore('ingotGold') * 4, item('minecraft:ender_pearl'))
    .output(item('enderutilities:enderpart', 1) * 4)
    .energy(6400)
    .tierNormal()
.register()

crafting.removeByOutput(item('enderutilities:enderpart', 2))
mods.enderio.alloy_smelter.recipeBuilder()
    .input(ore('ingotEnderium') * 4, ore('dustEnderEye'))
    .output(item('enderutilities:enderpart', 2) * 4)
    .energy(12800)
    .tierEnhanced()
.register()

//=================================================================================================