// Unidict Adjustments ============================================================================

//Plate
mods.unidict.removalByKind.get("Crafting").remove("plate");

//Gear
mods.unidict.removalByKind.get("Crafting").remove("gear");
mods.unidict.api.newShapedRecipeTemplate("gear", 1, [
    [null,   "ingot",    null],
    ["ingot", <ore:gearBushing>, "ingot"],
    [null,    "ingot",   null]
]);

// ================================================================================================