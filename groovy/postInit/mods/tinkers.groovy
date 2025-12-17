//Cobalt Unification ==============================================================================

//Dust ===
ore_dict.remove('dustCobalt', item('enderio:item_material', 31))
mods.jei.ingredient.hide(item('enderio:item_material', 31))

//Ingot ===
mods.jei.ingredient.hide(item('tconstruct:ingots'))
ore_dict.remove('ingotCobalt', item('tconstruct:ingots'))
mods.thermalexpansion.factorizer.removeByOutput(item('tconstruct:ingots'))
mods.thermalexpansion.factorizer.removeByInput(item('tconstruct:ingots'))

mods.thermalexpansion.pulverizer.removeByOutput(item('rockhounding_chemistry:chemical_dusts', 25))
mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(ore('ingotCobalt'))
    .output(item('rockhounding_chemistry:chemical_dusts', 25))
    .energy(2000)
.register()

mods.thermalexpansion.crucible.removeByInput(item('tconstruct:ingots'))
mods.thermalexpansion.crucible.recipeBuilder()
    .input(ore('ingotCobalt'))
    .fluidOutput(fluid('cobalt') * 144)
.register()

//Manyullyn Alloying
mods.thermalexpansion.smelter.removeByInput(item('tconstruct:ingots'))
mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('ingotArdite'), ore('ingotCobalt'))
    .output(item('tconstruct:ingots', 2))
    .energy(9600)
.register()

//Block ===
mods.jei.ingredient.hide(item('tconstruct:metal'))
// ore_dict.remove('blockCobalt', item('tconstruct:metal'))
crafting.removeByOutput(item('tconstruct:metal'))
mods.thermalexpansion.factorizer.removeByOutput(item('tconstruct:metal'))
mods.thermalexpansion.factorizer.removeByInput(item('tconstruct:metal'))

//=================================================================================================