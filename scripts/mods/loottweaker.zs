import crafttweaker.item.IItemStack;
import crafttweaker.data.IData;

var itemsToRemove as string[] = [

	"twilightforest:uncrafting_table",
	"twilightforest:golden_apple"

];

for item in itemsToRemove {
	mods.ltt.LootTable.removeGlobalItem(item);
}
