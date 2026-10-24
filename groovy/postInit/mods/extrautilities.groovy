
// Yeeting Furnace ====================================================================

mods.jei.ingredient.hide(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
crafting.removeByOutput(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
mods.extrautils2.furnace.removeAll()

// Block Patterns JEI Tab =========================================================================

// XU2 registers the "Blocks" tab (mill build patterns) with no icon or catalyst; the first catalyst becomes the tab icon.
// Water Mill, Lava Mill, Fire Mill, Dragon Egg Mill
for (meta in [3, 2, 5, 8]) {
    mods.jei.catalyst.add('extrautils2.blockPatterns', item('extrautils2:passivegenerator', meta))
}

//=================================================================================================