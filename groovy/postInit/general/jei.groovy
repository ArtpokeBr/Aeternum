
//Cleaning JEI Categories
def disabled_categories = [
    "Painter",
    "forestry.bottler",
    "EIOTank",
    "EIOWC",
    "thermalexpansion.transposer_extract",
    "thermaldynamics.covers"
]

for (category in disabled_categories) {
    mods.jei.category.remove(category)
}