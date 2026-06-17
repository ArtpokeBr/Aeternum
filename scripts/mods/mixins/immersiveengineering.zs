#modloaded immersiveengineering
#loader mixin

/*
Disable Arc Furnace recycling recipe generation.
In large modpacks IE iterates every tool/armor recipe at init to compute reclaimable
ingots, which takes several seconds and scales with mod count. With ~290 mods in
Aeternum this is a real chunk of loading time for a feature most players never notice.
By no-op'ing the thread's run() and finishUp() we skip the profiling entirely.
*/
#mixin {targets: "blusunrize.immersiveengineering.common.crafting.ArcRecyclingThreadHandler"}
zenClass MixinArcRecyclingThreadHandler {
    #mixin Overwrite
    function run() as void {
        // NO-OP: skip expensive recipe profiling
    }

    #mixin Overwrite
    function finishUp() as void {
        // NO-OP: do not inject recycling recipes
    }
}
