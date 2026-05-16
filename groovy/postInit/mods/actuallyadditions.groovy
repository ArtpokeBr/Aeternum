//Quartz Dust Unification =========================================================================

mods.jei.ingredient.hide(item('actuallyadditions:item_dust', 5))
ore_dict.remove('dustNetherQuartz', item('actuallyadditions:item_dust', 5))
ore_dict.remove('dustQuartz', item('actuallyadditions:item_dust', 5))

//=================================================================================================

//Diamond Dust Unification ========================================================================

mods.jei.ingredient.hide(item('actuallyadditions:item_dust', 2))
ore_dict.remove('dustDiamond', item('actuallyadditions:item_dust', 2))

//=================================================================================================

//Coffee to Coffee Seeds.
crafting.shapelessBuilder()
    .input([item('actuallyadditions:item_coffee_beans')])
    .output(item('actuallyadditions:item_coffee_seed'))
.register()


