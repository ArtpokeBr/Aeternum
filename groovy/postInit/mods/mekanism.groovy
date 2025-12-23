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