
//Polymer Clay: PVC (Chloride Compound) + plasticizer (Coal Tar Compound) + kaolin (clay) + pigment (lapis)
crafting.remove('deepmoblearning:misc/polymer_clay')
crafting.shapedBuilder()
    .output(item('deepmoblearning:polymer_clay') * 16)
    .shape('HCH',
           'CLC',
           'CTC')
    .key([H: ore('compoundChloride'), T: ore('compoundTar'), C: item('minecraft:clay_ball'), L: ore('gemLapis')])
.register()
