#modloaded thaumcraft

//Material Extractor Recipe ========================================================================

mods.nuclearcraft.Extractor.addRecipe(<thaumcraft:cinderpearl>, <minecraft:blaze_powder> * 3, null, 1.0, 2.0, 0.0);

//=================================================================================================

//Crystal Cluster Crushing ========================================================================

//The GroovyScript machines are in groovy/postInit/mods/thaumcraft.groovy; these two have no compat there.
//1 Vis Crystal per meta 0 cluster, as when broken.
val crystalClusters = {
    "aer": <thaumcraft:crystal_aer>,
    "aqua": <thaumcraft:crystal_aqua>,
    "ignis": <thaumcraft:crystal_ignis>,
    "ordo": <thaumcraft:crystal_ordo>,
    "perditio": <thaumcraft:crystal_perditio>,
    "terra": <thaumcraft:crystal_terra>,
    "vitium": <thaumcraft:crystal_vitium>
} as crafttweaker.item.IItemStack[string];

for aspect, cluster in crystalClusters {
    val visCrystal = <thaumcraft:crystal_essence>.withTag({Aspects: [{key: aspect, amount: 1}]});

    //Direct item ids only: the Manufactory rejects ore-dict inputs
    mods.nuclearcraft.Manufactory.addRecipe(cluster, visCrystal, 1.0, 1.0, 0.0);

    moretweaker.railcraft.RockCrusher.add(cluster, [visCrystal], [1.0], 100);
}

//=================================================================================================
