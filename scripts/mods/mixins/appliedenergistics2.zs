#modloaded appliedenergistics2
#loader mixin

import native.net.minecraft.util.NonNullList;

/*
Auto-crafting fails with "Error: ... IndexOutOfBoundsException: Index (0) is greater than or equal to list size (0)"
whenever a crafting pattern with substitutions enabled resolves to a shaped recipe whose getIngredients() is empty,
e.g. IC2's @hidden recipes (tin bucket, bronze piston/rails, resin torches, glowstone/gunpowder from dusts).
PatternHelper.getShapedRecipeIngredient guards the lookup with `index > ingredients.size()` instead of `>=`, so
index == size slips through to ingredients.get(index). Shrinking the size it compares against by one makes it fall
back to Ingredient.EMPTY, which just means that slot only accepts the exact item encoded in the pattern.
*/
#mixin {targets: "appeng.helpers.PatternHelper"}
zenClass MixinPatternHelper {
    #mixin Redirect
    #{
    #    method: "getShapedRecipeIngredient",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lnet/minecraft/util/NonNullList;size()I"
    #    }
    #}
    function fixIngredientBoundsCheck(instance as NonNullList) as int {
        return instance.size() - 1;
    }
}
