//Botania Dyes with Other Methods =================================================================

def botania_dyes = [
    [dye: item('botania:dye', 0), petal: item('botania:petal', 0)],
    [dye: item('botania:dye', 1), petal: item('botania:petal', 1)],
    [dye: item('botania:dye', 2), petal: item('botania:petal', 2)],
    [dye: item('botania:dye', 3), petal: item('botania:petal', 3)],
    [dye: item('botania:dye', 4), petal: item('botania:petal', 4)],
    [dye: item('botania:dye', 5), petal: item('botania:petal', 5)],
    [dye: item('botania:dye', 6), petal: item('botania:petal', 6)],
    [dye: item('botania:dye', 7), petal: item('botania:petal', 7)],
    [dye: item('botania:dye', 8), petal: item('botania:petal', 8)],
    [dye: item('botania:dye', 9), petal: item('botania:petal', 9)],
    [dye: item('botania:dye', 10), petal: item('botania:petal', 10)],
    [dye: item('botania:dye', 11), petal: item('botania:petal', 11)],
    [dye: item('botania:dye', 12), petal: item('botania:petal', 12)],
    [dye: item('botania:dye', 13), petal: item('botania:petal', 13)],
    [dye: item('botania:dye', 14), petal: item('botania:petal', 14)],
    [dye: item('botania:dye', 15), petal: item('botania:petal', 15)]
];

botania_dyes.each { recipe ->

    // mods.roots.mortar.recipeBuilder().name("roots_petal:$recipe.petal")
    //     .input(recipe.petal)
    //     .generate(true)
    //     .output(recipe.dye)
    //     .color(1, 0, 0.1, 1, 0, 0.1)
    // .register()

    mods.thermalexpansion.pulverizer.recipeBuilder()
        .input(recipe.petal)
        .output(recipe.dye, recipe.dye)
        .chance(10)
    .register()

    mods.enderio.sag_mill.recipeBuilder()
        .input(recipe.petal)
        .output(recipe.dye)
        .output(recipe.dye, 0.1)
        .tierAny()
    .register()

    mods.appliedenergistics2.grinder.recipeBuilder()
        .input(recipe.petal)
        .output(recipe.dye, recipe.dye)
        .turns(5)
        .chance1(0.1)
    .register()

}

//=================================================================================================

// Adding a usage to Project Vibrant Journey's Stuff ==============================================

mods.botania.mana_infusion.recipeBuilder()
    .input(item('pvj:unstable_essence'))
    .output(item('minecraft:ghast_tear'))
    .mana(750)
    .catalyst(blockstate('botania:alchemycatalyst'))
.register()
