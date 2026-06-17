#modloaded ic2 requious
#priority 950

import crafttweaker.item.IItemStack;
import crafttweaker.liquid.ILiquidStack;
import mods.requious.AssemblyRecipe;
import mods.requious.SlotVisual;

import scripts.jei.requious.add as addRecipe;

// -----------------------------------------------------------------------
// Liquid Heat Exchanger - lava/hot coolant cooling
// -----------------------------------------------------------------------
var x = <assembly:liquid_interaction>;
x.addJEICatalyst(<ic2:te:31>);
x.setJEIFluidSlot(0, 0, 'liquid_input');
x.setJEIItemSlot(1, 0, 'item_in');
x.setJEIDurationSlot(2, 0, 'duration', scripts.jei.requious.getVisGauge(5, 1));
x.setJEIFluidSlot(3, 0, 'liquid_output');

for l_in, l_out in {
  lava          : 'ic2pahoehoe_lava',
  ic2hot_coolant: 'ic2coolant',
} as string[string] {
  x.addJEIRecipe(AssemblyRecipe.create(function (container) {
    container.addFluidOutput('liquid_output', game.getLiquid(l_out));
  })
    .requireFluid('liquid_input', game.getLiquid(l_in))
    .requireItem('item_in', <ic2:crafting:7> * 10)
  );
}

// -----------------------------------------------------------------------
// Crop-Matron / Crystal Memory Chip - IC2 crop database reference
// -----------------------------------------------------------------------
x = <assembly:ic2_crops>;
x.addJEICatalyst(<ic2:crop_stick>);
x.addJEICatalyst(<ic2:te:58>);
x.addJEICatalyst(<ic2:crop_seed_bag>);
x.setJEIDurationSlot(2, 0, 'duration', scripts.jei.requious.getVisSlots(11, 1));
x.setJEIItemSlot(1, 0, 'input1', scripts.jei.requious.getVisSlots(0, 2));
scripts.jei.requious.addInsOuts(x, [[0, 0]], [[3, 0], [4, 0], [5, 0], [6, 0]]);

static glyphs as IItemStack[] = [
  <openblocks:glyph:48>, <openblocks:glyph:49>, <openblocks:glyph:50>,
  <openblocks:glyph:51>, <openblocks:glyph:52>, <openblocks:glyph:53>,
  <openblocks:glyph:54>, <openblocks:glyph:55>, <openblocks:glyph:56>,
  <openblocks:glyph:57>, <openblocks:glyph:65>, <openblocks:glyph:66>,
  <openblocks:glyph:67>, <openblocks:glyph:68>, <openblocks:glyph:69>,
  <openblocks:glyph:70>,
] as IItemStack[];

function addCrop(id as string, drops as IItemStack[], tier as int, attributes as string, requirments as string = null) as void {
  val seed = <ic2:crop_seed_bag>.withTag({ owner: 'ic2', scan: 1 as byte, growth: 1 as byte, id: id, resistance: 1 as byte, gain: 1 as byte });
  addRecipe(<assembly:ic2_crops>, { [
    isNull(requirments) ? seed : seed.withLore(['§6§lRequirments: ', requirments]),
    glyphs[tier].withDisplayName('§bTier: ' ~ tier).withLore(['§e§lAttributes: ', '§e' ~ attributes]),
  ]: drops });
}

//    Name           | Drops                                                          | Tier | Attributes                                  | "Special requirements"                                                                                                                            |
addCrop('weed'          , [<ic2:crop_res:5>]                                              , 0 , 'Weed Bad'                                    , '§6Can only be harvested with §nWeeding Trowel§r§6; Takes over neighboring plants and spreads grass');
addCrop('beetroots'     , [<minecraft:beetroot>]                                          , 1 , 'Red Food Beetroot');
addCrop('pumpkin'       , [<minecraft:pumpkin>]                                           , 1 , 'Orange Decoration Stem');
addCrop('wheat'         , [<minecraft:wheat>]                                             , 1 , 'Yellow Food Wheat'                           , '§6Light level of at least 9');
addCrop('blackthorn'    , [<minecraft:dye>]                                               , 2 , 'Black Flower Rose'                           , '§6Light level of at least 12');
addCrop('brown_mushroom', [<minecraft:brown_mushroom>]                                    , 2 , 'Brown Food Mushroom'                         , '§6Must be hydrated');
addCrop('carrots'       , [<minecraft:carrot>]                                            , 2 , 'Orange Food Carrots'                         , '§6Light level of at least 9');
addCrop('cyazint'       , [<minecraft:dye:6>]                                             , 2 , 'Blue Flower'                                 , '§6Light level of at least 12');
addCrop('dandelion'     , [<minecraft:dye:11>]                                            , 2 , 'Yellow Flower'                               , '§6Light level of at least 12');
addCrop('flax'          , [<minecraft:string>]                                            , 2 , 'Silk Vine Addictive');
addCrop('melon'         , [<minecraft:melon>,<minecraft:melon_block>]                     , 2 , 'Green Food Stem');
addCrop('potato'        , [<minecraft:potato>,<minecraft:poisonous_potato>]               , 2 , 'Yellow Food Potato'                          , '§6Light level of at least 9; Must be harvested early or it will drop §nPoisonous Potato');
addCrop('red_mushroom'  , [<minecraft:red_mushroom>]                                      , 2 , 'Red Food Mushroom'                           , '§6Must be hydrated');
addCrop('reed'          , [<minecraft:reeds>]                                             , 2 , 'Reed');
addCrop('rose'          , [<minecraft:dye:1>]                                             , 2 , 'Red Flower Rose'                             , '§6Light level of at least 12');
addCrop('tulip'         , [<minecraft:dye:5>]                                             , 2 , 'Purple Flower Tulip'                         , '§6Light level of at least 12');
addCrop('cocoa'         , [<minecraft:dye:3>]                                             , 3 , 'Brown Food Stem'                             , '§6Nutrients of at least 3');
addCrop('venomilia'     , [<minecraft:dye:5>,<ic2:crop_res:3>]                            , 3 , 'Purple Flower Tulip Poison'                  , '§6Light level of at least 12; Only drops §nGrin Powder§r§6 during second to last stage; at which point the plant is poisonous to touch unless sneaking');
addCrop('stickreed'     , [<minecraft:reeds>,<ic2:misc_resource:4>]                       , 4 , 'Reed Resin'                                  , '§6Drops §nSticky Resin§r§6 only when mature');
addCrop('corpse_plant'  , [<minecraft:rotten_flesh>,<minecraft:dye:15>,<minecraft:bone>]  , 5 , 'Toxic Undead Vine Edible Rotten');
addCrop('hops'          , [<ic2:crop_res:4>]                                              , 5 , 'Green Ingredient Wheat'                      , '§6Light level of at least 9');
addCrop('nether_wart'   , [<minecraft:nether_wart>]                                       , 5 , 'Red Nether Ingredient Soulsand'              , '§6Grows faster with §nSoul Sand§r§6 under it; Turns into §nTerra Wart§r§6 if there is Snow under it');
addCrop('terra_wart'    , [<ic2:terra_wart>]                                              , 5 , 'Blue Aether Consumable Snow'                 , '§6Grows faster with §nSnow§r§6 under it; Turns into §nNether Wart§r§6 if there is §nSoul Sand§r§6 under it');
addCrop('aurelia'       , [<ic2:dust:20>]                                                 , 6 , 'Gold Leaves Metal'                           , '§6Only matures if there is a §nGold Ore§r§6 or §nBlock of Gold§r§6 below it');
addCrop('blazereed'     , [<ic2:dust:16>, <minecraft:blaze_rod>, <minecraft:blaze_powder>], 6 , 'Fire Blaze Reed Sulfur');
addCrop('corium'        , [<minecraft:leather>]                                           , 6 , 'Cow Silk Vine');
addCrop('stagnium'      , [<ic2:dust:28>]                                                 , 6 , 'Shiny Leaves Metal'                          , '§6Only matures if there is an §nTin Ore§r§6 or §nTin Block§r§6 below it');
addCrop('cyprium'       , [<ic2:dust:19>]                                                 , 6 , 'Copper Leaves Metal'                         , '§6Only matures if there is an §nCopper Ore§r§6 or §nCopper Block§r§6 below it');
addCrop('eatingplant'   , [<minecraft:cactus>, <minecraft:rotten_flesh>]                                            , 6 , 'Bad Food'                                    , '§6Light level of at least 10; Only matures if §nLava§r§6 is below it; Will attack the player if not wearing metal armor and drop §nRotten Flesh');
addCrop('egg_plant'     , [<minecraft:egg>,<minecraft:chicken>,<minecraft:feather>]       , 6 , 'Chicken Egg Edible Feather Flower Addictive');
addCrop('ferru'         , [<ic2:dust:21>]                                                 , 6 , 'Gray Leaves Metal'                           , '§6Only matures if there is an §nIron Ore§r§6 or §nBlock of Iron§r§6 below it');
addCrop('milk_wart'     , [<ic2:crop_res:6>]                                              , 6 , 'Edible Milk Cow');
addCrop('plumbiscus'    , [<ic2:dust:23>]                                                 , 6 , 'Lead Leaves Metal'                           , '§6Only matures if there is an §nLead Ore§r§6 or §nLead Block§r§6 below it');
addCrop('redwheat'      , [<minecraft:redstone>,<minecraft:wheat>]                        , 6 , 'Red Redstone Wheat'                          , '§6Light level between 5 and 10 inclusive; Drops §nRedstone§r§6 only when powered by redstone');
addCrop('shining'       , [<ic2:dust:26>]                                                 , 6 , 'Silver Leaves Metal'                         , '§6Only matures if there is an §nSilver Block§r§6 or §nSilver Ore§r§6 below it');
addCrop('slime_plant'   , [<minecraft:slime_ball>]                                        , 6 , 'Slime Bouncy Sticky Bush');
addCrop('spidernip'     , [<minecraft:string>,<minecraft:web>,<minecraft:spider_eye>]     , 7 , 'Silk Vine Addictive');
addCrop('coffee'        , [<ic2:crop_res>]                                                , 7 , 'Leaves Ingredient Beans'                     , '§6Light level of at least 9');
addCrop('creeper_weed'  , [<minecraft:gunpowder>]                                         , 7 , 'Creeper Vine Explosive Fire Sulfur Saltpeter Coal');
addCrop('meat_rose'     , [<minecraft:dye:9>,<minecraft:chicken>,<minecraft:beef>,<minecraft:porkchop>,<minecraft:fish>] , 7 , 'Edible Flower Cow Fish Chicken Pig');
addCrop('tearstalks'    , [<minecraft:ghast_tear>]                                        , 8 , 'Healing Nether Ingredient Reed Ghast');
addCrop('withereed'     , [<ic2:dust:2>,<minecraft:coal>]                                 , 8 , 'Fire Undead Reed Coal Rotten Wither');
addCrop('oil_berries'   , [<ic2:crop_res:7>]                                              , 9 , 'Fire Dark Reed Rotten Coal Oil');
addCrop('ender_blossom' , [<ic2:dust:31>,<minecraft:ender_pearl>,<minecraft:ender_eye>]   , 10, 'Ender Flower Shiny');
addCrop('bobs_yer_uncle_ranks_berries', [<ic2:crop_res:8>, <minecraft:emerald>]           , 11, 'Shiny Vine Emerald Berylium Crystal');
addCrop('diareed'       , [<ic2:dust:36>,<minecraft:diamond>]                             , 12, 'Fire Shiny Reed Coal Diamond Crystal');

// -----------------------------------------------------------------------
// Semi-Fluid Generator
// -----------------------------------------------------------------------
x = <assembly:ic2_semifluid>;
x.addJEICatalyst(<ic2:te:7>);
x.setJEIFluidSlot(0, 0, 'fluid_in');
x.setJEIDurationSlot(1, 0, 'duration', SlotVisual.arrowRight());
x.setJEIEnergySlot(2, 0, 'energy_out', 'rf');

function semifluid(input as ILiquidStack, eu_t as double) as void {
  <assembly:ic2_semifluid>.addJEIRecipe(AssemblyRecipe.create(function (c) {
    c.addEnergyOutput('energy_out', eu_t * 4, 0);
  })
    .requireFluid('fluid_in', input)
  );
}

semifluid(<fluid:biomass>, 8);
semifluid(<fluid:ic2biogas>, 16);
semifluid(<fluid:bio.ethanol>, 32);
