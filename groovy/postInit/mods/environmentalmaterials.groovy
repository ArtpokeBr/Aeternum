//Chisel Groups for Environmental Materials' Stones (base, Bricks, Paver and Tiles). Basalt is disabled in its config.
def stones = ['hardened_stone', 'granodiorite', 'pumice', 'travertine', 'taconite', 'blueschist', 'greenschist']
def shapes = ['', '_bricks', '_paver', '_tiles']

stones.each { stone ->
    def group = mods.chisel.carving.carvingGroup(stone)
    shapes.each { shape -> group.add(item("environmentalmaterials:${stone}${shape}")) }
}

//Marble joins Chisel's own Marble group instead.
shapes.each { shape -> mods.chisel.carving.addVariation('marble', item("environmentalmaterials:marble${shape}")) }
