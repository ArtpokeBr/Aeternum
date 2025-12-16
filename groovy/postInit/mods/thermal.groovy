//Coke Unification ================================================================================
mods.thermalexpansion.furnace_pyrolysis.recipeBuilder()
    .input(item('minecraft:coal'))
    .output(item('railcraft:fuel_coke'))
    .creosote(250)
.register()

crafting.removeByOutput(item('thermalfoundation:material', 802))
crafting.removeByOutput(item('thermalfoundation:storage_resource', 1))
mods.jei.ingredient.hide(item('thermalfoundation:material', 802))
mods.thermalexpansion.factorizer.removeByOutput(item('thermalfoundation:storage_resource', 1))
mods.thermalexpansion.factorizer.recipeBuilder()
    .input(item('railcraft:fuel_coke') * 9)
    .output(item('railcraft:coke_block'))
    .combine()
.register()

//=================================================================================================
