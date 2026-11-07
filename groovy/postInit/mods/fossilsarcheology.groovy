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

// Permafrost processing - its 5 drops are an even 20% each (Fern Seed, Skull, Iced Meat, Bone, Book).
// Bone guaranteed like the Fossil block, the rest at an equal chance. Book left out: the SAG Mill caps at 4 outputs.
// Enhanced SAG Mill only, bonus outputs scale with grinding balls.
// =================================================================================================

mods.enderio.sag_mill.recipeBuilder()
    .input(item('fossil:permafrost'))
    .output(item('minecraft:bone'))
    .output(item('fossil:fossil_seed_fern'), 0.35F)
    .output(item('fossil:iced_meat'), 0.35F)
    .output(item('fossil:skull_block'), 0.35F)
    .energy(6400)
    .tierEnhanced()
    .bonusTypeChance()
.register()

//=================================================================================================
