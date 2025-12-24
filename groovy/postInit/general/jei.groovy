
//Cleaning JEI Categories
def disabled_categories = [
    "forestry.bottler"
]

for (category in disabled_categories) {
    mods.jei.category.remove(category)
}