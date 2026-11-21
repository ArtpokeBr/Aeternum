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
