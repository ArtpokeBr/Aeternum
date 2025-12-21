//Creating a Trophies OreDict =====================================================================

def twilightforest_trophies = [
    item('twilightforest:trophy'),
    item('twilightforest:trophy', 1),
    item('twilightforest:trophy', 2),
    item('twilightforest:trophy', 3),
    item('twilightforest:trophy', 4),
    item('twilightforest:trophy', 5),
    item('twilightforest:trophy', 6),
    item('twilightforest:trophy', 8)
];

for (trophy in twilightforest_trophies) {
    ore_dict.add('twilightTrophy', trophy)
}


//=================================================================================================