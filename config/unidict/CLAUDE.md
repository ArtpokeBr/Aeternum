# UniDict

UniDict unifies items and ore-dict entries across mods. For any unification request, try its config first; fall back to a Groovy/CraftTweaker script only when UniDict can't handle the case.

## Resources and CraftTweaker recipe templates

`scripts/mods/unidict.zs` rebuilds every plate and gear recipe per resource with UniDict's CraftTweaker compat: `mods.unidict.removalByKind.get("Crafting").remove("<kind>")` plus `mods.unidict.api.newShapedRecipeTemplate("<kind>", count, [[...]])`. Template cells are kind names (`"ingot"`, `"gem"`, ...) that expand to each resource's ore entry (`ingotIron`, `gemDiamond`, ...), or a real `<ore:...>` for fixed ingredients.

- **A template applies only to resources that have every kind it references** (output and all inputs). Removal-by-kind touches only resources that have that kind.
- Resources come from ore-dict names starting with `ingot` (minus a name blacklist), plus `UniDict.cfg` → `customUnifiedResources` (`Name:kind1|kind2`; a child kind exists only if the ore-dict name `kind+Name` exists). Anything with no `ingotX`, such as Diamond and Emerald, is invisible by default: the `"ingot"` gear template never matches it and its default gear recipe is never removed.
- **`customUnifiedResources` does not make templates work for gems.** `UniResourceHandler` keeps `apiResourceMap` (metals from `ingot*`), which backs `UniDictAPI` and so `newShapedRecipeTemplate`, and `resourceMap` (metals plus custom resources), which backs unification and removal-by-kind. Custom resources land only in `resourceMap`, so a `"gem"` template never matches them, and registering e.g. `Diamond:gem|gear` lets UniDict's postInit `remove("gear")` (which runs after CT scripts) delete a CraftTweaker recipe for that gear.
- **Non-ingot gears (Diamond, Emerald) skip UniDict:** plain CraftTweaker in `scripts/mods/thermalfoundation.zs` (`recipes.remove(<thermalfoundation:material:26>)` + `recipes.addShaped(...)`; TF gear metas: diamond 26, emerald 27) with `<ore:gearBushing>` (registered by Railcraft).
- Decompiled classes: `wanion.unidict.resource.UniResourceHandler.gatherResources` (discovery), `wanion.unidict.plugin.crafttweaker.UniDictCraftTweakerPlugin` (templates).
