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

//Unifying Mekanism's Salt Block ==================================================================

Dropt.list("mekanism_salt_unification")
  //Salt
  .add(Dropt.rule()
      .matchBlocks(["mekanism:saltblock"])
      .addDrop(Dropt.drop()
          .items([<rockhounding_chemistry:chemical_items:3>], Dropt.range(2, 6, 2))
      )
  );

//=================================================================================================