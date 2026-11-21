#modloaded rockhounding_chemistry

// Rods: Profiling Bench -> IE Metal Press =========================================================
// Rods leave the Profiling Bench; groovy/postInit/mods/rockhound.groovy presses them in the Metal
// Press (Rod Mold) instead. The Profiling Bench has no GroovyScript compat, so its side lives here.
// Rockhounding's Iron Rod (misc_items:2) duplicates IE's stickIron: its crafting uses take any
// stickIron instead. Don't ore-dict it as stickIron: IE's preferredOres ranks rockhounding_chemistry
// above immersiveengineering, so IE's own stickIron outputs would switch to Rockhounding's rod.

val profilingBenchRods = [
    <rockhounding_chemistry:misc_items:2>,  // Iron Rod
    <rockhounding_chemistry:misc_items:14>, // Nichrome Rod
    <rockhounding_chemistry:misc_items:24>, // Copper Rod
    <rockhounding_chemistry:misc_items:25>, // Nib Rod
    <rockhounding_chemistry:misc_items:26>, // Cosm Rod
    <rockhounding_chemistry:misc_items:27>  // Hastelloy Rod
] as crafttweaker.item.IItemStack[];

for rod in profilingBenchRods {
    mods.rockhounding_chemistry.ProfilingBench.removeByOutput(rod);
}

recipes.replaceAllOccurences(<rockhounding_chemistry:misc_items:2>, <ore:stickIron>);

// ================================================================================================

// Coils: Profiling Bench -> IE Metal Press (Coil Mold) and IC2 Metal Former (Extruding) ============
// The Coil Pattern's recipes move to the pack's Coil Mold (aeternum:mold_coil) and the Extruder;
// both sides live in groovy/postInit/mods/rockhound.groovy.

val profilingBenchCoils = [
    <rockhounding_chemistry:misc_items:8>,  // Iron Coil
    <rockhounding_chemistry:misc_items:28>, // Nimonic Coil
    <rockhounding_chemistry:misc_items:7>   // Glass Pipe
] as crafttweaker.item.IItemStack[];

for coil in profilingBenchCoils {
    mods.rockhounding_chemistry.ProfilingBench.removeByOutput(coil);
}

// ================================================================================================

// Coins: removed from the Profiling Bench =========================================================
// Thermal Expansion's Compactor (Mint) already presses every coinX that has an ingotX, at the same
// 1 ingot -> 3 coins, so the Coin Pattern recipes go without a replacement. removeByPattern drops
// one recipe per call: 31 calls for the 31 EnumAlloyPart coins (Coin Pattern = EnumCasting 9).

for i in 0 .. 31 {
    mods.rockhounding_chemistry.ProfilingBench.removeByPattern(9);
}

// ================================================================================================

// Gears: removed from the Profiling Bench =========================================================
// The 31 EnumAlloyPart gears (blockX -> 2) are already made elsewhere from their ingots: IE Metal
// Press (Gear Mold), TE Compactor (Gear) and the UniDict gear template (scripts/mods/unidict.zs).
// The 32nd Gear Pattern recipe is the Crushing Gear (Iron Ingot -> 1), which moves to the Metal
// Press in groovy/postInit/mods/rockhound.groovy. Gear Pattern = EnumCasting 6; one per call.

for i in 0 .. 32 {
    mods.rockhounding_chemistry.ProfilingBench.removeByPattern(6);
}

// ================================================================================================

// Plates: removed from the Profiling Bench ========================================================
// The 31 EnumAlloyPart plates (ingotX -> 1) are already made 1:1 from their ingots in the IE Metal
// Press (Plate Mold) and the TE Compactor (Plate). Plate Pattern = EnumCasting 10; one per call.

for i in 0 .. 31 {
    mods.rockhounding_chemistry.ProfilingBench.removeByPattern(10);
}

// ================================================================================================

// Iron Arm: Profiling Bench -> IE Metal Press (Rod Mold) ==========================================
// The Arm Pattern's only recipe; groovy/postInit/mods/rockhound.groovy presses it from Iron Rods.

mods.rockhounding_chemistry.ProfilingBench.removeByOutput(<rockhounding_chemistry:misc_items:4>);

// ================================================================================================

// Casings: Profiling Bench -> IC2 recipes ==========================================================
// The 4 Casing Pattern recipes (blockX -> 20: Iron, Hydronalium, Hastelloy, Nimonic) go; casings are
// made the IC2 way in groovy/postInit/mods/rockhound.groovy. Rockhounding's Iron Casing
// (misc_items:5) duplicates IC2's Iron Item Casing, which takes over its crafting uses. IC2 casings
// have no ore-dict entry, so the swap is item to item. Casing Pattern = EnumCasting 5; one per call.

for i in 0 .. 4 {
    mods.rockhounding_chemistry.ProfilingBench.removeByPattern(5);
}

recipes.replaceAllOccurences(<rockhounding_chemistry:misc_items:5>, <ic2:casing:3>);

// ================================================================================================

// Foils: Profiling Bench -> IC2 Block Cutter and TE Sawmill =========================================
// The 3 Foil Pattern recipes (ingotX -> 8: Iron, Aluminum, Stellite) go; groovy/postInit/mods/
// rockhound.groovy saws plates into foils instead. Foil Pattern = EnumCasting 3; one per call.

for i in 0 .. 3 {
    mods.rockhounding_chemistry.ProfilingBench.removeByPattern(3);
}

// ================================================================================================
