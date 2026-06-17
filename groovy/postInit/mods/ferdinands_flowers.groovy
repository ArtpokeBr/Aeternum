// [block name, number of metas (0..<count)]
def ferdinandBlockCounts = [
    ['block_cff_flowers', 16],
    ['block_cff_flowersb', 16],
    ['block_cff_flowersc', 16],
    ['block_cff_flowersd', 16],
    ['block_cff_flowerse', 16],
    ['block_cff_doubles', 6],
    ['block_cff_doublesb', 6],
    ['block_cff_doublesc', 6],
    ['block_cff_doubless', 6],
    ['block_cff_doublesp', 6],
    ['block_cff_desert', 16],
    ['block_cff_ouch', 5],
    ['block_cff_doublesd', 6],
    ['block_cff_fungus', 4],
    ['block_cff_dark', 2],
]

for (entry in ferdinandBlockCounts) {
    def (block, metaCount) = entry
    for (meta in 0..<metaCount) {
        def flower = item("ferdinandsflowers:${block}", meta)
        ore_dict.add('flower', flower)
        ore_dict.add('allFlowers', flower)
    }
}

//=====================================================================================
// Ferdinand's Flowers adds its own extended 32-colour dye palette (flower_dye /
// flower_dyeb, see EnumCFFDyes) instead of vanilla dyes. Only the FF colours that are
// an exact match for an existing vanilla dye colour are redirected to that vanilla dye
// below; flowers producing an in-between shade with no real vanilla counterpart (e.g.
// red_orange, light_lavender, dark_magenta) keep producing FF's own dye untouched.
//=====================================================================================

def ffRemovedRecipeIds = [
    'flower_dye_1', 'flower_dye_1b', 'flower_dye_1c',
    'flower_dye_3', 'flower_dye_3b', 'flower_dye_3c',
    'flower_dye_6', 'flower_dye_6b', 'flower_dye_6c',
    'flower_dye_9', 'flower_dye_9b',
    'flower_dye_10', 'flower_dye_10b', 'flower_dye_10c',
    'flower_dye_14', 'flower_dye_14b',
    'flower_dyeb_0', 'flower_dyeb_0b', 'flower_dyeb_0c',
    'flower_dyeb_3', 'flower_dyeb_3b',
    'flower_dyeb_6', 'flower_dyeb_6b', 'flower_dyeb_6c',
    'flower_dyeb_10', 'flower_dyeb_10b',
    'flower_dyeb_15', 'flower_dyeb_15b', 'flower_dyeb_15c',
]

for (id in ffRemovedRecipeIds) {
    crafting.remove(resource("ferdinandsflowers:${id}"))
}

// [flower block, flower meta, vanilla dye meta, output count]
def ffDyeFixes = [
    ['block_cff_desert', 14, 2, 1],
    ['block_cff_desert', 15, 5, 1],
    ['block_cff_desert', 5, 14, 1],
    ['block_cff_desert', 7, 9, 1],
    ['block_cff_doubles', 2, 5, 2],
    ['block_cff_doublesb', 3, 9, 2],
    ['block_cff_doublesd', 0, 2, 2],
    ['block_cff_doublesd', 5, 2, 2],
    ['block_cff_flowers', 11, 11, 1],
    ['block_cff_flowers', 12, 11, 1],
    ['block_cff_flowers', 5, 9, 1],
    ['block_cff_flowersb', 6, 11, 1],
    ['block_cff_flowersb', 9, 14, 1],
    ['block_cff_flowersc', 13, 1, 1],
    ['block_cff_flowersc', 2, 10, 1],
    ['block_cff_flowersc', 8, 1, 1],
    ['block_cff_flowersd', 10, 6, 1],
    ['block_cff_flowersd', 12, 12, 1],
    ['block_cff_flowersd', 14, 4, 1],
    ['block_cff_flowersd', 15, 13, 1],
    ['block_cff_flowersd', 2, 1, 1],
    ['block_cff_flowersd', 4, 14, 1],
    ['block_cff_flowerse', 0, 10, 1],
    ['block_cff_flowerse', 13, 13, 1],
    ['block_cff_flowerse', 3, 5, 1],
    ['block_cff_flowerse', 6, 12, 1],
    ['block_cff_flowerse', 8, 4, 1],
    ['block_cff_fungus', 12, 12, 1],
    ['block_cff_fungus', 13, 6, 1],
]

// Blue and brown vanilla dye come from lapis lazuli and cocoa beans respectively - use
// Biomes O'Plenty's dedicated dye items for those two colours instead of the vanilla ones.
def dyeItemFor(dyeMeta) {
    if (dyeMeta == 4) return item('biomesoplenty:blue_dye')
    if (dyeMeta == 3) return item('biomesoplenty:brown_dye')
    return item("minecraft:dye:${dyeMeta}")
}

for (fix in ffDyeFixes) {
    def (block, flowerMeta, dyeMeta, count) = fix
    crafting.addShapeless(
        "ff_dye_fix_${block}_${flowerMeta}",
        dyeItemFor(dyeMeta) * count,
        [item("ferdinandsflowers:${block}:${flowerMeta}")]
    )
}

// Same flowers, alternate processing options via machines - higher yield than hand-crafting
// to make the machines worth using. Pulverizer and Squeezer give 4x dye, Crusher gives 3x.
for (fix in ffDyeFixes) {
    def (block, flowerMeta, dyeMeta, ignoredCount) = fix
    def flower = item("ferdinandsflowers:${block}:${flowerMeta}")
    def dye = dyeItemFor(dyeMeta)

    mods.thermalexpansion.pulverizer.recipeBuilder()
        .input(flower)
        .output(dye * 4)
        .energy(800)
    .register()

    mods.integrateddynamics.squeezer.recipeBuilder()
        .input(flower)
        .output(dye * 4)
    .register()

    mods.integrateddynamics.mechanical_squeezer.recipeBuilder()
        .input(flower)
        .output(dye * 4)
        .output(dye * 2, 0.5F)
    .register()

    mods.actuallyadditions.crusher.recipeBuilder()
        .input(flower)
        .output(dye * 3)
    .register()

    mods.enderio.sag_mill.recipeBuilder()
        .input(flower)
        .output(dye, 0.8F)
        .output(dye, 0.6F)
        .output(dye, 0.3F)
        .output(item('enderio:item_material', 46), 0.1F)
        .energy(900)
    .register()
}

// These specific colours no longer have any recipe producing them (replaced by vanilla
// dye above), so hide just those metas from JEI. The other flower_dye/flower_dyeb
// colours are still craftable and stay visible.
def ffDyesToHide = [
    ['flower_dye', 1], ['flower_dye', 3], ['flower_dye', 6],
    ['flower_dye', 9], ['flower_dye', 10], ['flower_dye', 14],
    ['flower_dyeb', 0], ['flower_dyeb', 3], ['flower_dyeb', 6],
    ['flower_dyeb', 10], ['flower_dyeb', 15],
]

for (dye in ffDyesToHide) {
    mods.jei.ingredient.hide(item("ferdinandsflowers:${dye[0]}", dye[1]))
}
