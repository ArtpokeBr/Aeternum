import loottweaker.LootTweaker;
import loottweaker.vanilla.loot.LootTable;
import loottweaker.vanilla.loot.LootPool;
import loottweaker.vanilla.loot.Functions;
import crafttweaker.item.IItemStack;
import crafttweaker.data.IData;

var itemsToRemove as string[] = [

	"twilightforest:uncrafting_table",
	"twilightforest:golden_apple"

];

for item in itemsToRemove {
	mods.ltt.LootTable.removeGlobalItem(item);
}
