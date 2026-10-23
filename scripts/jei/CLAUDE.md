# Custom JEI tabs (Requious Frakto)

- Each block type's variants are defined by `resourceName` in `config/requious/<machine>.json` (e.g. `assembly.json`). Scripts reference them as `<assembly:resourceName>` through `mods.requious.Assembly`/`AssemblyRecipe`/`SlotVisual` (`addJEICatalyst`, `addJEIRecipe`, `setJEIItemSlot`/`setJEIFluidSlot`/`setJEIDurationSlot`).
- Shared helpers: `scripts/jei/requious.zs`. Calls like `scripts.jei.requious.add(...)` resolve by file path (dots for slashes), so the folder must stay where it is.
- Ported tabs: `scripts/jei/mod/<sourceMod>.zs`.
- Tab titles: lang key `requious.jei.recipe.<resourceName>`, kept in `groovy/assets/aeternum/lang/en_us.lang` (lang keys are global, see `groovy/assets/CLAUDE.md`).
- The Garden Cloche liquid-fertilizer tab needs Roids Tweaker (`mods.roidtweaker.immersiveengineering.GardenCloche`).
