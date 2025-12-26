// Copper Adjustments =============================================================================

mods.futuremc.stonecutter.recipeBuilder()
    .input(item('thermalfoundation:storage'))
    .output(item('deeperdepths:copper_block'))
.register()

mods.futuremc.stonecutter.recipeBuilder()
    .input(item('deeperdepths:copper_block'))
    .output(item('thermalfoundation:storage'))
.register()

def copper_gear = [
    item('deeperdepths:copper_sword'),
    item('deeperdepths:copper_pickaxe'),
    item('deeperdepths:copper_axe'),
    item('deeperdepths:copper_shovel'),
    item('deeperdepths:copper_hoe'),
    item('deeperdepths:copper_helmet'),
    item('deeperdepths:copper_chestplate'),
    item('deeperdepths:copper_leggings'),
    item('deeperdepths:copper_boots')
]

for (item in copper_gear) {
    crafting.removeByOutput(item)
    mods.jei.ingredient.hide(item)
}

//=================================================================================================

// Amethyst Adjustments ===========================================================================

mods.thermalexpansion.factorizer.removeByOutput(item('deeperdepths:amethyst_block'))
crafting.removeByOutput(item('deeperdepths:amethyst_block'))
crafting.shapedBuilder()
    .output(item('deeperdepths:amethyst_block'))
    .shape('AA',
           'AA')
    .key([A: item('deeperdepths:material', 1)])
.register()
crafting.shapelessBuilder()
    .input([item('deeperdepths:amethyst_block')])
    .output(item('deeperdepths:material', 1) * 4)
.register()

ore_dict.add('gemAmethyst', item('deeperdepths:material', 1))

//BOP Amethyst
mods.thermalexpansion.factorizer.removeByOutput(item('biomesoplenty:gem_block'))
crafting.removeByOutput(item('biomesoplenty:gem_block'))
crafting.shapedBuilder()
    .output(item('biomesoplenty:gem_block'))
    .shape('AAA',
           'AAA',
           'AAA')
    .key([A: item('biomesoplenty:gem')])
.register()

//Roots Amethyst
crafting.shapedBuilder()
    .output(item('mysticalworld:amethyst_block'))
    .shape('AAA',
           'AAA',
           'AAA')
    .key([A: item('mysticalworld:amethyst_gem')])
.register()
crafting.shapelessBuilder()
    .input([item('mysticalworld:amethyst_block')])
    .output(item('mysticalworld:amethyst_gem') * 9)
.register()

//=================================================================================================

// Calcite Adjustments ============================================================================
def calcite_dust = item('rockhounding_chemistry:chemical_dusts', 23)

mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(ore('stoneCalcite'))
    .output(calcite_dust, calcite_dust)
    .chance(10)
.register()

mods.enderio.sag_mill.recipeBuilder()
    .input(ore('stoneCalcite'))
    .output(calcite_dust)
    .output(calcite_dust, 0.1)
    .tierAny()
.register()

mods.appliedenergistics2.grinder.recipeBuilder()
    .input(ore('stoneCalcite'))
    .output(calcite_dust, calcite_dust)
    .turns(5)
    .chance1(0.1)
.register()

//=================================================================================================

// Deepslate to Slate =============================================================================

mods.botania.mana_infusion.recipeBuilder()
    .input(ore('stoneDeepslate'))
    .output(item('rustic:slate'))
    .mana(100)
    .catalyst(blockstate('botania:alchemycatalyst'))
.register()

//=================================================================================================