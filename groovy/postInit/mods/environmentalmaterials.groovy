//Chisel Groups for Environmental Materials' Stones (base, Bricks, Paver and Tiles).
def stones = ['hardened_stone', 'granodiorite', 'pumice', 'travertine', 'taconite', 'blueschist', 'greenschist']
def shapes = ['', '_bricks', '_paver', '_tiles']

stones.each { stone ->
    def group = mods.chisel.carving.carvingGroup(stone)
    shapes.each { shape -> group.add(item("environmentalmaterials:${stone}${shape}")) }
}

//Marble and Basalt join Chisel's own groups instead.
['marble', 'basalt'].each { stone ->
    shapes.each { shape -> mods.chisel.carving.addVariation(stone, item("environmentalmaterials:${stone}${shape}")) }
}
