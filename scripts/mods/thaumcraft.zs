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

//Cinnabar Ore Processing =========================================================================

//The GroovyScript machines and the Quicksilver Dust ore dict are in groovy/postInit/mods/thaumcraft.groovy.

//Fluid Infuser: Thermal's Transposer recipe, Cinnabar Ore + 200 mB Cryotheum -> 4 Cinnabar
mods.nuclearcraft.Infuser.addRecipe(<ore:oreCinnabar>, <liquid:cryotheum> * 200, <thermalfoundation:material:866> * 4);

//Direct item ids only: the Manufactory rejects ore-dict inputs. Ores are 2 dust, ingots 1.
mods.nuclearcraft.Manufactory.addRecipe(<thaumcraft:ore_cinnabar>, <aeternum:quicksilver_dust> * 2, 1.0, 1.0, 0.0);
mods.nuclearcraft.Manufactory.addRecipe(<thaumcraft:quicksilver>, <aeternum:quicksilver_dust>, 1.0, 1.0, 0.0);

//Rock Crusher: 2 dust with GregTech's Cinnabar byproducts, Redstone then Sulfur
moretweaker.railcraft.RockCrusher.add(<thaumcraft:ore_cinnabar>, [<aeternum:quicksilver_dust> * 2, <minecraft:redstone>, <thermalfoundation:material:771>], [1.0, 0.1, 0.05], 100);

//=================================================================================================
