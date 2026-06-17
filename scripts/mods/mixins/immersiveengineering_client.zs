#modloaded immersiveengineering
#loader mixin
#sideonly client

import native.blusunrize.lib.manual.IManualPage;
import native.blusunrize.lib.manual.ManualPages;

/*
Skip building the 3x3 crafting-grid preview on Engineer's Manual recipe pages.
IE scans the entire crafting-table recipe registry per manual page to render
that preview, which costs several seconds of load time with this many mods
installed. The page text stays - only the little recipe-grid image is skipped.
*/
#mixin {targets: "blusunrize.lib.manual.ManualInstance"}
zenClass MixinManualInstance {
    #mixin Redirect
    #{
    #   method: "indexRecipes",
    #   at: {
    #       value: "INVOKE",
    #       target: "Lblusunrize/lib/manual/IManualPage;recalculateCraftingRecipes()V"
    #   }
    #}
    function removeCraftingRecipes(page as IManualPage) as void {
        // NO-OP
    }
}

#mixin {targets: "blusunrize.lib.manual.ManualPages$Crafting"}
zenClass MixinManualPagesCrafting {
    #mixin Redirect
    #{
    #   method: "<init>",
    #   at: {
    #       value: "INVOKE",
    #       target: "Lblusunrize/lib/manual/ManualPages$Crafting;recalculateCraftingRecipes()V"
    #   }
    #}
    function removeCraftingRecipes(page as ManualPages.Crafting) as void {
        // NO-OP
    }
}

#mixin {targets: "blusunrize.lib.manual.ManualPages$CraftingMulti"}
zenClass MixinManualPagesCraftingMulti {
    #mixin Redirect
    #{
    #   method: "<init>",
    #   at: {
    #       value: "INVOKE",
    #       target: "Lblusunrize/lib/manual/ManualPages$CraftingMulti;recalculateCraftingRecipes()V"
    #   }
    #}
    function removeCraftingRecipes(page as ManualPages.CraftingMulti) as void {
        // NO-OP
    }
}
