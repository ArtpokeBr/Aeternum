
// Yeeting Furnace ====================================================================

mods.jei.ingredient.hide(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
crafting.removeByOutput(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
mods.extrautils2.furnace.removeAll()

//=================================================================================================