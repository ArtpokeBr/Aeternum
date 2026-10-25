---
name: aeternum-recipes
description: How to write recipes in the Aeternum 1.12.2 pack - which language each machine needs (GroovyScript first, CraftTweaker fallback), the full list of alloying machines, crushing machines and item+fluid machines (Transposer, NC Infuser) and their APIs, and NuclearCraft's CraftTweaker-only API and Manufactory ore-dict bug. Use when adding, changing or removing a recipe or alloy, adding a crusher/pulverizer/grinder recipe, adding a machine recipe for NuclearCraft, Immersive Engineering, Ender IO, Thermal, Mekanism or Tinkers', or adding a Mekanism infuse type.
---

# Recipes in Aeternum

## Language

- Write recipes in GroovyScript: `groovy/postInit/mods/<mod>.groovy` (`groovy/preInit/` for registrations).
- Fall back to CraftTweaker (`scripts/mods/<mod>.zs`) only for machines GroovyScript 1.4.3 has no compat for. Check by listing `com/cleanroommc/groovyscript/compat/mods/` in `mods/groovyscript-1.4.3.jar` with moddig.
- CraftTweaker extras: MoreTweaker ([wiki](https://bitbucket.org/noeppi_noeppi/moretweaker/wiki/Home)) and Roids Tweaker ([wiki](https://github.com/roidrole/Roids-Tweaker/wiki)).
- Block drops go through Dropt ([docs](https://dropt.readthedocs.io/en/latest/)); unification through UniDict (`config/unidict/CLAUDE.md`).
- Any code in a recipe script must stay server-safe: no client-only calls (see the Mekanism infuse-type note in `references/alloying.md`).

## References

Read the one the task needs:

- `references/alloying.md`: every machine that makes alloys, with its API. Read before adding an alloy; an alloy added "everywhere" covers every row.
- `references/crushing.md`: every crusher, grinder and mill with its API and quirks (IC2 Macerator takes the output first, SAG Mill grinding-ball bonus). Read before adding a crushing recipe; one added "to every crusher" covers every automatable row.
- `references/crushing.md` also has the Iron Ore yield table (metal-ore parity) and why ore loops skip ores with no matching dust.
- `references/nuclearcraft.md`: NuclearCraft's CraftTweaker API and the Manufactory ore-dict bug. Read before any NuclearCraft recipe.
- `references/fluid-infusion.md`: item + fluid -> item machines (Transposer, NC Infuser, Bottling Machine, ...) and fluid-name notes (Cryotheum, mercury/quicksilver). Read before porting a Transposer-style recipe.

## Thaumcraft Quicksilver and Cinnabar

- Ore dict: Quicksilver is plain `quicksilver` (no `item` prefix), its nugget `nuggetQuicksilver` (`thaumcraft:nugget:5`; meta 10 is Rare Earth), the cluster `clusterCinnabar` (`thaumcraft:cluster:6`). Thermal's Cinnabar (`thermalfoundation:material:866`) is `crystalCinnabar`/`itemCinnabar`.
- Infernal Furnace bonuses (`mods.thaumcraft.smelting_bonus`): the input is an ore-dict name string or an ItemStack. `removeByOutput` strips that output from every input, so filter `streamRecipes()` on `it.in` instead. Pack rule ("Infernal Furnace Bonuses" in `groovy/postInit/mods/thaumcraft.groovy`): ores give only their nugget, clusters only Rare Earth (`thaumcraft:nugget:10`). It's applied generically, so a new ore or cluster bonus from any mod follows it automatically.
- The pack's dust stage is `aeternum:quicksilver_dust` (`dustQuicksilver`, registered in `groovy/postInit/mods/thaumcraft.groovy`).

## Looking up vanilla-ore yields (ore parity)

- `groovy/postInit/mods/projectred.groovy` ("Electrotine Ore Processing") gives Electrotine Ore Redstone Ore's yields in every machine that crushes Redstone Ore; use it as the template for matching a modded ore to a vanilla one. Each machine block's comment records the Redstone yield it copies, and `removeByInput` precedes the new recipe wherever a generic ore->dust loop already made one.
- Ender IO's SAG Mill recipes sit in XMLs inside the EnderIO jar that moddig's `search` does not index. Ender IO writes PDF copies to `config/enderio/recipes/*.pdf`; read them with `pdftotext -layout <file>.pdf -`.
