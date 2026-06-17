#priority 950
#modloaded forestry requious

import crafttweaker.item.IItemStack;
import mods.requious.AssemblyRecipe;

// JEI catalyst tab on Forestry's Farm blocks documenting which fertilizers work and how many
// are needed relative to Forestry's Fertilizer Compound (the strongest entry in farm.cfg).
// Weights recalculated against this pack's own config/forestry/farm.cfg fertilizers.items list
// (forestry:fertilizer_compound;500, ic2:crop_res:2;250, actuallyadditions:item_fertilizer;250,
// industrialforegoing:fertilizer;250) rather than copying E2EE's, since their config has
// different items/values.
val x = <assembly:forestry_farm>;
x.setJEIItemSlot(0, 0, 'item_in');
x.setJEIDurationSlot(1, 0, 'duration', scripts.jei.requious.getVisSlots(11, 1));
for i in 0 .. 11 {
  x.addJEICatalyst(<forestry:ffarm:3>.withTag({ FarmBlock: i }));
}

static fertilizers as IItemStack[] = [
  <forestry:fertilizer_compound> * 3,
  <ic2:crop_res:2> * 6,
  <actuallyadditions:item_fertilizer> * 6,
  <industrialforegoing:fertilizer> * 6,
];

for input in fertilizers {
  x.addJEIRecipe(AssemblyRecipe.create(function (container) {})
    .requireItem('item_in', input)
  );
}
