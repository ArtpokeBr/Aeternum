// mods_loaded: thaumcraft

//IC2 Extractor Recipe for Cinder Pearl ===========================================================

mods.ic2.extractor.add(item('thaumcraft:cinderpearl'), item('minecraft:blaze_powder') * 3)

//=================================================================================================

//Crystal Cluster Crushing ========================================================================

//The Environmental Tech Void Ore Miner outputs Thaumcraft crystal clusters as blocks (meta 0), which
//only give their Vis Crystal when placed and broken. Crush them instead, 1 crystal per cluster as
//when broken (BlockCrystal drops growth + 1). Meta 0 only: higher growth metas drop more crystals.
//The NuclearCraft Manufactory and Railcraft Rock Crusher are in scripts/mods/thaumcraft.zs.
def crystalClusters = [
    'aer': 'crystal_aer',
    'aqua': 'crystal_aqua',
    'ignis': 'crystal_ignis',
    'ordo': 'crystal_ordo',
    'perditio': 'crystal_perditio',
    'terra': 'crystal_terra',
    'vitium': 'crystal_vitium',
]

for (entry in crystalClusters) {
    def cluster = item("thaumcraft:${entry.value}", 0)
    def visCrystal = crystal(entry.key)

    mods.thermalexpansion.pulverizer.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
        .energy(2000)
    .register()

    mods.mekanism.crusher.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
    .register()

    mods.immersiveengineering.crusher.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
        .energy(2000)
    .register()

    mods.actuallyadditions.crusher.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
    .register()

    mods.extrautils2.crusher.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
        .energy(2000)
        .time(100)
    .register()

    //No grinding-ball bonus, so balls can't multiply crystals
    mods.enderio.sag_mill.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
        .energy(2000)
        .bonusTypeNone()
        .tierAny()
    .register()

    //Macerator takes the output first (the Extractor above takes the input first)
    mods.ic2.macerator.add(visCrystal, cluster)

    mods.appliedenergistics2.grinder.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
        .turns(4)
    .register()

    mods.astralsorcery.grindstone.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
        .weight(5)
    .register()

    mods.integrateddynamics.squeezer.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
    .register()

    mods.integrateddynamics.mechanical_squeezer.recipeBuilder()
        .input(cluster)
        .output(visCrystal)
    .register()
}

//=================================================================================================

//Cinnabar Ore Processing =========================================================================

//Cinnabar Ore only smelted to 1 Quicksilver: no dustCinnabar/ingotCinnabar exists, so every generic
//ore loop skips it. Quicksilver Dust is its dust stage, at Iron Ore's yields in each machine, with
//GregTech's Cinnabar byproducts (Redstone first, Sulfur second). Named dustQuicksilver, not
//dustCinnabar, so the generic loops don't add flat recipes of their own next to these.
//The NuclearCraft and Railcraft parts are in scripts/mods/thaumcraft.zs.
def cinnabarOre = item('thaumcraft:ore_cinnabar')
def quicksilver = item('thaumcraft:quicksilver')
def quicksilverDust = item('aeternum:quicksilver_dust')
def sulfurDust = item('thermalfoundation:material', 771)
def slag = item('thermalfoundation:material', 864)
def richSlag = item('thermalfoundation:material', 865)
def thermalCinnabar = item('thermalfoundation:material', 866)

oreDict.add('dustQuicksilver', quicksilverDust)

//Smelting: 1 dust -> 1 Quicksilver, as the ore smelts
furnace.add(ore('dustQuicksilver'), quicksilver, 0.2)
mods.thermalexpansion.furnace.add(2000, ore('dustQuicksilver'), quicksilver)

//Thermal Pulverizer: Iron Ore is 2 dust + 10% secondary, 4000 RF; ingot -> dust 2000 RF
mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(ore('oreCinnabar'))
    .output(quicksilverDust * 2, item('minecraft:redstone'))
    .chance(10)
    .energy(4000)
.register()

mods.thermalexpansion.pulverizer.recipeBuilder()
    .input(ore('quicksilver'))
    .output(quicksilverDust)
    .energy(2000)
.register()

//Induction Smelter: Iron Ore + Sand is 2 ingots + 5% Rich Slag, + Rich Slag 3 + 75% Slag,
//+ Cinnabar 3 + 75% Rich Slag; dust + Sand is 1 ingot + 25% Slag
mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('oreCinnabar'), ore('sand'))
    .output(quicksilver * 2, richSlag)
    .chance(5)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('oreCinnabar'), richSlag)
    .output(quicksilver * 3, slag)
    .chance(75)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('oreCinnabar'), thermalCinnabar)
    .output(quicksilver * 3, richSlag)
    .chance(75)
    .energy(4000)
.register()

mods.thermalexpansion.smelter.recipeBuilder()
    .input(ore('dustQuicksilver'), ore('sand'))
    .output(quicksilver, slag)
    .chance(25)
    .energy(1000)
.register()

//Mekanism: Enrichment 1 ore -> 2 dust, Combiner 8 dust + Cobblestone -> 1 ore, Crusher ingot -> dust
mods.mekanism.enrichment_chamber.recipeBuilder()
    .input(ore('oreCinnabar'))
    .output(quicksilverDust * 2)
.register()

mods.mekanism.combiner.recipeBuilder()
    .input(ore('dustQuicksilver') * 8)
    .extra(item('minecraft:cobblestone'))
    .output(cinnabarOre)
.register()

mods.mekanism.crusher.recipeBuilder()
    .input(ore('quicksilver'))
    .output(quicksilverDust)
.register()

//IE Crusher: ores are 2 dust + secondaries, 6000 RF; ingot -> dust 3600 RF
mods.immersiveengineering.crusher.recipeBuilder()
    .input(ore('oreCinnabar'))
    .output(quicksilverDust * 2)
    .secondaryOutput(item('minecraft:redstone'), 0.1F)
    .secondaryOutput(sulfurDust, 0.05F)
    .energy(6000)
.register()

mods.immersiveengineering.crusher.recipeBuilder()
    .input(ore('quicksilver'))
    .output(quicksilverDust)
    .energy(3600)
.register()

//Actually Additions Crusher: ores are 2 dust
mods.actuallyadditions.crusher.recipeBuilder()
    .input(ore('oreCinnabar'))
    .output(quicksilverDust * 2, item('minecraft:redstone'))
    .chance(10)
.register()

mods.actuallyadditions.crusher.recipeBuilder()
    .input(ore('quicksilver'))
    .output(quicksilverDust)
.register()

//Extra Utilities 2 Crusher: ores are 2 dust
mods.extrautils2.crusher.recipeBuilder()
    .input(ore('oreCinnabar'))
    .output(quicksilverDust * 2)
    .energy(4000)
    .time(200)
.register()

mods.extrautils2.crusher.recipeBuilder()
    .input(ore('quicksilver'))
    .output(quicksilverDust)
    .energy(2000)
    .time(100)
.register()

//SAG Mill (Ender IO's sagmill_ores.xml): Iron Ore is 2 dust + 5% and 10% byproducts + 15%
//Cobblestone, 3600 RF; Iron Ingot is 1 dust, 2400 RF, no grinding-ball bonus
mods.enderio.sag_mill.recipeBuilder()
    .input(ore('oreCinnabar'))
    .output(quicksilverDust * 2)
    .output(item('minecraft:redstone'), 0.1F)
    .output(sulfurDust, 0.05F)
    .output(item('minecraft:cobblestone'), 0.15F)
    .energy(3600)
    .bonusTypeMultiply()
    .tierAny()
.register()

mods.enderio.sag_mill.recipeBuilder()
    .input(ore('quicksilver'))
    .output(quicksilverDust)
    .energy(2400)
    .bonusTypeNone()
    .tierAny()
.register()

//IC2 Macerator: output first; ores are 2 dust
mods.ic2.macerator.add(quicksilverDust * 2, ore('oreCinnabar'))
mods.ic2.macerator.add(quicksilverDust, ore('quicksilver'))

//Blood Magic Alchemy Table: ore + Basic Cutting Fluid is 2 dust, 400 LP, 200 ticks, tier 1
mods.bloodmagic.alchemy_table.recipeBuilder()
    .input(ore('oreCinnabar'), item('bloodmagic:cutting_fluid', 0))
    .output(quicksilverDust * 2)
    .syphon(400)
    .time(200)
    .tier(1)
.register()

//Infernal Furnace: Thaumcraft gives oreCinnabar both Quicksilver Drops (33%) and Rare Earth (2.5%).
//Keep only the Quicksilver Drops. removeByOutput would strip Rare Earth from every ore, so filter on
//the input, which Thaumcraft stores as the ore-dict name string.
def rareEarth = item('thaumcraft:nugget', 10)
mods.thaumcraft.smelting_bonus.streamRecipes()
    .filter { it.in == 'oreCinnabar' && rareEarth.isItemEqual(it.out) }
    .removeAll()

//Astral Sorcery Grindstone: its generic ore loop gives 1 dust with a second-dust chance
mods.astralsorcery.grindstone.recipeBuilder()
    .input(ore('oreCinnabar'))
    .output(quicksilverDust)
    .weight(12)
    .secondaryChance(0.85F)
.register()

//=================================================================================================
