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

//Sawdust Adjustments =============================================================================

//Yeeting Pulverizer Sawdust Recipes cause some where bugged.
mods.thermalexpansion.pulverizer.removeByOutput(item('thermalfoundation:material', 800))

//=================================================================================================

//Converting BOP Honey into Forestry Honey =====================================================

mods.thermalexpansion.refinery.recipeBuilder()
    .fluidInput(fluid('honey') * 250)
    .fluidOutput(fluid('for.honey') * 250)
    .output(item('forestry:honey_drop'))
    .chance(5)
    .energy(5000)
.register()

// ================================================================================================

//Rubber Wood Direcly in the Sawmill
mods.thermalexpansion.tapper.addItem(item('ic2:rubber_wood'), fluid('resin') * 40)
mods.thermalexpansion.sawmill.recipeBuilder()
    .input(item('ic2:rubber_wood'))
    .output(item('minecraft:planks', 3) * 6, item('thermalfoundation:material', 800))
    .chance(100)
    .energy(1000)
.register()

//Sesame Seeds itself into Sesame Oil
mods.thermalexpansion.centrifuge.recipeBuilder()
    .input(item('harvestcraft:sesameseedsitem'))
    .output(item('harvestcraft:sesameoilitem'), item('harvestcraft:grainbaititem'))
    .chance(100, 100)
    .energy(4000)
.register()