import net.minecraft.entity.monster.EntityGhast
import net.minecraft.entity.monster.IMob
import net.minecraft.entity.passive.EntityBat
import net.minecraftforge.fml.common.registry.ForgeRegistries
import thaumcraft.common.entities.monster.EntityFireBat

// Yeeting Furnace ====================================================================

mods.jei.ingredient.hide(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
crafting.removeByOutput(item('extrautils2:machine').withNbt(['Type': 'extrautils2:furnace']))
mods.extrautils2.furnace.removeAll()

// Block Patterns JEI Tab =========================================================================

// XU2 registers the "Blocks" tab (mill build patterns) with no icon or catalyst; the first catalyst becomes the tab icon.
// Water Mill, Lava Mill, Fire Mill, Dragon Egg Mill
for (meta in [3, 2, 5, 8]) {
    mods.jei.catalyst.add('extrautils2.blockPatterns', item('extrautils2:passivegenerator', meta))
}

// Angel Ring: any ghast, bat or Firebat ==========================================================

// A lasso holding any registered entity whose class passes the filter. XU2 keeps hostile mobs (IMob)
// in the Cursed Lasso (meta 1), the rest in the Golden Lasso (meta 0). withNbt is a subset match.
def lassoOf = { Closure filter ->
    ForgeRegistries.ENTITIES.valuesCollection
        .findAll { filter(it.entityClass) }
        .collect { item('extrautils2:goldenlasso', IMob.isAssignableFrom(it.entityClass) ? 1 : 0)
            .withNbt(['Animal': ['id': it.registryName.toString()]]) }
        .inject { a, b -> a | b }
}

// XU2 hardcodes minecraft:ghast and minecraft:bat. Any EntityGhast subclass counts (Special Mobs'
// Baby/Fighter/King/Queen/Unholy, Twilight Forest's ghasts); Thaumcraft's Firebat stands in for the bat.
def ghastLasso = lassoOf { EntityGhast.isAssignableFrom(it) }
// Call methods on the fixed class, never on `it`: `it == X` makes Groovy build every entity class's
// metaclass, which crashes the dedicated server on classes with client-only members.
def batLasso = lassoOf { EntityBat.equals(it) || EntityFireBat.equals(it) }

// Wing materials per ring variant (meta 0-5), left and right, as in XU2's own recipe;
// XU2 mirrors the recipe only when the two wings differ
def wings = [
    [ore('blockGlass'),               ore('blockGlass'),           false],
    [item('minecraft:feather'),       item('minecraft:feather'),   false],
    [ore('dyePurple'),                ore('dyePink'),              true],
    [item('minecraft:leather'),       item('minecraft:leather'),   false],
    [ore('nuggetGold'),               ore('nuggetGold'),           false],
    [item('minecraft:coal', 0),       item('minecraft:coal', 1),   true],
]
wings.eachWithIndex { lr, meta ->
    def builder = crafting.shapedBuilder()
        .name('extrautils2:angel_ring_' + meta)
        .replaceByName()
        .output(item('extrautils2:angelring', meta))
        .shape('LGR',
               'GrG',
               'BGH')
        .key([L: lr[0], R: lr[1], G: ore('ingotGold'), r: item('extrautils2:chickenring', 1),
              B: batLasso, H: ghastLasso])
    if (lr[2]) builder.mirrored()
    builder.register()
}

//=================================================================================================