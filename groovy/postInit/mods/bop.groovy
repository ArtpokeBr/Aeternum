//Kelp Tweaks =====================================================================================

furnace.recipeBuilder()
    .input(item('biomesoplenty:seaweed'))
    .output(item('oe:dried_kelp'))
    .exp(0.5)
.register()

mods.thermalexpansion.furnace.recipeBuilder()
    .input(item('biomesoplenty:seaweed'))
    .output(item('oe:dried_kelp'))
.register()

//=================================================================================================

//BOP Gem Unification =============================================================================

ore_dict.remove('orePeridot', item('biomesoplenty:gem_ore', 2))
ore_dict.remove('oreRuby', item('biomesoplenty:gem_ore', 1))
ore_dict.remove('blockRuby', item('biomesoplenty:gem_block', 1))
ore_dict.remove('oreSapphire', item('biomesoplenty:gem_ore', 6))
ore_dict.remove('blockSapphire', item('biomesoplenty:gem_block', 6))
ore_dict.remove('oreAmber', item('biomesoplenty:gem_ore', 7))
ore_dict.remove('blockAmber', item('biomesoplenty:gem_block', 7))

//---

mods.mekanism.combiner.removeByInput(item('biomesoplenty:gem', 2), item('minecraft:cobblestone'))
mods.mekanism.combiner.removeByInput(item('projectred-core:resource_item', 202), item('minecraft:cobblestone'))
mods.mekanism.combiner.recipeBuilder()
    .input(item('projectred-core:resource_item', 202) * 3)
    .extra(item('minecraft:cobblestone'))
    .output(item('biomesoplenty:gem_ore', 2))
.register()

crafting.shapelessBuilder()
    .name("aeternum:peridot_conversion")
    .input([item('biomesoplenty:gem', 2)])
    .output(item('projectred-core:resource_item', 202))
.register()

//---

mods.mekanism.combiner.removeByInput(item('biomesoplenty:gem', 1), item('minecraft:cobblestone'))
mods.mekanism.combiner.removeByInput(item('projectred-core:resource_item', 200), item('minecraft:cobblestone'))
mods.mekanism.combiner.recipeBuilder()
    .input(item('projectred-core:resource_item', 200) * 3)
    .extra(item('minecraft:cobblestone'))
    .output(item('projectred-exploration:ore'))
.register()

crafting.shapelessBuilder()
    .name("aeternum:ruby_conversion")
    .input([item('biomesoplenty:gem', 1)])
    .output(item('projectred-core:resource_item', 200))
.register()

//---

mods.mekanism.combiner.removeByInput(item('biomesoplenty:gem', 6), item('minecraft:cobblestone'))
mods.mekanism.combiner.removeByInput(item('projectred-core:resource_item', 201), item('minecraft:cobblestone'))
mods.mekanism.combiner.recipeBuilder()
    .input(item('projectred-core:resource_item', 201) * 3)
    .extra(item('minecraft:cobblestone'))
    .output(item('projectred-exploration:ore', 1))
.register()

crafting.shapelessBuilder()
    .name("aeternum:sapphire_conversion")
    .input([item('biomesoplenty:gem', 6)])
    .output(item('projectred-core:resource_item', 201))
.register()

//---

mods.mekanism.combiner.removeByInput(item('biomesoplenty:gem', 7), item('minecraft:cobblestone'))
mods.mekanism.combiner.removeByInput(item('thaumcraft:amber'), item('minecraft:cobblestone'))
mods.mekanism.combiner.recipeBuilder()
    .input(item('thaumcraft:amber') * 3)
    .extra(item('minecraft:cobblestone'))
    .output(item('thaumcraft:ore_amber'))
.register()

crafting.remove('unidict:gemamber_x9_size.1')
crafting.shapelessBuilder()
    .name("aeternum:amber_conversion")
    .input([item('biomesoplenty:gem', 7)])
    .output(item('thaumcraft:amber'))
.register()

mods.mekanism.enrichment_chamber.removeByInput(item('biomesoplenty:gem_ore', 7))
furnace.removeByInput(item('biomesoplenty:gem_ore', 7))

mods.thermalexpansion.pulverizer.removeByInput(item('biomesoplenty:gem_ore', 7))
mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(item('thaumcraft:ore_amber'))
    .output(item('thaumcraft:amber') * 2)
.register()

//Thermal Expansion's auto-registered Factorizer recipe assumed every amber block compresses at
//the standard 9:1 ratio. That's correct for BOP's Block of Amber (a genuine 9-gem crafting
//recipe), but Thaumcraft's Amber Block is only 4 Amber - decompressing it back into 9 was a free
//5-amber duplication exploit. Overriding it to the correct 4:1 ratio; BOP's stays untouched.
mods.thermalexpansion.factorizer.removeByOutput(item('thaumcraft:amber_block'))
mods.thermalexpansion.factorizer.recipeBuilder()
    .input(item('thaumcraft:amber') * 4)
    .output(item('thaumcraft:amber_block'))
    .combine()
.register()

mods.thermalexpansion.factorizer.removeByOutput(item('thaumcraft:amber'))
mods.thermalexpansion.factorizer.recipeBuilder()
    .input(item('thaumcraft:amber_block'))
    .output(item('thaumcraft:amber') * 4)
    .combine()
.register()

crafting.shapelessBuilder()
    .name("aeternum:bop_amber_decompression")
    .input([item('biomesoplenty:gem_block', 7)])
    .output(item('thaumcraft:amber') * 9)
.register()

// ================================================================================================