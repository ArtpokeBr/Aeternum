//Alternate Recipes for Vanilla Blocks ============================================================

//Piston with Bronze
crafting.shapedBuilder()
    .output(item('minecraft:piston') * 2)
    .shape(
        'WWW',
        'CIC',
        'CRC'
    )
    .key([
        W: ore('plankWood'),
        C: ore('cobblestone'),
        I: ore('ingotBronze'),
        R: ore('dustRedstone')
    ])
.register()

//Piston with Steel
crafting.shapedBuilder()
    .output(item('minecraft:piston') * 4)
    .shape(
        'WWW',
        'CIC',
        'CRC'
    )
    .key([
        W: ore('plankTreatedWood'),
        C: ore('cobblestone'),
        I: ore('ingotSteel'),
        R: ore('dustRedstone')
    ])
.register()

//Tripwire with Bronze
crafting.shapedBuilder()
    .output(item('minecraft:tripwire_hook') * 3)
    .shape(
        ' I ',
        ' S ',
        ' W '
    )
    .key([
        W: ore('plankTreatedWood'),
        I: ore('ingotBronze'),
        S: ore('stickWood')
    ])
.register()

//Tripwire with Steel
crafting.shapedBuilder()
    .output(item('minecraft:tripwire_hook') * 4)
    .shape(
        ' I ',
        ' S ',
        ' W '
    )
    .key([
        W: ore('plankTreatedWood'),
        I: ore('ingotSteel'),
        S: ore('stickWood')
    ])
.register()

//Cauldron with Steel
crafting.shapedBuilder()
    .output(item('minecraft:cauldron') * 2)
    .shape(
        'S S',
        'S S',
        'SSS'
    )
    .key([
        S: ore('ingotSteel')
    ])
.register()

//Bucket with Steel
crafting.shapedBuilder()
    .output(item('minecraft:bucket') * 2)
    .shape(
        'S S',
        ' S '
    )
    .key([
        S: ore('ingotSteel')
    ])
.register()

// ================================================================================================

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
