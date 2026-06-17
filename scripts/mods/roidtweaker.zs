#modloaded immersiveengineering roidtweaker

import crafttweaker.liquid.ILiquidStack;

// *======= Garden Cloche liquid fertilizers =======*
function addLiquidFertilizer(fluid as ILiquidStack, mult as float) {
  if (fluid.name != 'water') mods.roidtweaker.immersiveengineering.GardenCloche.addFertilizer(fluid, mult);
  scripts.jei.mod.immersiveengineering.addGardenClocheFluid(fluid, mult);
}

addLiquidFertilizer(<liquid:water>                , 0.20);
addLiquidFertilizer(<liquid:meat>                 , 0.40);
addLiquidFertilizer(<liquid:sewage>               , 0.45);
addLiquidFertilizer(<liquid:for.honey>            , 0.50);
addLiquidFertilizer(<liquid:short.mead>           , 0.60);
addLiquidFertilizer(<liquid:lifeessence>          , 0.65);
addLiquidFertilizer(<liquid:nutrient_distillation>, 1.20);
addLiquidFertilizer(<liquid:vapor_of_levity>      , 2.00);
