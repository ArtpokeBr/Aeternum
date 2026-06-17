#modloaded railcraft

//Fixing Metal Post Recipes
moretweaker.railcraft.RollingMachine.addShaped(<railcraft:post_metal:15>, [
    [null, <ore:ingotIron>, null],
    [null, <ore:ingotIron>, null],
    [null, <ore:ingotIron>, null]
], 100);

recipes.addShaped("railcraft_post_metal_platform_fix", <railcraft:post_metal_platform:15>, [
    [<ore:plateIron>, <ore:plateIron>, <ore:plateIron>],
    [<ore:plateIron>, <railcraft:post_metal:15>, <ore:plateIron>],
    [<ore:plateIron>, <ore:plateIron>, <ore:plateIron>]
]);
