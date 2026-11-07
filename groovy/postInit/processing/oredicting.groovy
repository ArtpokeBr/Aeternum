
//Making Natura's Barley Flour an actual Wheat Flour
ore_dict.add('dustWheat', item('natura:materials', 1))

//Adding Thermal's Coke to Coke Block Tag (So it can be chiseled)
ore_dict.add('blockCoalCoke', item('thermalfoundation:storage_resource', 1))

//Adding Mystical World Material Blocks to OreDicts so they can be Chiseled.
// ore_dict.add('blockCopper', item('mysticalworld:copper_block'))
// ore_dict.add('blockSilver', item('mysticalworld:silver_block'))
// ore_dict.add('blockAmethyst', item('mysticalworld:amethyst_block'))

//Adding BOP Material Blocks to OreDicts so they can be Chiseled.
ore_dict.add('blockAmethyst', item('biomesoplenty:gem_block'))

//Adding Deeper Depths Material Blocks to OreDicts so they can be Chiseled.
// ore_dict.add('blockAmethyst', item('deeperdepths:amethyst_block'))

//Adding Immersive Engineering Metal Blocks back to their OreDicts so they can be Chiseled (UniDict's keepOneEntry strips them).
ore_dict.add('blockCopper', item('immersiveengineering:storage', 0))
ore_dict.add('blockAluminum', item('immersiveengineering:storage', 1))
ore_dict.add('blockLead', item('immersiveengineering:storage', 2))
ore_dict.add('blockSilver', item('immersiveengineering:storage', 3))
ore_dict.add('blockNickel', item('immersiveengineering:storage', 4))
ore_dict.add('blockUranium', item('immersiveengineering:storage', 5))
ore_dict.add('blockElectrum', item('immersiveengineering:storage', 7))
ore_dict.add('blockSteel', item('immersiveengineering:storage', 8))

//Chisel has no Constantan group, so one is made for Thermal's and Immersive Engineering's Constantan Blocks.
mods.chisel.carving.carvingGroup('constantan')
    .add(item('thermalfoundation:storage_alloy', 4), item('immersiveengineering:storage', 6))
