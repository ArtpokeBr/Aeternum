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
