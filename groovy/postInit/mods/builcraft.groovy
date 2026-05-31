
//Land Mark with Electrotine
crafting.removeByOutput(item('buildcraftcore:marker_volume'))
crafting.shapedBuilder()
    .output(item('buildcraftcore:marker_volume'))
    .shape('E',
           'R')
    .key([R: item('minecraft:redstone_torch'), E: ore('dustElectrotine')])
.register()