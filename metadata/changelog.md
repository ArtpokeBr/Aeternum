# Aeternum 1.2.0

### Mod Changes

- Added Modern Mob Spawn.
- Added Requious Frakto.
- Added RoidTweaker.

### Changed

- Mobs should spawn only on blocks with light level 0 and below now.
- Disabled Biomes O'Plenty's own Ruby, Sapphire, Peridot, and Amber ore generation across all 44 biomes that had them, now that those gems are unified into other mods' canonical versions.
- Ferdinand's Flowers colours with a direct vanilla dye equivalent (red, orange, yellow, lime, green, cyan, light blue, blue, purple, magenta, pink) now produce that vanilla dye instead of the mod's own dye item.
- Those same Ferdinand's Flowers can now also be processed into vanilla dye via Thermal's Pulverizer and Integrated Dynamics' Squeezer/Mechanical Squeezer (4x dye), Actually Additions' Crusher (3x dye), or Ender IO's Sag Mill (chance-based dye output, matching EnderIO's own vanilla flower recipes).
- Amber unified across mods: Thaumcraft's Amber is now the canonical item, with Fossils & Archeology's and Biomes O'Plenty's Amber both converting into it (same precedent as the existing Ruby/Sapphire/Peridot unification).
- Thermal Expansion's Pulverizer recipe for Amber Ore now uses Thaumcraft's Amber Ore instead of Biomes O'Plenty's, matching the new canonical Amber source.
- Forestry's Natura-compat Fertilizer recipe now accepts any grain (ore('listAllgrain')) instead of only Natura's Barley.
- Forestry Farms now also accept Actually Additions' and Industrial Foregoing's Fertilizer as a fertilizer source.
- Added 9 custom Requious Frakto JEI tabs ported from E2EE, recalculated against aeternum's own configs/mods where they differed from E2EE's:
  - IC2's Liquid Heat Exchanger (lava/hot coolant cooling), Crop database (46 crops), and Semi-Fluid Generator
  - Forestry's Farm Fertilizers (weighted by aeternum's own `config/forestry/farm.cfg` values)
  - Actually Additions' Lens of the Miner End Stone bonus ores
  - Astral Sorcery's Evershifting Fountain and Mineralis ritual rewards (weighted by aeternum's own `fluid_rarities.cfg`/`mineralis_ritual.cfg`)
  - Immersive Engineering's Chemical Thrower (concrete spraying) and Garden Cloche liquid fertilizers (Water, Industrial Foregoing's Meat/Sewage, Forestry Honey, Short Mead, Botania's Life Essence, Ender IO's Nutrient Distillation/Vapor of Levity), the latter via RoidTweaker
  - Thermal Expansion's Arboreal Extractor tree-tapping (resin/sap)

### Fixed

- Railcraft Metal Posts and Metal Post Platforms having no crafting recipes.
- Immersive Engineering's Arc Furnace recycling recipe scan adding several seconds to load time; it's now skipped.
- Immersive Engineering's villager core-sample trade generation and Engineer's Manual crafting-grid previews adding extra load time; both are now skipped (manual page text is unaffected).
- Thermal Expansion's Factorizer letting Thaumcraft's Amber Block decompress into 9 Amber when it's only made from 4, a free duplication exploit.
- Biomes O'Plenty biome ore-gen tweaks being silently discarded on every boot; they were placed under `biomes/defaults/`, which BOP deletes and regenerates from its own jar on startup. Moved to the actual config path BOP reads (`biomes/<namespace>/<biome>.json`, no `defaults` segment) so they persist.

---

# Aeternum 1.1.1

### Mod Changes

- Added ClocheCall.

### Changed

- Thanks to ClocheCall, IE's Garden Cloches should have support for a ton of extra crops and even Trees.

### Fixed

- Industrial Foregoing's Machine Produce JEI Category having no Catalysts.
- Cleanroom Relauncher Configs being provided by Default.

---

# Aeternum 1.1.0

### Mod Changes

- Added Fancy Menu.

### Changed

- Added Custom Copper Chipset.
- Added Alternate LP Raw Chipset Recipes that require BC's Chipsets and provide 2x Outputs.
- Added Storage Drawer's Framing Tool.
- Added a Recipe to the BC's Filler.
- Tweaked Power Requirements of BC's Chipset Recipes.
- Added a Thermal Expansion's Refinery Recipe to Convert Honey Types.
- Enabled Menu Buttons for Changelog and Socials.
- Enabled Pack Version Display in the Main Menu.
- Added a Customized Main Menu.
- Enabled Quark's End Stone and Obsidian Speleothems.

### Fixed

- IC2 Energy Limits with Flux Networks.
