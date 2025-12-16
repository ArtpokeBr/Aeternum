
//Adding Chisel Compat to Underground's Stones
//mods.chisel.carving.addVariation("marble", item('undergroundbiomes:metamorphic_stone', 2))
mods.chisel.carving.addVariation("limestone", item('undergroundbiomes:sedimentary_stone'))
mods.chisel.carving.addVariation("basalt", item('undergroundbiomes:igneous_stone', 5))

//Converting Marble into Usable Marble.
crafting.shapelessBuilder().output(item('undergroundbiomes:metamorphic_stone', 2)).input([ore('blockMarble')]).register()
crafting.shapelessBuilder().output(item('astralsorcery:blockmarble')).input([item('undergroundbiomes:metamorphic_stone', 2)]).register()