//Removing Furnace Recipes by Output
def removeFromFurnaceOutput = [
    item('appliedenergistics2:material', 5)
];

removeFromFurnaceOutput.each { item ->
    furnace.removeByOutput(item)
}

//Adjusting Silicon Smelting Recipes =================================================================
furnace.add(ore('dustQuartz'), item('nuclearcraft:gem', 6), 0.25)
furnace.add(ore('dustCertusQuartz'), item('nuclearcraft:gem', 6), 0.25)

mods.thermalexpansion.furnace.removeByOutput(item('appliedenergistics2:material', 5))
mods.thermalexpansion.furnace.add(2000, ore('dustQuartz'), item('nuclearcraft:gem', 6))
mods.thermalexpansion.furnace.add(2000, ore('dustCertusQuartz'), item('nuclearcraft:gem', 6))