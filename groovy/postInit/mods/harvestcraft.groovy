//Yeeting HarvestCraft's Machines =================================================================

def harvestcraft_machines = [
    item('harvestcraft:waterfilter'),
    item('harvestcraft:grinder'),
    item('harvestcraft:presser')
]

for (machine in harvestcraft_machines) {
    crafting.removeByOutput(machine)
    mods.jei.ingredient.hide(machine)
}

//=================================================================================================