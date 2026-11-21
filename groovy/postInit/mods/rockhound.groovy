//Cobalt Unification ==============================================================================

furnace.removeByOutput(item('tconstruct:ingots'))
mods.thermalexpansion.furnace.removeByOutput(item('tconstruct:ingots'))
crafting.removeByOutput(item('tconstruct:ingots'))

crafting.shapedBuilder()
    .output(item('rockhounding_chemistry:metal_items', 10))
    .shape('CCC',
           'CCC',
           'CCC')
    .key([C: ore('nuggetCobalt')])
.register()

furnace.recipeBuilder()
    .input(ore('dustCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10))
    .exp(0.5)
.register()
furnace.recipeBuilder()
    .input(ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10))
    .exp(0.5)
.register()

mods.thermalexpansion.furnace.recipeBuilder()
    .input(ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10))
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('sand'), ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 2, item('thermalfoundation:material', 865))
    .chance(5)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('sand'), ore('dustCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 1, item('thermalfoundation:material', 864))
    .chance(25)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(item('thermalfoundation:material', 865), ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 3, item('thermalfoundation:material', 864))
    .chance(75)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(item('thermalfoundation:material', 866), ore('oreCobalt'))
    .output(item('rockhounding_chemistry:metal_items', 10) * 3, item('thermalfoundation:material', 865))
    .chance(75)
    .energy(4000)
.register()

//=================================================================================================

//Graphite Ingot Unification ======================================================================

furnace.removeByOutput(item('rockhounding_chemistry:metal_items', 7))
mods.thermalexpansion.furnace.removeByOutput(item('rockhounding_chemistry:metal_items', 7))
crafting.removeByInput(item('rockhounding_chemistry:metal_items', 7))

mods.jei.ingredient.hide(item('rockhounding_chemistry:metal_items', 7))
ore_dict.remove('ingotGraphite', item('rockhounding_chemistry:metal_items', 7))

//=================================================================================================

//Rod Unification =================================================================================
//Rods come from the IE Metal Press (Rod Mold) at IE's rate, 2 per ingot; scripts/mods/rockhounding.zs
//takes them out of the Profiling Bench. IE's own ingot -> rod press loop runs during recipe
//registration, before these ore entries exist, so the press recipes are added here by hand.

//Iron Rod: IE's stickIron replaces it (its crafting uses are swapped in rockhounding.zs)
mods.jei.ingredient.hide(item('rockhounding_chemistry:misc_items', 2))
crafting.shapelessBuilder()
    .name('aeternum:rockhounding_iron_rod_to_ie')
    .input([item('rockhounding_chemistry:misc_items', 2)])
    .output(item('immersiveengineering:material', 1))
.register()

//Rods only Rockhounding makes: ore-dicted like IE's (stickX) and pressed from their ingot
def rockhound_rods = [
    'Nichrome' : 14,
    'Copper'   : 24,
    'Nib'      : 25,
    'Cosm'     : 26,
    'Hastelloy': 27
]
for (rod in rockhound_rods) {
    ore_dict.add('stick' + rod.key, item('rockhounding_chemistry:misc_items', rod.value))
    mods.immersiveengineering.metal_press.recipeBuilder()
        .input(ore('ingot' + rod.key))
        .output(item('rockhounding_chemistry:misc_items', rod.value) * 2)
        .mold(item('immersiveengineering:mold', 2))
        .energy(2400)
    .register()
}

//=================================================================================================

//Coils ===========================================================================================
//The Profiling Bench's Coil Pattern recipes (scripts/mods/rockhounding.zs removes them) move to the
//IE Metal Press with the pack's Coil Mold (aeternum:mold_coil, groovy/preInit/items.groovy) and to
//the IC2 Metal Former's Extruding mode, at the Profiling Bench's yields.

//Coil Mold: same Engineer's Blueprint (Molds) recipe as IE's own molds
mods.immersiveengineering.blueprint.recipeBuilder()
    .category('molds')
    .input(ore('plateSteel'), ore('plateSteel'), ore('plateSteel'), ore('plateSteel'), ore('plateSteel'),
           item('immersiveengineering:tool', 1))
    .output(item('aeternum:mold_coil'))
.register()

//Metal Press (Coil Mold)
def rockhound_coils = [
    [ore('ingotIron'),    item('rockhounding_chemistry:misc_items', 8) * 2],  //Iron Coil
    [ore('ingotNimonic'), item('rockhounding_chemistry:misc_items', 28) * 2], //Nimonic Coil
    [ore('blockGlass'),   item('rockhounding_chemistry:misc_items', 7) * 4]   //Glass Pipe
]
for (coil in rockhound_coils) {
    mods.immersiveengineering.metal_press.recipeBuilder()
        .input(coil[0])
        .output(coil[1])
        .mold(item('aeternum:mold_coil'))
        .energy(2400)
    .register()
}

//Metal Former (Extruding = type 1). An Iron Ingot already extrudes into Iron Cable, and the
//Metal Former takes one recipe per input, so the Iron Coil comes from an IE Iron Rod instead
mods.ic2.metal_former.add(1, ore('stickIron'), item('rockhounding_chemistry:misc_items', 8))
mods.ic2.metal_former.add(1, ore('ingotNimonic'), item('rockhounding_chemistry:misc_items', 28) * 2)
mods.ic2.metal_former.add(1, ore('blockGlass'), item('rockhounding_chemistry:misc_items', 7) * 4)

//=================================================================================================