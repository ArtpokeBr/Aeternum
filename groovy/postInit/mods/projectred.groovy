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

//Electrotine Ore Processing ======================================================================

//Electrotine Ore is only reached by generic ore->dust loops (flat 2x, no byproduct), and not at all
//by the machines whose loop needs an ingot. Give it Redstone Ore's yields in every machine that
//crushes Redstone Ore. Electrum (its GregTech byproduct) stands in where Redstone gets Cinnabar.
def electrotineOre = item('projectred-exploration:ore', 6)
def electrotine = item('projectred-core:resource_item', 105)
def electrumDust = item('thermalfoundation:material', 97)

//Thermal Pulverizer: Redstone Ore is 6 dust + 25% Cinnabar
mods.thermalexpansion.pulverizer.removeByInput(electrotineOre)
mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(ore('oreElectrotine'))
    .output(electrotine * 6, electrumDust)
    .chance(10)
    .energy(4000)
.register()

//Induction Smelter: Redstone Ore + Sand is 8 dust + 50% Rich Slag
mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('oreElectrotine'), ore('sand'))
    .output(electrotine * 8, item('thermalfoundation:material', 865))
    .chance(50)
    .energy(4000)
.register()

//Mekanism: Enrichment 1 ore -> 12 dust, Combiner 16 dust + Cobblestone -> 1 ore, as for Redstone
mods.mekanism.enrichment_chamber.recipeBuilder()
    .input(ore('oreElectrotine'))
    .output(electrotine * 12)
.register()

mods.mekanism.combiner.recipeBuilder()
    .input(ore('dustElectrotine') * 16)
    .extra(item('minecraft:cobblestone'))
    .output(electrotineOre)
.register()

//IE Crusher: Redstone Ore is 6 dust + 25% Cinnabar
mods.immersiveengineering.crusher.removeByInput(electrotineOre)
mods.immersiveengineering.crusher.recipeBuilder()
    .input(ore('oreElectrotine'))
    .output(electrotine * 6)
    .secondaryOutput(electrumDust, 0.1F)
    .energy(6000)
.register()

//Actually Additions Crusher: Redstone Ore is 10 dust
//removeByInput casts to ItemStack, so no ore() here
mods.actuallyadditions.crusher.removeByInput(electrotineOre)
mods.actuallyadditions.crusher.recipeBuilder()
    .input(ore('oreElectrotine'))
    .output(electrotine * 10)
.register()

//Extra Utilities 2 Crusher: Redstone Ore is 8 dust
mods.extrautils2.crusher.recipeBuilder()
    .input(ore('oreElectrotine'))
    .output(electrotine * 8)
    .energy(4000)
    .time(200)
.register()

//SAG Mill (Ender IO's sagmill_ores.xml): Redstone Ore is 8 dust + 20% dust + 80% Silicon + 15%
//Cobblestone, Nether Redstone Ore 21 dust + 2 at 20% + 2 Silicon at 80% + 15% Netherrack.
//Project Red's own Silicon (defined in Doped Silicon above), which Electrotine is already paired with.
mods.enderio.sag_mill.recipeBuilder()
    .input(ore('oreElectrotine'))
    .output(electrotine * 8)
    .output(electrotine, 0.2F)
    .output(silicon, 0.8F)
    .output(item('minecraft:cobblestone'), 0.15F)
    .energy(3600)
    .bonusTypeMultiply()
    .tierAny()
.register()

mods.enderio.sag_mill.recipeBuilder()
    .input(ore('oreNetherElectrotine'))
    .output(electrotine * 21)
    .output(electrotine * 2, 0.2F)
    .output(silicon * 2, 0.8F)
    .output(item('minecraft:netherrack'), 0.15F)
    .energy(3200)
    .bonusTypeMultiply()
    .tierAny()
.register()

//Blood Magic Alchemy Table: Redstone Ore + Basic Cutting Fluid is 8 dust, 400 LP, 200 ticks, tier 1
def cuttingFluid = item('bloodmagic:cutting_fluid', 0)
//Ore only: with several inputs, removeByInput keeps only the last one's match, so passing the
//Cutting Fluid too would remove every ore-doubling recipe
mods.bloodmagic.alchemy_table.removeByInput(electrotineOre)
mods.bloodmagic.alchemy_table.recipeBuilder()
    .input(ore('oreElectrotine'), cuttingFluid)
    .output(electrotine * 8)
    .syphon(400)
    .time(200)
    .tier(1)
.register()

//Astral Sorcery Grindstone: Redstone Ore is 4 dust (its generic loop gives other ores only 1)
mods.astralsorcery.grindstone.removeByInput(electrotineOre)
mods.astralsorcery.grindstone.recipeBuilder()
    .input(ore('oreElectrotine'))
    .output(electrotine * 4)
    .weight(12)
    .secondaryChance(0.85F)
.register()

//Thaumcraft: Redstone Ore has a 1% bonus nugget when smelted (Electrotine Ore smelts to 1 dust)
mods.thaumcraft.smelting_bonus.add(ore('oreElectrotine'), item('thermalfoundation:material', 225), 0.01F)

//Botania Orechid: Redstone is 6885; Electrotine is rarer, as a modded ore
mods.botania.orechid.add('oreElectrotine', 2000)

//=================================================================================================
