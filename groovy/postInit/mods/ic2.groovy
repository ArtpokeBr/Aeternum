
//Ender Dust Unification ==========================================================================

mods.jei.ingredient.hide(item('ic2:dust', 31))
// crafting.removeByOutput(item('ic2:dust', 31))
ore_dict.remove('dustEnderPearl', item('ic2:dust', 31))

mods.thermalexpansion.pulverizer.removeByOutput(item('ic2:dust', 31))
mods.appliedenergistics2.grinder.removeByOutput(item('ic2:dust', 31))

mods.appliedenergistics2.grinder.recipeBuilder()
    .input(ore('enderpearl'))
    .output(item('railcraft:dust', 6))
    .turns(10)
.register()

mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(ore('enderpearl'))
    .output(item('railcraft:dust', 6))
    .energy(2000)
.register()

//=================================================================================================
