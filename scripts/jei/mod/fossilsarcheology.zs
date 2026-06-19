#modloaded fossil requious
#priority 950
#reloadable

import crafttweaker.item.IItemStack;

import scripts.jei.requious.add as addRecipe;

// -----------------------------------------------------------------------
// Fossil block drops - breaking <fossil:fossil> rolls random.nextInt(1100)
// and partitions the result into one of 15 possible items (decompiled from
// FossilBlock.func_180660_a). Display counts are that exact 0-1099 weight
// scaled down to a max of 64 (same convention as the Mineralis ritual tab
// below), with each entry's real percentage noted alongside for reference.
// -----------------------------------------------------------------------
var x = <assembly:fossil_drops>;
x.addJEICatalyst(<fossil:fossil>);

val fossilDropsList = [
  <minecraft:bone>          * 64, // 31.80% (350/1100)
  <fossil:biofossil>        * 50, // 25.00% (275/1100)
  <fossil:relic_scrap>      * 37, // 18.18% (200/1100)
  <fossil:plant_fossil>     * 37, // 18.18% (200/1100)
  <fossil:skull_block>      *  7, //  3.36% ( 37/1100)
  <fossil:bone_leg_bone>    *  2, //  0.82% (  9/1100)
  <fossil:scarab_gem>       *  1, //  0.09% (  1/1100)
  <fossil:broken_sword>     *  1, //  0.45% (  5/1100)
  <fossil:broken_helmet>    *  1, //  0.45% (  5/1100)
  <fossil:bone_skull>       *  1, //  0.27% (  3/1100)
  <fossil:bone_foot>             *  1, //  0.27% (  3/1100)
  <fossil:bone_vertebrae>        *  1, //  0.27% (  3/1100)
  <fossil:bone_arm_bone>         *  1, //  0.27% (  3/1100)
  <fossil:bone_ribcage>          *  1, //  0.27% (  3/1100)
] as IItemStack[];

var k = 0;
for _y in 0 .. fossilDropsList.length / 9 + 1 {
  for _x in 0 .. 9 {
    x.setJEIItemSlot(_x, _y, 'output' ~ k);
    k += 1;
  }
}

addRecipe(x, { []: fossilDropsList });
