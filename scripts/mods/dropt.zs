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

Dropt.list("railcraft_sulfur_unification")
  //Saltpeter/Niter
  .add(Dropt.rule()
      .matchBlocks(["railcraft:ore:1"])
      .addDrop(Dropt.drop()
          .items([<thermalfoundation:material:772>], Dropt.range(2, 6, 2))
      )
  );

//=================================================================================================