//Mekanism Salt Unification =======================================================================
//Dropt is being used to replace the block drops, but there are recipes that need adjustments still

crafting.removeByOutput(item('mekanism:saltblock'))
crafting.shapedBuilder()
    .output(item('mekanism:saltblock'))
    .shape('SS',
           'SS')
    .key([S: ore('dustSalt')])
.register()
crafting.shapelessBuilder()
    .input([ore('blockSalt')])
    .output(item('rockhounding_chemistry:chemical_items', 3) * 4)
.register()

mods.mekanism.chemical_oxidizer.removeByInput(item('mekanism:salt'))
mods.mekanism.chemical_oxidizer.removeByInput(item('harvestcraft:saltitem'))
mods.mekanism.chemical_oxidizer.recipeBuilder()
    .input(ore('dustSalt'))
    .gasOutput(gas('brine') * 15)
.register()

//=================================================================================================

//Mekanism Osmium Unification =====================================================================

//Mekanism Sensor Kit
crafting.removeByOutput(item('energycontrol:item_kit', 16))
crafting.shapedBuilder()
    .output(item('energycontrol:item_kit', 16))
    .shape('OT',
           'PC')
    .key([O: ore('ingotOsmium'), P: ore('paper'), C: ore('dyeGreen'), T: item('energycontrol:item_component', 3)])
.register()

//=================================================================================================
