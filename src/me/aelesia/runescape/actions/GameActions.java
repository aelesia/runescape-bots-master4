package me.aelesia.runescape.actions;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.input.Keyboard;
import com.runemate.game.api.hybrid.local.hud.interfaces.Bank;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.utils.game.GameUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;

public class GameActions {
	
//	public static void fish(String fishingSpot, String action) {
//		CommonActions.fish(LocationUtils.getNpcByActionNearestVisible(action, fishingSpot), action);
//	}
	
	public static boolean fish(Npc fishingSpot, String action) {
		if (!fishingSpot.interact(action)) {
			Logger.fail("Failed to interact with " + fishingSpot.getDefinition().getName());
			return false;
		}
		Logger.action("Interacting with fishing spot");
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			Logger.success("Arrived at fishing spot");
		}
		if (Execution.delayUntil(()->PlayerUtils.isFishing(), 3000)) {
			Logger.success("Fishing");
			Execution.delayUntil(()->!PlayerUtils.isFishing());
			return true;
		}
		return false;
	}
	
	public static boolean chop(GameObject tree) {
		
		if (!tree.interact(E.Action.CHOP_DOWN)) {
			Logger.fail("Failed to interact with " + tree);
			return false;
		}
		Logger.action("Interacting with " + tree.getDefinition().getName());
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			Logger.success("Arrived at " + tree);
		}
		if (Execution.delayUntil(()->PlayerUtils.isChopping(), 1000)) {
			Logger.success("Chopping " + tree.getDefinition().getName());
			Execution.delayUntil(()->!PlayerUtils.isChopping());
			return true;
		}
		return false;
	}
	
	public static void chopWait(GameObject tree) {
		chop(tree);
	}
	
	
	public static boolean startFire(String ...logs) {

    	SpriteItem tinderBox = Inventory.getItems(E.Item.TINDERBOX).first();
    	SpriteItem log = Inventory.getItems(logs).last();
    	return startFire(tinderBox, log);
	}
	
	private static boolean startFire(SpriteItem tinderBox, SpriteItem log) {

//    	if (tinderBox == null) {
//    		throw new ItemNotFoundException("No tinderbox in inventory");
//    	} else if (log == null) {
//    		throw new ItemNotFoundException("No logs in inventory");
//    	}
		
		
		InventoryActions.use(tinderBox, log);
    	if (Execution.delayUntil(()->PlayerUtils.isStartingFire(), 1000)) {
    		Execution.delayUntil(()->PlayerUtils.isIdle(), 20000, 30000);
    		Execution.delay(1000);
    		Logger.success("Logs catch fire");
    		return true;
    	}
    	return false;
//    	Execution.delayUntil(()->PlayerUtils.isIdle(), 1000);
//    	System.out.println("[END] startFire()");
    	
	}
	
	public static boolean mine(GameObject rock) {
		rock.interact(E.Action.MINE);
		Logger.action("Interacting with rock");
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			Logger.success("Arrived at rock");
		}
		if (Execution.delayUntil(()->PlayerUtils.isMining(), 1000)) {
			Logger.success("Mining rock");
			Execution.delayUntil(()->!PlayerUtils.isMining());
			return true;
		}
		return false;
	}
	
	public static boolean cookFood(GameObject fire, SpriteItem food) {
		
//		if (food == null) {
//			throw new ItemNotFoundException("Raw food");
//		} else if (fire == null) {
//			throw new LocatableNotFoundException(E.Object.FIRE);
//		}
		
		
    	InventoryActions.use(food, fire);
    	Execution.delay(1000, 1500);
    	Keyboard.typeKey(" ");
		Execution.delay(1000, 1500);
		
		if (PlayerUtils.isCooking()) {
			Logger.success("Cooking " + food);
			Execution.delayUntil(()->PlayerUtils.isIdleFor(2000), 60000);
			return true;
		}
    	return false;
	}
	
	public static boolean cookFood(GameObject fire, String ...foodList) {
//		if (foodList.length==0) {
//			throw new IllegalArgumentException("foodList cannot be empty");
//		}
		
		SpriteItem food = Inventory.getItems(foodList).first();
		return GameActions.cookFood(fire, food);
	}
	
//	public static void cookRawFoodOnFire(GameObject fire) {
//		CommonActions.cookFoodOnFire(fire, Category.RAW_FOOD);
//	}
	
//	public static void cookRawFood(GameObject fire) {
//		System.out.println("I'm cooking raw food");
//    	SpriteItem fish = Inventory.getItems(E.Item.RAW_SALMON, E.Item.RAW_TROUT).first();
//    	if (fish!=null) {
//    		System.out.println("Cooking");
//    		InventoryActions.use(fish, fire);
//	    	Execution.delay(1000, 1500);
//	    	Keyboard.typeKey(" ");
//			Execution.delay(1000, 1500);
//	    	Execution.delayUntil(()->PlayerUtils.isIdleFor(2000), 30000);
//    	}
//	}
	
	public static void cancel() {
		Players.getLocal().getPosition().interact(E.Action.WALK_HERE);
		Logger.action("Walking to self");
		Execution.delay(1000);
		if (Execution.delayUntil(()->PlayerUtils.isIdle(), 2000)) {
			Logger.success("Action cancelled");
		}
	}
	
	public static boolean attack(Npc target) {
		target.interact(E.Action.ATTACK);
		Logger.action("Interacting with " + target);
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			Logger.success("Arrived at " + target);
		}
		if (Execution.delayUntil(()->PlayerUtils.isInCombat2(), 1000)) {
			Logger.success("Attacking " + target);
			Execution.delayUntil(()->GameUtils.isDead(target), 60000);
			Logger.success("Killed Target");
			return true;
		}
		return false;
	}
	
	public static boolean loot(GroundItem item) {
		int quantity = Inventory.getQuantity();
		item.interact(E.Action.TAKE);
		Logger.action("Looting " + item.getDefinition().getName());			
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			Logger.success("Arrived at " + item);
		}
		if (Inventory.getQuantity() > quantity) {
			Logger.success("Picked up: " + item);
			return true;
		}
		return false;
	}
	
	public static void lootAsync(GroundItem item) {
		item.interact(E.Action.TAKE);
		Logger.action("Looting " + item.getDefinition().getName());
		Execution.delayUntil(()->PlayerUtils.isMoving(), 1000);
	}
	
	public static void openBank() {
		if (!Bank.isOpen()) {
			Logger.action("Opening Bank");
			Bank.open();
			if (Execution.delayUntil(()->Bank.isOpen(), 5000)) {
				Logger.success("Bank is open");
				Execution.delay(1000);
			}
		}
	}
	
	public static void closeBank() {
		if (Bank.isOpen()) {
			Bank.close();
			Logger.action("Closing Bank");
			if (Execution.delayUntil(()->!Bank.isOpen(), 1000)) {
				Logger.success("Bank is closed");
			}
		}
	}
	
	public static void depositAll(SpriteItem item) {
		Logger.action("Depositing all " + item);
		Bank.deposit(item, 0);
		if (Execution.delayUntil(()->!item.isValid(), 2000)) {
			Logger.success("Item deposited");
		}
	}
	
//	public static void loot(List<GroundItem> items) {
//		for (GroundItem g : items) {
//			while (g.isValid()) {
//				GameActions.loot(g);
//			}
//		}
//	}
	
//	public static void lootNearby(int radius, int maxLoot, String ...items) {
//		
//		int itemsPickedUp=0;
//		int retryCount=0;
//		GroundItem item = LocationUtils.getGroundItemsVisibleNearest(radius, items);
//		
//		while (item!=null && item.isValid() && itemsPickedUp < maxLoot) {
//			System.out.println("itemsPickedUp: " + itemsPickedUp);
//			System.out.println("retryCount: " + retryCount);
//			if (loot(item)) {
//				itemsPickedUp++;
//				item = LocationUtils.getGroundItemsVisibleNearest(radius, items);
//				retryCount=0;
//			} else if (retryCount>10) {
//				item = LocationUtils.getGroundItemsVisibleNearest(radius, items);
//				retryCount=0;
//			} else {
//				retryCount++;
//			}
//		}
//	}
}
