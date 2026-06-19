// mods_loaded: ic2

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

//Making Rubber Wood Great Again
ore_dict.add('logWood', item('ic2:rubber_wood'))

// More ways to make Ender Eye Dust ===============================================================

mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(ore('pearlEnderEye'))
    .output(item('ic2:dust', 32))
    .energy(2000)
.register()

mods.enderio.sag_mill.recipeBuilder()
    .input(ore('pearlEnderEye'))
    .output(item('ic2:dust', 32))
    .tierAny()
.register()

mods.integrateddynamics.squeezer.recipeBuilder()
    .input(ore('pearlEnderEye'))
    .output(item('ic2:dust', 32))
.register()

mods.integrateddynamics.mechanical_squeezer.recipeBuilder()
    .input(ore('pearlEnderEye'))
    .output(item('ic2:dust', 32))
.register()

mods.actuallyadditions.crusher.recipeBuilder()
    .input(ore('pearlEnderEye'))
    .output(item('ic2:dust', 32))
.register()

// ================================================================================================