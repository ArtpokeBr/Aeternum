#modloaded rockhounding_chemistry
#loader mixin
#sideonly client

import native.java.util.ArrayList;
import native.java.util.Collections;
import native.java.util.IdentityHashMap;
import native.com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LeachingVatRecipe;
import native.com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import native.com.globbypotato.rockhounding_chemistry.machines.recipe.construction.RetentionVatRecipe;

/*
Rockhounding Chemistry's tooltip handler (GlobbyEventHandler.handleTooltip) scans every Mineral
Sizer, Leaching Vat and Retention Vat recipe for every item, and calls getOutput() in its loop
condition. getOutput() copies the recipe's output list on every call, so each tooltip makes
thousands of list copies. HEI builds its tooltip search index from every item's tooltip at
startup, where this cost ~5 s of boot (Flare profiles, Nov 2026).

Inside the tooltip handler only, getOutput() returns one cached copy per recipe instead. The
handler only reads the list. Recipes are final by the time tooltips render (CraftTweaker and
GroovyScript run earlier), and a removed recipe is just never looked up again.
*/
#mixin {targets: "com.globbypotato.rockhounding_chemistry.handlers.GlobbyEventHandler"}
zenClass MixinGlobbyEventHandler {
    #mixin Redirect
    #{
    #    method: "handleTooltip",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lcom/globbypotato/rockhounding_chemistry/machines/recipe/construction/MineralSizerRecipe;getOutput()Ljava/util/ArrayList;"
    #    }
    #}
    function cachedSizerOutput(recipe as MineralSizerRecipe) as ArrayList {
        if (isNull(scripts.mods.mixins.rockhounding.shared.Op.outputs)) {
            scripts.mods.mixins.rockhounding.shared.Op.outputs = Collections.synchronizedMap(IdentityHashMap());
        }
        val cached = scripts.mods.mixins.rockhounding.shared.Op.outputs.get(recipe) as ArrayList;
        if (!isNull(cached)) {
            return cached;
        }
        val fresh = recipe.getOutput();
        scripts.mods.mixins.rockhounding.shared.Op.outputs.put(recipe, fresh);
        return fresh;
    }

    #mixin Redirect
    #{
    #    method: "handleTooltip",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lcom/globbypotato/rockhounding_chemistry/machines/recipe/construction/LeachingVatRecipe;getOutput()Ljava/util/ArrayList;"
    #    }
    #}
    function cachedLeachingOutput(recipe as LeachingVatRecipe) as ArrayList {
        if (isNull(scripts.mods.mixins.rockhounding.shared.Op.outputs)) {
            scripts.mods.mixins.rockhounding.shared.Op.outputs = Collections.synchronizedMap(IdentityHashMap());
        }
        val cached = scripts.mods.mixins.rockhounding.shared.Op.outputs.get(recipe) as ArrayList;
        if (!isNull(cached)) {
            return cached;
        }
        val fresh = recipe.getOutput();
        scripts.mods.mixins.rockhounding.shared.Op.outputs.put(recipe, fresh);
        return fresh;
    }

    #mixin Redirect
    #{
    #    method: "handleTooltip",
    #    at: {
    #        value: "INVOKE",
    #        target: "Lcom/globbypotato/rockhounding_chemistry/machines/recipe/construction/RetentionVatRecipe;getOutput()Ljava/util/ArrayList;"
    #    }
    #}
    function cachedRetentionOutput(recipe as RetentionVatRecipe) as ArrayList {
        if (isNull(scripts.mods.mixins.rockhounding.shared.Op.outputs)) {
            scripts.mods.mixins.rockhounding.shared.Op.outputs = Collections.synchronizedMap(IdentityHashMap());
        }
        val cached = scripts.mods.mixins.rockhounding.shared.Op.outputs.get(recipe) as ArrayList;
        if (!isNull(cached)) {
            return cached;
        }
        val fresh = recipe.getOutput();
        scripts.mods.mixins.rockhounding.shared.Op.outputs.put(recipe, fresh);
        return fresh;
    }
}
