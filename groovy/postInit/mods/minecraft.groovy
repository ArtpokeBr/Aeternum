//OreDicting Vanilla Clay Recipe ==================================================================

crafting.remove('minecraft:clay')
crafting.shapelessBuilder()
    .name(resource('minecraft:clay_ball'))
    .input([ore('blockClay')])
    .output(item('minecraft:clay_ball') * 4)
.register()

//=================================================================================================