#modloaded deepmoblearning requious
#priority 950
#reloadable

import scripts.jei.requious.add as addRecipe;

// -----------------------------------------------------------------------
// Left-click on block - Deep Mob Learning crafts these when a player left-clicks
// a block while holding the input (PlayerEventHandler.playerLeftClickedBlock),
// one held item per click. DML shows neither in JEI.
// Slots: held item, clicked block -> output.
// -----------------------------------------------------------------------
var x = <assembly:left_click_block>;
x.addJEICatalyst(<minecraft:coal_block>);
x.addJEICatalyst(<minecraft:obsidian>);
x.setJEIItemSlot(0, 0, 'input0');
x.setJEIItemSlot(1, 0, 'input1');
scripts.jei.requious.arrow(x, 2, 0);
x.setJEIItemSlot(3, 0, 'output0');

addRecipe(x, {
  [<minecraft:redstone>, <minecraft:coal_block>]: [<deepmoblearning:soot_covered_redstone>],
  [<deepmoblearning:glitch_heart>, <minecraft:obsidian>]: [<deepmoblearning:glitch_fragment> * 3]
});
