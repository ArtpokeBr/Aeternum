//Fertilizer Compound Recipe (Natura Barley compat -> any grain) =================================
//Forestry's Natura compat (PluginNatura) auto-registers "forestry:item.for.fertilizer_compound"
//using Natura's Barley specifically. Re-adding it with ore('listAllgrain') so any grain-type
//item (wheat, rice, barley, etc.) works, not just Natura's.

crafting.remove(resource('forestry:item.for.fertilizer_compound'))
crafting.shapedBuilder()
    .name(resource('forestry:item.for.fertilizer_compound'))
    .output(item('forestry:fertilizer_compound'))
    .matrix([
        [null, ore('listAllgrain'), null],
        [ore('listAllgrain'), item('minecraft:dirt'), ore('listAllgrain')],
        [null, ore('listAllgrain'), null]
    ])
.register()

//=================================================================================================
