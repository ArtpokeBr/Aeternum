#modloaded astralsorcery requious
#priority 950
#reloadable

import crafttweaker.item.IItemStack;
import crafttweaker.liquid.ILiquidStack;
import mods.requious.AssemblyRecipe;

import scripts.jei.requious.add as addRecipe;

// -----------------------------------------------------------------------
// Evershifting Fountain (Neromantic Prime) - random fluid gift ritual.
// Display counts mirror this pack's own config/astralsorcery/fluid_rarities.cfg rarity
// values (higher = more common), not E2EE's list, since the installed fluids differ.
// Water is excluded - its guaranteed amount is Integer.MAX, a sentinel for "always
// available" rather than a meaningful ritual reward.
// -----------------------------------------------------------------------
var x = <assembly:neromantic_prime>;
x.addJEICatalyst(<astralsorcery:blockbore>);
x.addJEICatalyst(<astralsorcery:blockborehead>);
x.addJEICatalyst(<astralsorcery:blockchalice>);
x.setJEIFluidSlot(0, 0, 'fluid_out');

static neromantic_prime_fluids as ILiquidStack[] = [
  <fluid:lava>              * 7500,
  <fluid:mana>               * 1500,
  <fluid:iron>                * 900,
  <fluid:crystaloil>           * 800,
  <fluid:gold>                 * 600,
  <fluid:ic2biomass>            * 600,
  <fluid:refined_oil>          * 600,
  <fluid:refined_fuel>         * 550,
  <fluid:ic2biogas>            * 500,
  <fluid:redstone>             * 500,
  <fluid:glowstone>            * 500,
  <fluid:ender>                * 250,
  <fluid:pyrotheum>            * 200,
  <fluid:cryotheum>            * 200,
  <fluid:empoweredoil>         * 200,
  <fluid:cobalt>                * 80,
  <fluid:ardite>                * 80,
  <fluid:emerald>               * 30,
  <fluid:ic2uu_matter>           * 1,
] as ILiquidStack[];

for i, output in neromantic_prime_fluids {
  x.addJEIRecipe(AssemblyRecipe.create(function (container) {
    container.addFluidOutput('fluid_out', output);
  }));
}

// -----------------------------------------------------------------------
// Mineralis ritual - random ore gift.
// Display counts mirror this pack's own config/astralsorcery/mineralis_ritual.cfg
// weights, scaled to a max of 64. That cfg turns out to be Astral Sorcery's static
// default list, not actually validated against installed mods, so two entries are
// dropped here: oreMithril (weight 1, negligible, Tinkers' Construct Antique may not
// register it) and oreYellorite (confirmed absent - this NuclearCraft build has no
// such tag at all, which is what crashed this whole script with a null firstItem).
// -----------------------------------------------------------------------
x = <assembly:mineralis>;
x.addJEICatalyst(<astralsorcery:blockritualpedestal>);
x.addJEICatalyst(<astralsorcery:itemtunedcelestialcrystal>.withTag({ astralsorcery: { constellationName: 'astralsorcery.constellation.mineralis', crystalProperties: { collectiveCapability: 100, size: 900, fract: 0, purity: 100, sizeOverride: -1 } } }));
x.addJEICatalyst(<astralsorcery:itemtunedrockcrystal>.withTag({ astralsorcery: { constellationName: 'astralsorcery.constellation.mineralis', crystalProperties: { collectiveCapability: 100, size: 400, fract: 0, purity: 100, sizeOverride: -1 } } }));

val mineralisList = [
  <ore:oreCoal>.firstItem * 64,
  <ore:oreIron>.firstItem * 30,
  <ore:oreTin>.firstItem * 18,
  <ore:oreCopper>.firstItem * 13,
  <ore:oreLead>.firstItem * 12,
  <ore:oreOsmium>.firstItem * 11,
  <ore:oreRedstone>.firstItem * 8,
  <ore:oreSulfur>.firstItem * 7,
  <ore:oreAluminum>.firstItem * 7,
  <ore:oreGold>.firstItem * 6,
  <ore:oreUranium>.firstItem * 6,
  <ore:oreCertusQuartz>.firstItem * 6,
  <ore:oreRuby>.firstItem * 4,
  <ore:oreSapphire>.firstItem * 4,
  <ore:oreZinc>.firstItem * 3,
  <ore:oreNickel>.firstItem * 3,
  <ore:oreDiamond>.firstItem * 2,
  <ore:oreSilver>.firstItem * 2,
  <ore:oreLapis>.firstItem * 1,
  <ore:oreEmerald>.firstItem * 1,
  <ore:orePlatinum>.firstItem * 1,
] as IItemStack[];

var k = 0;
for _y in 0 .. mineralisList.length / 9 + 1 {
  for _x in 0 .. 9 {
    x.setJEIItemSlot(_x, _y, 'output' ~ k);
    k += 1;
  }
}

addRecipe(x, { []: mineralisList });
