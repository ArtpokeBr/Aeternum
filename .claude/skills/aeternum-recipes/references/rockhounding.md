# Rockhounding Chemistry recipes (3.72)

- Machine recipes are hard-coded Java (`machines/recipe/*Recipes.java`); `config/` has no recipe JSON. No GroovyScript compat: CraftTweaker only (`mods.rockhounding_chemistry.*`: BedReactor, ChemicalExtractor, CrushingGears, DepositionChamber, GasCondenser, GasifierPlant). Its items work in Groovy crafting recipes by id/ore dict.
- `rockhounding_chemistry:chemical_items` meta follows the `EnumChemicals` enum order, not the lang order: 3 Salt (`dustSalt`), 6 Chloride Compound (`compoundChloride`), 7 Coal Tar Compound (`compoundTar`, 15% Gasifier Plant byproduct). `chemical_dusts:23` = Calcium (`dustCalcium`).
- No PVC, plastic, plasticizer or kaolin items; its fluids exist only as Forge buckets.
