

//Fixing Conflicting Recipes ======================================================================

//Mossy and Non-Mossy Stone Bricks Recipes
crafting.shapedBuilder()
    .name('minecraft:stone_brick_slab')
    .output(item('minecraft:stone_slab', 5) * 6)
    .shape([[item('minecraft:stonebrick'), item('minecraft:stonebrick'), item('minecraft:stonebrick')]])
    .replaceByName()
.register()

crafting.shapedBuilder()
    .name('minecraft:stone_brick_stairs')
    .output(item('minecraft:stone_brick_stairs') * 8)
    .shape([[item('minecraft:stonebrick'), null, null],
            [item('minecraft:stonebrick'), item('minecraft:stonebrick'), null],
            [item('minecraft:stonebrick'), item('minecraft:stonebrick'), item('minecraft:stonebrick')]
    ])
    .replaceByName()
.register()