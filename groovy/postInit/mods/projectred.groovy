//Red Alloy & Electrotine Alloy ===================================================================

//IE adds Red Alloy (1 Copper + 4 Redstone) to its Alloy Kiln and Arc Furnace; mirror it on every
//other alloyer, and add Electrotine Alloy (1 Silver + 4 Electrotine) the same way.
//NuclearCraft's Alloy Furnace has no GroovyScript compat: see scripts/mods/projectred.zs.
def alloys = [
    [base: 'Silver', additive: 'dustElectrotine', infuse: 'ELECTROTINE', output: item('projectred-core:resource_item', 104)],
    [base: 'Copper', additive: 'dustRedstone', infuse: 'REDSTONE', output: item('projectred-core:resource_item', 103)]
]

//Electrotine infuse sources, same values as Mekanism's Redstone (type registered in preInit/mekanism.groovy)
mods.mekanism.infusion.add('ELECTROTINE', 10, ore('dustElectrotine'))
mods.mekanism.infusion.add('ELECTROTINE', 80, item('aeternum:compressed_electrotine'))
mods.mekanism.infusion.add('ELECTROTINE', 90, ore('blockElectrotine'))

//Compressed Electrotine, like Compressed Redstone (1 dust -> 1 compressed)
mods.mekanism.enrichment_chamber.recipeBuilder()
    .input(ore('dustElectrotine'))
    .output(item('aeternum:compressed_electrotine'))
.register()

//Glowstone infuse sources, for Energized Silicon below. A Glowstone block is only 4 dust, so 40.
mods.mekanism.infusion.add('GLOWSTONE', 10, ore('dustGlowstone'))
mods.mekanism.infusion.add('GLOWSTONE', 80, item('aeternum:compressed_glowstone'))
mods.mekanism.infusion.add('GLOWSTONE', 40, item('minecraft:glowstone'))

mods.mekanism.enrichment_chamber.recipeBuilder()
    .input(ore('dustGlowstone'))
    .output(item('aeternum:compressed_glowstone'))
.register()

for (alloy in alloys) {
    //Immersive Engineering: Red Alloy is already added by IE itself
    if (alloy.base != 'Copper') {
        for (form in ['ingot', 'dust']) {
            mods.immersiveengineering.alloy_kiln.recipeBuilder()
                .input(ore(form + alloy.base), ore(alloy.additive) * 4)
                .output(alloy.output)
                .time(200)
            .register()

            mods.immersiveengineering.arc_furnace.recipeBuilder()
                .mainInput(ore(form + alloy.base))
                .input(ore(alloy.additive) * 4)
                .output(alloy.output)
                .time(100)
                .energyPerTick(512)
                .alloying()
            .register()
        }
    }

    mods.enderio.alloy_smelter.recipeBuilder()
        .input(ore('ingot' + alloy.base), ore(alloy.additive) * 4)
        .output(alloy.output)
        .energy(2000)
        .tierAny()
    .register()

    mods.thermalexpansion.smelter.recipeBuilder()
        .input(ore('ingot' + alloy.base), ore(alloy.additive) * 4)
        .output(alloy.output)
        .energy(4000)
    .register()

    //40 infuse = 4 dusts (10 each)
    mods.mekanism.metallurgic_infuser.recipeBuilder()
        .input(ore('ingot' + alloy.base))
        .infuse(alloy.infuse)
        .amount(40)
        .output(alloy.output)
    .register()
}

//=================================================================================================

//Doped Silicon ===================================================================================

//ProjectRed dopes Silicon by crafting it with 8 dust into a compound and smelting that; let every
//alloyer do it in one step.
//NuclearCraft's Alloy Furnace has no GroovyScript compat: see scripts/mods/projectred.zs.
def silicon = item('projectred-core:resource_item', 301)
def dopedSilicon = [
    [additive: 'dustRedstone', infuse: 'REDSTONE', output: item('projectred-core:resource_item', 320)],
    [additive: 'dustGlowstone', infuse: 'GLOWSTONE', output: item('projectred-core:resource_item', 341)],
    [additive: 'dustElectrotine', infuse: 'ELECTROTINE', output: item('projectred-core:resource_item', 342)]
]

for (doped in dopedSilicon) {
    mods.immersiveengineering.alloy_kiln.recipeBuilder()
        .input(silicon, ore(doped.additive) * 8)
        .output(doped.output)
        .time(200)
    .register()

    mods.immersiveengineering.arc_furnace.recipeBuilder()
        .mainInput(silicon)
        .input(ore(doped.additive) * 8)
        .output(doped.output)
        .time(100)
        .energyPerTick(512)
        .alloying()
    .register()

    mods.enderio.alloy_smelter.recipeBuilder()
        .input(silicon, ore(doped.additive) * 8)
        .output(doped.output)
        .energy(2000)
        .tierAny()
    .register()

    mods.thermalexpansion.smelter.recipeBuilder()
        .input(silicon, ore(doped.additive) * 8)
        .output(doped.output)
        .energy(4000)
    .register()

    //80 infuse = 8 dusts (10 each)
    mods.mekanism.metallurgic_infuser.recipeBuilder()
        .input(silicon)
        .infuse(doped.infuse)
        .amount(80)
        .output(doped.output)
    .register()
}

//=================================================================================================
