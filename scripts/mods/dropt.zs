import mods.dropt.Dropt;

//Unifying Railcraft's Ore Drops ==================================================================
Dropt.list("railcraft_sulfur_unification")

  //Sulfur
  .add(Dropt.rule()
      .matchBlocks(["railcraft:ore"])
      .addDrop(Dropt.drop()
          .items([<thermalfoundation:material:771>], Dropt.range(2, 6, 2))
      )
  );

Dropt.list("railcraft_saltpeter_unification")
  //Saltpeter/Niter
  .add(Dropt.rule()
      .matchBlocks(["railcraft:ore:1"])
      .addDrop(Dropt.drop()
          .items([<thermalfoundation:material:772>], Dropt.range(2, 6, 2))
      )
  );

//=================================================================================================

//Unifying BOP's Gem Drops ========================================================================

Dropt.list("bop_peridot_unification")
  .add(Dropt.rule()
      .matchBlocks(["biomesoplenty:gem_ore:2"])
      .addDrop(Dropt.drop()
          .items([<projectred-core:resource_item:202>], Dropt.range(1, 3, 2))
      )
  );

Dropt.list("bop_ruby_unification")
  .add(Dropt.rule()
      .matchBlocks(["biomesoplenty:gem_ore:1"])
      .addDrop(Dropt.drop()
          .items([<projectred-core:resource_item:200>], Dropt.range(1, 3, 2))
      )
  );

Dropt.list("bop_sapphire_unification")
  .add(Dropt.rule()
      .matchBlocks(["biomesoplenty:gem_ore:6"])
      .addDrop(Dropt.drop()
          .items([<projectred-core:resource_item:201>], Dropt.range(1, 3, 2))
      )
  );

Dropt.list("bop_amber_unification")
  .add(Dropt.rule()
      .matchBlocks(["biomesoplenty:gem_ore:7"])
      .addDrop(Dropt.drop()
          .items([<thaumcraft:amber>], Dropt.range(1, 3, 2))
      )
  );

//=================================================================================================

//Unifying Mekanism's Salt Block ==================================================================

Dropt.list("mekanism_salt_unification")
  .add(Dropt.rule()
      .matchBlocks(["mekanism:saltblock"])
      .addDrop(Dropt.drop()
          .items([<rockhounding_chemistry:chemical_items:3>], Dropt.range(2, 6, 2))
      )
  );

//=================================================================================================