// Fossil Block processing - alternate to picking/finding bones by hand, using the 4 most
// common drops from breaking a Fossil block (Biofossil, Relic Scrap, Bone, Plant Fossil)
// Enhanced SAG Mill only, bonus outputs scale with grinding balls.
// =================================================================================================

mods.enderio.sag_mill.recipeBuilder()
    .input(item('fossil:fossil'))
    .output(item('minecraft:bone'))
    .output(item('fossil:biofossil'), 0.6F)
    .output(item('fossil:relic_scrap'), 0.45F)
    .output(item('fossil:plant_fossil'), 0.2F)
    .energy(6400)
    .tierEnhanced()
    .bonusTypeChance()
.register()

//=================================================================================================
