# NuclearCraft recipes

## CraftTweaker only

GroovyScript 1.4.3 has no NuclearCraft compat (no `nuclearcraft` dir under `com/cleanroommc/groovyscript/compat/mods/`). Use NuclearCraft's bundled CraftTweaker integration:

- `mods.nuclearcraft.Manufactory.addRecipe(input, output, time, power, radiation)`
- `mods.nuclearcraft.Extractor.addRecipe(input, output1, output2OrNull, time, power, radiation)`
- `mods.nuclearcraft.Infuser.addRecipe(...)`, `mods.nuclearcraft.AlloyFurnace.addRecipe(...)`, ...

The machine classes are `nc/integration/crafttweaker/CTRecipes$<Machine>Methods` in `mods/nuclearcraft-*.jar`; zenClass names match `mods.nuclearcraft.<Machine>`. Get exact signatures per machine with `javap`.

## Manufactory: no ore-dict ingredients

- `<ore:tagName>` inputs fail on the Manufactory with a bare log line, `Failed to add manufactory recipe 1 x ore:X -> 1 x Y`, and no detail even at TRACE.
- Cause (decompiled `nc.integration.crafttweaker.CTAddRecipe` / `nc.recipe.BasicRecipeHandler`): `BasicRecipeHandler.buildRecipe(List<?>...)` runs each already-built ingredient through `RecipeHelper.buildItemIngredient(Object)` a second time. For the Manufactory that pass returns `null` for an `OreIngredient`, which sets `nullRecipe` and aborts the recipe.
- It is not a duplicate recipe (`removeRecipeWithInput`/`removeRecipeWithOutput` find no match) and not a config issue: changing `nuclearcraft.cfg` `smart_processor_input` vs `stellar_core.cfg` `BasicRecipeSearchImprovements` doesn't help. Keep `smart_processor_input=false` anyway; StellarCore's doc comment requires it.
- The same tag works on every other NC machine (Extractor, Infuser, Alloy Furnace, ...).
- **Fix:** use a direct item id (`<modid:item:meta>`) for Manufactory inputs and outputs. In `scripts/mods/ic2.zs`, `<minecraft:ender_eye>` works where `<ore:pearlEnderEye>` does not.
