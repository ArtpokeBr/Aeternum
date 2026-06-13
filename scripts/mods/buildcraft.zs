import mods.buildcraft.AssemblyTable;

/*
 █████  ███████ ███████ ███████ ███    ███ ██████  ██      ██    ██     ████████  █████  ██████  ██      ███████ 
██   ██ ██      ██      ██      ████  ████ ██   ██ ██       ██  ██         ██    ██   ██ ██   ██ ██      ██      
███████ ███████ ███████ █████   ██ ████ ██ ██████  ██        ████          ██    ███████ ██████  ██      █████   
██   ██      ██      ██ ██      ██  ██  ██ ██   ██ ██         ██           ██    ██   ██ ██   ██ ██      ██      
██   ██ ███████ ███████ ███████ ██      ██ ██████  ███████    ██           ██    ██   ██ ██████  ███████ ███████ 
*/                                                                                                                

// Method
//AssemblyTable.addRecipe(recipeName, output, power, inputs);

//Removing Original Chipsets
AssemblyTable.removeByName("buildcraftsilicon:redstone_chipset");
AssemblyTable.removeByName("buildcraftsilicon:iron_chipset");
AssemblyTable.removeByName("buildcraftsilicon:gold_chipset");
AssemblyTable.removeByName("buildcraftsilicon:quartz_chipset");
AssemblyTable.removeByName("buildcraftsilicon:diamond_chipset");

//Redstone Chipset
AssemblyTable.addRecipe("aeternum_redstone_chipset", <buildcraftsilicon:redstone_chipset>,  1000,[<ore:dustRedstone>]);
//Copper Chipset
AssemblyTable.addRecipe("aeternum_copper_chipset", <aeternum:copper_chipset>, 2500,[<ore:dustRedstone>, <ore:ingotCopper>]);
//Iron Chipset
AssemblyTable.addRecipe("aeternum_iron_chipset", <buildcraftsilicon:redstone_chipset:1>, 5000,[<ore:dustRedstone>, <ore:ingotIron>]);
//Gold Chipset
AssemblyTable.addRecipe("aeternum_gold_chipset", <buildcraftsilicon:redstone_chipset:2>, 8000,[<ore:dustRedstone>, <ore:ingotGold>]);
//Quartz Chipset
AssemblyTable.addRecipe("aeternum_quartz_chipset", <buildcraftsilicon:redstone_chipset:3>, 2500,[<ore:dustRedstone>, <ore:gemQuartz>]);
//Diamond Chipset
AssemblyTable.addRecipe("aeternum_diamond_chipset", <buildcraftsilicon:redstone_chipset:4>, 10000,[<ore:dustRedstone>, <ore:gemDiamond>]);

//Adding a Recipe to the Filler
recipes.addShaped(<buildcraftbuilders:filler>, [[<ore:dyeBlack>, <buildcraftcore:marker_volume>, <ore:dyeBlack>],[<ore:dyeYellow>, <ore:craftingTableWood>, <ore:dyeYellow>], [<ore:gearGold>, <ore:chest>, <ore:gearGold>]]);