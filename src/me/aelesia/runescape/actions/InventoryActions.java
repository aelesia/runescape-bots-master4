package me.aelesia.runescape.actions;

import java.util.Arrays;
import java.util.List;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.InterfaceWindows;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.script.Execution;

import me.aelesia.runescape.consts.Hotkey;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.general.CommonUtils;

public class InventoryActions {
	
	public static void open() {
		if (!InterfaceWindows.getInventory().isOpen()) {
			Keyboard.typeKey(Hotkey.INVENTORY);
			Logger.action("Opening Inventory");
			Execution.delayUntil(()->InterfaceWindows.getInventory().isOpen(), 1000);
		}
	}
	
	public static void close() {
		if (InterfaceWindows.getInventory().isOpen()) {
			Keyboard.typeKey(Hotkey.INVENTORY);
			Logger.action("Closing Inventory");
			Execution.delayUntil(()->!InterfaceWindows.getInventory().isOpen(), 1000);
		}
	}
	
	public static void checkIfSelected() {
		if (Inventory.getSelectedItem() != null) {
			Inventory.getSelectedItem().click();
			Logger.action("Deselecting item");
			Execution.delayUntil(()->Inventory.getSelectedItem()==null, 1000);
		}
	}
	
//	public static void close() {
//		InterfaceWindows.getInventory().
//	}
	
	public static void use(SpriteItem item1, SpriteItem item2) {
    	InventoryActions.open();
    	InventoryActions.checkIfSelected();
    	
    	item1.interact("Use");
    	item2.interact("Use", item1.getDefinition().getName() + " -> " + item2.getDefinition().getName());
    	Logger.action("Using " + item1.getDefinition().getName() + " with " + item2.getDefinition().getName());
	}
	
	public static void use(SpriteItem item, GameObject object) {
    	InventoryActions.open();
    	InventoryActions.checkIfSelected();
    	
    	item.interact("Use");
    	object.interact("Use", item.getDefinition().getName() + " -> " + object.getDefinition().getName());
    	Logger.action("Using " + item.getDefinition().getName() + " with " + object.getDefinition().getName());
	}
	
	@Deprecated
	public static void disposeAll(String ...items) {
//		System.out.println("[START] disposeAll()");
		List<String> disposeList = Arrays.asList(items);
		List<SpriteItem> inventory = Inventory.getItems(items).asList();
		inventory = CommonUtils.sortInventoryByIndex(inventory);
		Keyboard.pressKey(16);
		for (SpriteItem item : inventory) {
			if (disposeList.contains(item.getDefinition().getName())) {
				item.click();
				Logger.action("Drop " + item);
			}
		}
		Keyboard.releaseKey(16);
		Execution.delay(1000);
//		System.out.println("[END] disposeAll()");
	}
	
	@Deprecated
	public static void interactAll(String action, String ...itemName) {
		for (SpriteItem item : Inventory.getItems(itemName)) {
	    	InventoryActions.open();
	    	InventoryActions.checkIfSelected();
			
			int initialSize = Inventory.getQuantity(itemName);
			Logger.action(action + item);
			item.interact(action);
			Execution.delay(1000);
			Execution.delayUntil(()->(Inventory.getQuantity(itemName)!=initialSize), 2000);
		}
	}
	
	public static boolean interact(String action, SpriteItem item) {
    	InventoryActions.open();
    	InventoryActions.checkIfSelected();
    	
		int initialSize = Inventory.getQuantity(item.getDefinition().getName());
		Logger.action(action + item);
		item.interact(action);
		if (Execution.delayUntil(()->(Inventory.getQuantity(item.getDefinition().getName())!=initialSize), 2000)) {
			Logger.success(action + item);
			return true;
		}
		return false;
		
	}
	
//	public static void buryBones() {
//		InventoryActions.interactAll(E.Action.BURY, Category.BONES);
//	}
	
//	public static void dispose(SpriteItem item) {
//		
//		item.click();
//	}
	
//	public static void disposeAll(List<String> cat1, List<String> cat2) {
//		List<String> combined = new ArrayList<String>(cat1);
//		combined.addAll(cat2);
//		dispose(combined);
//	}
	
//	public static void dispose(List<String> disposeCategories) {
//		System.out.println("Disposing of: " + disposeCategories);
//		for (int y=0; y<7; y++) {
//			
////			int start = 0;
////			int end = 3;
////			int dx = 1;
////			
////			if (y%2==0) {
////				start = 3;
////				end = 0;
////				dx = -1;
////			}
//			
////			for (int x=start; x!=end; x=x+dx) {
//			for (int x=0; x<4; x++) {
//                SpriteItem item = Inventory.getItemIn(y*4+x);
//                if (item!=null && disposeCategories.contains(item.getDefinition().getName())) {
//	    			if(item.interact("Drop"))
//	                    Execution.delayUntil(()->!item.isValid(), 1000);
//	    		}
//			}
//		}
//	}
}
