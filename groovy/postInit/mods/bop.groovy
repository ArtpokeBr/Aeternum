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
ore_dict.remove('oreSapphire', item('biomesoplenty:gem_ore', 6))

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

// ================================================================================================