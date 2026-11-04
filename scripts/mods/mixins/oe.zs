#modloaded oe
#loader mixin
#sideonly client

import native.java.lang.Object;
import native.net.minecraft.item.ItemStack;
import native.mezz.jei.api.IModRegistry;
import native.mezz.jei.api.ingredients.IIngredientBlacklist;
import native.mezz.jei.api.recipe.IIngredientType;

/*
Oceanic Expanse's HEI plugin adds info pages for blocks the config can turn off without checking
first. The pack sets enableCoconut=false, so Coconut and Flowering Palm Leaves are never registered,
new ItemStack(block) is empty, and HEI's addIngredientInfo throws "Invalid ingredient found". HEI then
drops the whole plugin: no info pages for Barnacle Shells, Conduit, Guardian Spike, Sea Oats, Sea
Pickle or Shell Sand, and the Coquina Brick and Palm double slabs are left in the item list.

Skip empty stacks instead. getCount() is 0 for an empty stack.
*/
#mixin {targets: "com.sirsquidly.oe.compat.CompatJEI"}
zenClass MixinOECompatJEI {
    #mixin Redirect
    #{
    #    method: "register",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lmezz/jei/api/IModRegistry;addIngredientInfo(Ljava/lang/Object;Lmezz/jei/api/recipe/IIngredientType;[Ljava/lang/String;)V"
    #    }
    #}
    function skipEmptyInfo(registry as IModRegistry, ingredient as Object, type as IIngredientType, keys as string[]) as void {
        if (ingredient instanceof ItemStack && (ingredient as ItemStack).getCount() <= 0) {
            return;
        }
        registry.addIngredientInfo(ingredient, type, keys);
    }

    #mixin Redirect
    #{
    #    method: "register",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lmezz/jei/api/ingredients/IIngredientBlacklist;addIngredientToBlacklist(Ljava/lang/Object;)V"
    #    }
    #}
    function skipEmptyBlacklist(blacklist as IIngredientBlacklist, ingredient as Object) as void {
        if (ingredient instanceof ItemStack && (ingredient as ItemStack).getCount() <= 0) {
            return;
        }
        blacklist.addIngredientToBlacklist(ingredient);
    }
}
