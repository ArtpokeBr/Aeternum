//Scarab now requires Twilight Forest Stuff =======================================================

crafting.remove("atum:scarab")
crafting.shapedBuilder()
    .output(item('atum:scarab'))
    .shape('FDF',
           'STS',
           'GGG')
    .key([
        T: ore('twilightTrophy').reuse(), 
        G: ore('plateGold'), 
        D: ore('manaDiamond'), 
        F: item('twilightforest:alpha_fur'),
        S: ore('ingotSteeleaf')
    ])
.register()

//=================================================================================================