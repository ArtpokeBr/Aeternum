
//Polymer Clay: PVC (Chloride Compound) + plasticizer (Coal Tar Compound) + kaolin (clay) + pigment (lapis)
crafting.remove('deepmoblearning:misc/polymer_clay')
crafting.shapedBuilder()
    .output(item('deepmoblearning:polymer_clay') * 16)
    .shape('HCH',
           'CLC',
           'CTC')
    .key([H: ore('compoundChloride'), T: ore('compoundTar'), C: item('minecraft:clay_ball'), L: ore('gemLapis')])
.register()

//In-World Glitch-Infused Ingot: same as DML's own handler (which has no config toggle), here so it shows in JEI.
//Whichever runs first consumes the items, so they never both craft from the same drop. The water source is not consumed.
inWorldCrafting.fluidToItem.recipeBuilder()
    .fluidInput(fluid('water'), 0.0f)
    .input(item('deepmoblearning:glitch_fragment'), item('minecraft:gold_ingot'), item('minecraft:dye', 4))
    .output(item('deepmoblearning:glitch_infused_ingot'))
.register()
