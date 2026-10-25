//Ender Dust Unification ==========================================================================

mods.jei.ingredient.hide(item('appliedenergistics2:material', 46))
ore_dict.remove('dustEnderPearl', item('appliedenergistics2:material', 46))
ore_dict.remove('dustEnder', item('appliedenergistics2:material', 46))

//=================================================================================================

//Quartz Dust Unification =========================================================================

mods.jei.ingredient.hide(item('appliedenergistics2:material', 3))
ore_dict.remove('dustNetherQuartz', item('appliedenergistics2:material', 3))
ore_dict.remove('dustQuartz', item('appliedenergistics2:material', 3))

//=================================================================================================

//Silicon Unification =============================================================================

mods.jei.ingredient.hide(item('appliedenergistics2:material', 5))
ore_dict.remove('itemSilicon', item('appliedenergistics2:material', 5))

//=================================================================================================

//In-World Fluix Crystals =========================================================================
//AE2's own handler is off (InWorldFluix=false in AppliedEnergistics2.cfg); these show in JEI.
//Like AE2's, the water source is not consumed.

inWorldCrafting.fluidToItem.recipeBuilder()
        .fluidInput(fluid('water'), 0.0f)
        .input(item('appliedenergistics2:material', 1), item('minecraft:redstone'), item('minecraft:quartz'))
        .output(item('appliedenergistics2:material', 7) * 2)
        .register()

inWorldCrafting.fluidToItem.recipeBuilder()
        .fluidInput(fluid('water'), 0.0f)
        .input(item('appliedenergistics2:material', 1), ore('dustElectrotine'), item('minecraft:quartz'))
        .output(item('appliedenergistics2:material', 7) * 4)
        .register()

//=================================================================================================
