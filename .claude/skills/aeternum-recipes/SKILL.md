---
name: aeternum-recipes
description: How to write recipes in the Aeternum 1.12.2 pack - which language each machine needs (GroovyScript first, CraftTweaker fallback), the full list of alloying machines and their APIs, and NuclearCraft's CraftTweaker-only API and Manufactory ore-dict bug. Use when adding, changing or removing a recipe or alloy, adding a machine recipe for NuclearCraft, Immersive Engineering, Ender IO, Thermal, Mekanism or Tinkers', or adding a Mekanism infuse type.
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
- `references/nuclearcraft.md`: NuclearCraft's CraftTweaker API and the Manufactory ore-dict bug. Read before any NuclearCraft recipe.
