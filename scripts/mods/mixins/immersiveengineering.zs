#modloaded immersiveengineering
#loader mixin

import mixin.CallbackInfo;
import native.net.minecraft.entity.IMerchant;
import native.net.minecraft.item.ItemStack;
import native.net.minecraft.village.MerchantRecipeList;
import native.java.util.Random;

/*
Skip generating villager "core sample" trades. Building this trade list consumes
1-3 seconds of world load time for a minor, easily-missed villager trade.
*/
#mixin {targets: "blusunrize.immersiveengineering.common.util.IEVillagerHandler$OreveinMapForEmeralds"}
zenClass MixinIEVillagerHandler {
    #mixin Inject
    #{
    #   method: "func_190888_a",
    #   at: {value: "HEAD"},
    #   cancellable: true
    #}
    function skipCoreSampleTrades(merchant as IMerchant, recipeList as MerchantRecipeList, random as Random, ci as CallbackInfo) as void {
        ci.cancel();
    }
}

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
