# Aeternum 1.6.3

### Mod Changes

- Removed: Modern Mob Spawn.
- Added: File Director.

### Added

- Fossils & Archeology's Permafrost can now be processed in an Ender IO Enhanced Sag Mill into Bone (guaranteed), with chance-based bonus outputs of Fern Seeds, Iced Meat and Skulls, scaling with grinding balls.
- Added a JEI Tab for Fossils & Archeology Permafrost Drops.

### Changed

- Red Alloy can now be made in every alloying machine (Ender IO Alloy Smelter, Thermal Induction Smelter, Mekanism Metallurgic Infuser and NuclearCraft Alloy Furnace), and Electrotine Alloy (1 Silver + 4 Electrotine) was added to all of them, including the IE Alloy Kiln and Arc Furnace. Electrotine Dust is now a Metallurgic Infuser source.
- Electrotine now works like Redstone in the Metallurgic Infuser: it has its own infuse texture, Blocks of Electrotine give 90, and a new Compressed Electrotine (made from Electrotine Dust in the Enrichment Chamber) gives 80.
- ProjectRed's Infused, Energized and Electrotine Silicon can now be made in one step from Silicon + 8 Redstone, Glowstone or Electrotine Dust in the IE Alloy Kiln and Arc Furnace, Ender IO Alloy Smelter, Thermal Induction Smelter and NuclearCraft Alloy Furnace, and from Silicon with Redstone or Electrotine infuse in the Mekanism Metallurgic Infuser.
- Glowstone is now a Metallurgic Infuser infuse type (Glowstone Dust 10, Glowstone block 40, new Compressed Glowstone 80, made from Glowstone Dust in the Enrichment Chamber), so Energized Silicon can be made there too.
- Electrotine Ore now processes in every machine Redstone Ore does, at the same yields (SAG Mill, Induction Smelter, Enrichment Chamber, XU2 Crusher and more), with Electrum as a byproduct.
- Added Crushing Recipes for Thaumcraft's Crystal Clusters in every Crusher, Pulverizer and Grinder.
- The Angel Ring now accepts a Cursed Lasso holding any kind of Ghast, including Special Mobs' Ghast variants and Twilight Forest's Ghasts, and a Thaumcraft Firebat (in a Cursed Lasso) in place of the Bat. Angel Rings now have a tooltip saying so.
- Traveler's Titles dimension names now use the same purple as The One Probe overlay.
- Thaumcraft's Cinnabar Ore can now be doubled: every Crusher, Pulverizer and Grinder turns it into the new Quicksilver Dust (with Redstone and Sulfur byproducts), which smelts into Quicksilver. The Induction Smelter gives up to 3 Quicksilver, the Mekanism Combiner turns 8 Quicksilver Dust back into Cinnabar Ore, and Quicksilver can be crushed back into dust.
- The Thermal Transposer recipe that turns Cinnabar Ore and Cryotheum into 4 Cinnabar now also works in the NuclearCraft Fluid Infuser.
- Infernal Furnace bonuses no longer overlap: ores now only give their nugget (Rare Earth stays only on Redstone and Lapis Ore, which have no nugget), and Native and Eldritch Clusters only give Rare Earth (Eldritch Clusters keep their Flux crystal).
- Immersive Engineering's Metal Blocks (Copper, Aluminum, Lead, Silver, Nickel, Uranium, Electrum and Steel), hidden by unification, can now be made again in a Chisel from any Block of the same metal. Constantan gets its own Chisel group with Thermal's and Immersive Engineering's Blocks of Constantan.
- Deep Mob Learning's Polymer Clay is now made like the real thing: Rockhounding Chemistry's Chloride Compound (the PVC) and Coal Tar Compound (the plasticizer), with Clay and Lapis Lazuli. It no longer needs Gold or Iron.
- Celeritas is now offered on first launch (ticked by default, untick it to keep the vanilla renderer) and updated to 2.5.0. No more installing it by hand.
- Blocks now break at normal speed while flying (jetpacks, Angel Ring, flight armour).
- The /time set day and /time set night commands skip ahead to the next day or night again instead of resetting world time.
- AE2's in-world Fluix Crystal recipe (Charged Certus Quartz, Redstone and Nether Quartz dropped into water) now shows in JEI, and a new variant using Electrotine Dust instead of Redstone gives 4 Fluix Crystals instead of 2.

### Fixed

- Environmental Tech's Void Ore Miner never producing Aer and Ignis Crystal Clusters.
- Extra Utilities 2's JEI Blocks tab (Water, Lava, Fire and Dragon Egg Mill build patterns) having no icon or catalysts.
- Dragging items from JEI into filters with Just Enough Drags not saving on servers (the mod was missing from the server pack).
- Dragging items and fluids from JEI into AE2 buses, interfaces, level emitters, the Cell Workbench and the Pattern Terminal now uses AE2's own handler again, instead of Just Enough Drags' older one overriding it.
- Dedicated servers crashing on boot because of the new Electrotine and Glowstone infuse types.
- Just Enough Drags crashing dedicated servers on boot (its XNet support now only loads on the client).
- Infernal Furnace bonuses still showing duplicates on dedicated servers (the de-duplication script failed there).
- Joining a Dedicated Server no longer shows a Mixin error in chat about Nether Backport.
- AE2 Storage Buses no longer crash the game when attached to a misbehaving modded inventory.
- Glass Panes and Iron Bars no longer connect to Cooking for Blockheads counters, cabinets, cutting boards and other kitchen blocks.

---

# Aeternum 1.6.2

### Mod Changes

- Removed: AE2 Cell Render.

### Fixed

- Fixed AE2 Auto-Crafting failing with an "Index (0) is greater than or equal to list size (0)" error for Patterns that use some of IC2's hidden Recipes (Pistons, Rails, Torches, Buckets...).

---

# Aeternum 1.6.1

### Mod Changes

- Downgraded HEI to 4.34.3 to fix a Crash with AE2 Terminals.

---

# Aeternum 1.6.0

### Mod Changes

- Added: Snad

### Changed 

- Disabled Salutation's Chat Override.
- Added Immersive Engineering's Squeezer Recipes for Root's Elemental Seeds.
- Basic Ender Alloy now requires Copper instead of Iron ingots.
- Enhanced Ender Alloy now requires Platinum instead of Enderium Ingots.
- Biocrude can be used to make Bio Diesel with Immersive Engineering now.
- Removed Not Needed Ember's Crash Fix Mixin.
- Filtered harmless Advancement, BuildCraft Facade and AE2 config errors out of the logs.

### Fixed

- Diamond and Emerald Gears should require Bushes now.
- Fixed Dedicated Servers crashing on startup because of the Bedrock Ores crash fix Mixin.
- Fixed Dedicated Servers crashing when Unseen's Nether Backport generates its structures.

### Other

- Added a Server Pack.

---

# Aeternum 1.5.0

### Mod Changes

- Added: Rather Enough Immersive Engineering Multiblocks.
- Added: Demagnetize.
- Added: Engineer's Decor.
- Added: Engineer's Doors.
- Added: Ender Quarry Optimizer.
- Added: JEI UU-Assembler.

- Removed: ThaumicJEI.

### Changed

- Spice of Life's Lunchbox can now hold up to 8 Items per slot.
- Buffed Thermal Expansion's Augment Slots for Dynamos and Machines per Tier.
- Buffed Thermal Expansion's Dynamo Generation.
- Blacklisted Evilcraft's Display Stands and Biome Bottles from HEI.
- Added a Mixin to Fix an Issue with Embers Freeze during Boot.
- Reworked Immersive Engineering's Blueprint Recipes.
- Added more JEI Tabs to IC2 Machines.
- Immersive Engineering's Circuit Board is now considered a "circuitBasic".
- Disabled IC2's Mixins.
- Improved JER Performance via a Mixin. (Original Credits goes to the E2EE Dev!)
- Improved CyclopsCore Server Performance via a Mixin. (Original Credits goes to the E2EE Dev!)
- Added Alt Recipes for Buckets, Pistons, Cauldrons, Hoppers and Tripwires.
- Added a Roots Transmutation Recipe for Copper Blocks to Aluminum Blocks.
- Buffed Immersive Engineering's Metal Press.
- Buffed Immersive Tech's Multiblocks.
- Added Nutrional Values to Farmer's Delight's Foods.

---

# Aeternum 1.4.0

### Mod Changes

- Added: Thaumic Info.
- Added: Immersive Fixes.
- Added: Unlimited Chisel Works.

- Replaced: Improved Cleanroom Relauncher -> Cleanroom Relauncher.

### Changed 

- Aqueous Soil now pauses auto-harvesting when receiving a redstone signal. Its tooltip has been updated to reflect this.
- Added a Clay Ball to Clay Block Shaped Recipe.
- Added a Centrifugal Separator Recipe for Sesame Seeds Crop to Sesame Oil.

### Fixed

- Added Thermal Expansion Sawmill and Arboreal Extractor Compat to IC2 Rubber Wood. 

---

# Aeternum 1.3.0

### Mod Changes

- Added Backpack Display.

### Changed 

- Aeternum now provides a Dark Mode ResourcePack.
- Iron Tanks' and BuildCraft's tanks now show their fluid contents, fill level, and a bucket icon on TheOneProbe overlay.

### Fixed

- Some Resources and Scripts not being shipped with the Pack.

--- 

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
- IC2 Rubber is now considered a logWood.
- Rustic's Apiary now shows its bees and honeycombs on TheOneProbe overlay.
- Ender Utilities Ender Alloys now can only be made in a EnderIO's Alloy Smelter.
- Added Additional Recipes to make Ender Eye Dust.
- Added Extractor Recipes to Cinder Pearls and Faint Amaranths.
- Fossils & Archeology's Fossil block can now be processed in an Ender IO Enhanced Sag Mill into Bone (guaranteed), with chance-based bonus outputs of Biofossil, Relic Scrap, and Plant Fossil, scaling with grinding balls.
- Added a JEI Tab for Fossil and Archeology Possible Fossil Drops.

### Fixed

- Railcraft Metal Posts and Metal Post Platforms having no crafting recipes.
- Immersive Engineering's Arc Furnace recycling recipe scan adding several seconds to load time; it's now skipped.
- Immersive Engineering's villager core-sample trade generation and Engineer's Manual crafting-grid previews adding extra load time; both are now skipped (manual page text is unaffected).
- Thermal Expansion's Factorizer letting Thaumcraft's Amber Block decompress into 9 Amber when it's only made from 4, a free duplication exploit.
- Biomes O'Plenty biome ore-gen tweaks being silently discarded on every boot; they were placed under `biomes/defaults/`, which BOP deletes and regenerates from its own jar on startup. Moved to the actual config path BOP reads (`biomes/<namespace>/<biome>.json`, no `defaults` segment) so they persist.
- Blood Magic Meteors Tab having no JEI Catalyst.

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
