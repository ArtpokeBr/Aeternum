
//Raw Chips with Buildcraft's Chipsets

//Basic
crafting.shapedBuilder()
    .output(item('logisticspipes:chip_basic_raw') * 4)
    .shape('SC',
           'CS')
    .key([C: item('aeternum:copper_chipset'), S: ore('sand')])
.register()

//Advanced
crafting.shapedBuilder()
    .output(item('logisticspipes:chip_advanced_raw') * 4)
    .shape('BD',
           'DB')
    .key([D: item('buildcraftsilicon:redstone_chipset', 4), B: item('logisticspipes:chip_basic_raw')])
.register()

//FPGA
crafting.shapedBuilder()
    .output(item('logisticspipes:chip_fpga_raw') * 32)
    .shape(
        'SDS',
        'GRG',
        'SES'
        )
    .key([
        S: ore('sand'),
        D: item('buildcraftsilicon:redstone_chipset', 4),
        G: item('buildcraftsilicon:redstone_chipset', 2),
        R: item('buildcraftsilicon:redstone_chipset'),
        E: ore('dustElectrotine')
        ])
.register()
