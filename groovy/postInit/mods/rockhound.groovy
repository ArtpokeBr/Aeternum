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

//Crushing Gear ===================================================================================
//The Profiling Bench's Gear Pattern is gone (scripts/mods/rockhounding.zs). The Crushing Gear is a
//Mineral Sizer consumable that stacks to 1, so it can't be batched: 1 Iron Plate in the Gear Mold
//(an Iron Ingot there already presses into IE's Iron Gear). Don't raise its stack size: the Mineral
//Sizer copies a whole cabinet stack into a tank slot (dupe) and damages a slot's stack as one.
mods.immersiveengineering.metal_press.recipeBuilder()
    .input(ore('plateIron'))
    .output(item('rockhounding_chemistry:crushing_gear'))
    .mold(item('immersiveengineering:mold', 1))
    .energy(2400)
.register()

//=================================================================================================

//Iron Arm ========================================================================================
//The Profiling Bench's Arm Pattern recipe (1 Iron Ingot -> 2, removed in scripts/mods/rockhounding.zs)
//becomes 2 Iron Rods in the Rod Mold -> 2 Iron Arms: 2 rods are 1 ingot, so the cost is unchanged
mods.immersiveengineering.metal_press.recipeBuilder()
    .input(ore('stickIron') * 2)
    .output(item('rockhounding_chemistry:misc_items', 4) * 2)
    .mold(item('immersiveengineering:mold', 2))
    .energy(2400)
.register()

//=================================================================================================

//Casings =========================================================================================
//The Profiling Bench's Casing Pattern is gone (scripts/mods/rockhounding.zs); casings follow IC2's
//rates: 1 plate + Forge Hammer -> 2 casings, and 1 plate -> 2 casings in the Metal Former (Rolling).

//Iron Casing: IC2's Iron Item Casing replaces it (crafting uses swapped in rockhounding.zs)
mods.jei.ingredient.hide(item('rockhounding_chemistry:misc_items', 5))
crafting.shapelessBuilder()
    .name('aeternum:rockhounding_iron_casing_to_ic2')
    .input([item('rockhounding_chemistry:misc_items', 5)])
    .output(item('ic2:casing', 3))
.register()

//Casings only Rockhounding makes
def rockhound_casings = [
    'Hydronalium': 17,
    'Hastelloy'  : 18,
    'Nimonic'    : 29
]
for (casing in rockhound_casings) {
    crafting.shapelessBuilder()
        .name('aeternum:rockhounding_' + casing.key.toLowerCase() + '_casing')
        .input([ore('plate' + casing.key), ore('craftingToolForgeHammer')])
        .output(item('rockhounding_chemistry:misc_items', casing.value) * 2)
    .register()
    mods.ic2.metal_former.add(2, ore('plate' + casing.key), item('rockhounding_chemistry:misc_items', casing.value) * 2)
}

//=================================================================================================

//Foils ===========================================================================================
//The Profiling Bench's Foil Pattern (1 ingot -> 8, removed in scripts/mods/rockhounding.zs) becomes
//sawing the matching plate into 8 foils in the IC2 Block Cutter and the TE Sawmill. A plate is one
//ingot, so the cost is unchanged. No hardness tag on the Block Cutter recipe: any blade cuts foil.
def rockhound_foils = [
    'Iron'    : 3,
    'Aluminum': 33,
    'Stellite': 34
]
for (foil in rockhound_foils) {
    mods.ic2.block_cutter.add(ore('plate' + foil.key), item('rockhounding_chemistry:misc_items', foil.value) * 8)
    mods.thermalexpansion.sawmill.recipeBuilder()
        .input(ore('plate' + foil.key))
        .output(item('rockhounding_chemistry:misc_items', foil.value) * 8)
        .energy(2000)
    .register()
}

//=================================================================================================

//Basic Logic Chip ================================================================================
//Alternative to Rockhounding's own recipe: an AE2 Logic Processor ringed by 4 Iron Foils and
//4 Quartz Slivers (Thaumcraft's nuggetQuartz) gives 2 chips
crafting.shapedBuilder()
    .name('aeternum:rockhounding_base_chip_from_logic_processor')
    .output(item('rockhounding_chemistry:misc_items', 1) * 2)
    .shape('FQF',
           'QPQ',
           'FQF')
    .key([F: item('rockhounding_chemistry:misc_items', 3),
          Q: ore('nuggetQuartz'),
          P: item('appliedenergistics2:material', 22)])
.register()

//=================================================================================================

//Gauze Catalysts =================================================================================
//The Profiling Bench's Gauze Pattern (1 ingot -> 1 catalyst, removed in scripts/mods/rockhounding.zs)
//moves to the IE Metal Press with the pack's Gauze Mold (aeternum:mold_gauze). Catalysts are
//consumables that stack to 1, so 1 per ingot as before.
mods.immersiveengineering.blueprint.recipeBuilder()
    .category('molds')
    .input(ore('plateSteel'), ore('plateSteel'), ore('plateSteel'), ore('plateSteel'), ore('plateSteel'),
           item('immersiveengineering:tool', 1))
    .output(item('aeternum:mold_gauze'))
.register()

def rockhound_catalysts = [
    'Iron'     : 'fe',
    'Molybdenum': 'mo',
    'Graphite' : 'gr',
    'Vanadium' : 'va',
    'Platinum' : 'pt',
    'Whitegold': 'wg',
    'Nial'     : 'nl',
    'Gold'     : 'au',
    'Nickel'   : 'ni',
    'Osmium'   : 'os',
    'Zeolite'  : 'ze',
    'Zinc'     : 'zn',
    'Cobalt'   : 'co',
    'Inconel'  : 'in'
]
for (catalyst in rockhound_catalysts) {
    mods.immersiveengineering.metal_press.recipeBuilder()
        .input(ore('ingot' + catalyst.key))
        .output(item('rockhounding_chemistry:' + catalyst.value + '_catalyst'))
        .mold(item('aeternum:mold_gauze'))
        .energy(2400)
    .register()
}

//=================================================================================================

//Profiling Bench: removed ========================================================================
//Every recipe it had now lives in other machines (see the blocks above), so the bench
//(machines_a:11) loses its crafting recipe and is hidden with its now-unused pattern icons.
crafting.remove('rockhounding_chemistry:profiling_bench')
mods.jei.ingredient.hide(item('rockhounding_chemistry:machines_a', 11))
for (i in 0..10) {
    mods.jei.ingredient.hide(item('rockhounding_chemistry:pattern_items', i))
}

//=================================================================================================