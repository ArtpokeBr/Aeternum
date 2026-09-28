#modloaded thermalfoundation

// Gem Gears with Bushing ==========================================================================
// UniDict can't handle these: it only builds its gear templates for metals discovered through ingot
// ore entries, and its CraftTweaker templates never see "custom unified resources" like gems.

// Diamond Gear
recipes.remove(<thermalfoundation:material:26>);
recipes.addShaped("thermalfoundation_gear_diamond_bushing", <thermalfoundation:material:26>, [
    [null,              <ore:gemDiamond>,  null],
    [<ore:gemDiamond>,  <ore:gearBushing>, <ore:gemDiamond>],
    [null,              <ore:gemDiamond>,  null]
]);

// Emerald Gear
recipes.remove(<thermalfoundation:material:27>);
recipes.addShaped("thermalfoundation_gear_emerald_bushing", <thermalfoundation:material:27>, [
    [null,              <ore:gemEmerald>,  null],
    [<ore:gemEmerald>,  <ore:gearBushing>, <ore:gemEmerald>],
    [null,              <ore:gemEmerald>,  null]
]);

// ================================================================================================
