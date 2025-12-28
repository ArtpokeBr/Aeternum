// mods_loaded: mysticalworld

//Giving Amethyst a OreDict =======================================================================

ore_dict.add("gemAmethyst", item('mysticalworld:amethyst_gem'));

//=================================================================================================
output = "";
def input = "";
def tickTime = 0;
def recipeBuilder = inWorldCrafting.burning.recipeBuilder();

def recipes = [
        ['mysticalworld:charred_log', 'logWood', 160, true],
        ['mysticalworld:charred_planks', 'plankWood', 160, false],
        ['mysticalworld:charred_slab', 'slabWood', 160, false],
        ['mysticalworld:charred_stairs', 'stairWood', 160, true],
        ['mysticalworld:charred_fence', 'fenceWood', 160, true],
        ['mysticalworld:charred_fence_gate', 'fenceGateWood', 160, true],
        ['mysticalworld:charred_pressure_plate', 'pressurePlateWood', 160, false],
        ['mysticalworld:charred_button', 'buttonWood', 160, false]
]

recipes.each { recipe ->
    output = recipe[0]
    input = recipe[1]
    tickTime = recipe[2]
    recipeBuilder = inWorldCrafting.burning.recipeBuilder()

    if (recipe[3]) {
        crafting.removeByOutput(item(output))
    }

    print("\nAdding in_world recipe for $output");
    recipeBuilder.input(ore(input))
    recipeBuilder.output(item(output))
    recipeBuilder.ticks(tickTime)
    recipeBuilder.startCondition(null)
    recipeBuilder.register()

}