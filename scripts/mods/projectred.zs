#modloaded projectred-core nuclearcraft

//Red Alloy & Electrotine Alloy in the NuclearCraft Alloy Furnace (no GroovyScript compat for NC).
//Every other alloyer is in groovy/postInit/mods/projectred.groovy.
mods.nuclearcraft.AlloyFurnace.addRecipe(<ore:ingotCopper>, <ore:dustRedstone> * 4, <projectred-core:resource_item:103>, 1.0, 1.0, 0.0);
mods.nuclearcraft.AlloyFurnace.addRecipe(<ore:ingotSilver>, <ore:dustElectrotine> * 4, <projectred-core:resource_item:104>, 1.0, 1.0, 0.0);

//Doped Silicon (Silicon + 8 dust) in the NuclearCraft Alloy Furnace.
mods.nuclearcraft.AlloyFurnace.addRecipe(<projectred-core:resource_item:301>, <ore:dustRedstone> * 8, <projectred-core:resource_item:320>, 1.0, 1.0, 0.0);
mods.nuclearcraft.AlloyFurnace.addRecipe(<projectred-core:resource_item:301>, <ore:dustGlowstone> * 8, <projectred-core:resource_item:341>, 1.0, 1.0, 0.0);
mods.nuclearcraft.AlloyFurnace.addRecipe(<projectred-core:resource_item:301>, <ore:dustElectrotine> * 8, <projectred-core:resource_item:342>, 1.0, 1.0, 0.0);
