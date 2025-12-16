//Unifying Barley Flour ===========================================================================

//crafting.removeByOutput(item('natura:materials', 1))
mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(item('natura:materials'))
    .output(item('natura:materials', 1) * 2)
    .energy(4000)
.register()

mods.appliedenergistics2.grinder.recipeBuilder()
    .input(item('natura:materials'))
    .output(item('natura:materials', 1) * 2)
    .turns(10)
.register()

//=================================================================================================