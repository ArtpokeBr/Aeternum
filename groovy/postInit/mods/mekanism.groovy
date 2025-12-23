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

//Mekanism Diamond Dust Unification ===============================================================

mods.jei.ingredient.hide(item('mekanism:otherdust'))
ore_dict.remove('dustDiamond', item('mekanism:otherdust'))

//=================================================================================================

//Mekanism Osmium Unification =====================================================================

//Tweaking Bedrock Miner Recipe as it should be the initial way to get Osmium.
crafting.removeByOutput(item('bedrockores:bedrock_miner'))
crafting.shapedBuilder()
    .output(item('bedrockores:bedrock_miner'))
    .shape('PLP',
           'BCB',
           'PDP')
    .key([
        C: item('enderio:item_material', 1), 
        B: ore('blockRedstone'), 
        D: item('thermalfoundation:material', 656), 
        L: item('actuallyadditions:item_misc', 16),
        P: ore('plateVanadium')
    ])
.register()

//Mekanism Sensor Kit
crafting.removeByOutput(item('energycontrol:item_kit', 16))
crafting.shapedBuilder()
    .output(item('energycontrol:item_kit', 16))
    .shape('OT',
           'PC')
    .key([O: ore('ingotOsmium'), P: ore('paper'), C: ore('dyeGreen'), T: item('energycontrol:item_component', 3)])
.register()

//=================================================================================================
