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