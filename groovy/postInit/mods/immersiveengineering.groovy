
//Making the Circuit Board a bit more Useful
ore_dict.add('circuitBasic', item('immersiveengineering:material', 27))

//Remaking Blueprint Recipes ======================================================================

//Molds
crafting.remove('immersiveengineering:blueprints/molds')
crafting.shapelessBuilder()
    .name('immersiveengineering:blueprints/molds')
    .input([item('buildcraftbuilders:snapshot', 2), ore('plateSteel')])
    .output(item('immersiveengineering:blueprint').withNbt(['blueprint': 'molds']))
.register()

//Components
crafting.remove('immersiveengineering:blueprints/components')
crafting.shapelessBuilder()
    .name('immersiveengineering:blueprints/components')
    .input([item('buildcraftbuilders:snapshot', 2), item('immersiveengineering:material', 8)])
    .output(item('immersiveengineering:blueprint').withNbt(['blueprint': 'components']))
.register()

//Common Projectiles
crafting.remove('immersiveengineering:blueprints/common_cartridges')
crafting.shapelessBuilder()
    .name('immersiveengineering:blueprints/common_cartridges')
    .input([item('buildcraftbuilders:snapshot', 2), item('immersiveengineering:bullet', 1)])
    .output(item('immersiveengineering:blueprint').withNbt(['blueprint': 'bullets']))
.register()

// ================================================================================================