
//Cleaning JEI Categories
def disabled_categories = [
    "forestry.bottler"
]

for (category in disabled_categories) {
    mods.jei.category.remove(category)
}

//Fixing Foregoing's JEI Catalyst

def foregoing_produce_catalyst = [
    item('industrialforegoing:latex_processing_unit'),
    item('industrialforegoing:spores_recreator'),
    item('industrialforegoing:sewage_composter_solidifier'),
    item('industrialforegoing:dye_mixer'),
    item('industrialforegoing:lava_fabricator'),
    item('industrialforegoing:froster'),
    item('industrialforegoing:water_resources_collector'),
    item('industrialforegoing:mob_relocator'),
    item('industrialforegoing:crop_recolector'),
    item('industrialforegoing:water_condensator'),
    item('industrialforegoing:animal_byproduct_recolector'),
    item('industrialforegoing:mob_slaughter_factory'),
    item('industrialforegoing:protein_reactor'),
    item('industrialforegoing:animal_resource_harvester')
]
for (machine in foregoing_produce_catalyst) {
    mods.jei.catalyst.add("machine_produce_category", machine);
}