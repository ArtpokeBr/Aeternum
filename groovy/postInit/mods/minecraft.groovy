//OreDicting Vanilla Clay Recipe ==================================================================

crafting.remove('minecraft:clay')
crafting.shapelessBuilder()
    .name(resource('minecraft:clay_ball'))
    .input([ore('blockClay')])
    .output(item('minecraft:clay_ball') * 4)
.register()

//=================================================================================================

//Clay Block by Hand
crafting.shapedBuilder()
    .output(item('minecraft:clay'))
    .shape('CC',
           'CC')
    .key([C: item('minecraft:clay_ball')])
.register()
