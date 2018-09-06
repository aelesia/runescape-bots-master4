package me.aelesia.runescape.utils.game;

import com.runemate.game.api.hybrid.local.hud.interfaces.Equipment;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import me.aelesia.runescape.consts.Category;


public class InventoryUtils {
	
	public static boolean equipped(String ...items) {
		return !Equipment.getItems(items).isEmpty();
	}
	
	public static boolean contains(String ...items) {
		return !Inventory.getItems(items).isEmpty();
	}
	
	public static boolean hasRawFood() {
		return !Inventory.getItems(Category.RAW_FOOD).isEmpty();
	}
	
//	public static boolean inventoryHasCookedFood() {
//		return !Inventory.getItems(Category.).isEmpty();
//	}
//	
//	public static boolean inventoryHasBurntFood() {
//		String items[] = Category.BURNT_FOOD_LIST.toArray(new String[Category.BURNT_FOOD_LIST.size()]);
//		return !Inventory.getItems(items).isEmpty();
//	}
	
	//Use only if the object will disappear
//	public static void interactAll(String action, String ...itemName) {
//		for (SpriteItem item : Inventory.getItems(itemName)) {
//			int initialSize = Inventory.getQuantity(itemName);
//			item.interact(action);
//			Execution.delay(1000);
//			Execution.delayUntil(()->(Inventory.getQuantity(itemName)!=initialSize), 2000);
//		}
//	}
//	
//	public static void buryAllBones() {
//		InventoryUtils.interactAll(E.Action.BURY, E.Item.BONES);
//	}
}
