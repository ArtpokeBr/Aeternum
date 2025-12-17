
// Yeeting Furnace and Crusher ====================================================================

mods.jei.ingredient.hide(item('extrautils2:machine').withNbt(['Type': 'extrautils2:crusher']))
crafting.removeByOutput(item('extrautils2:machine').withNbt(['Type': 'extrautils2:crusher']))
mods.extrautils2.crusher.removeAll()
mods.jei.ingredient.hide(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
crafting.removeByOutput(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
mods.extrautils2.furnace.removeAll()

//=================================================================================================