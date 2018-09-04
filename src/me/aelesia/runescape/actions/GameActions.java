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
import me.aelesia.runescape.utils.game.PlayerUtils;

public class GameActions {
	
//	public static void fish(String fishingSpot, String action) {
//		CommonActions.fish(LocationUtils.getNpcByActionNearestVisible(action, fishingSpot), action);
//	}
	
	public static boolean fish(Npc fishingSpot, String action) {
		fishingSpot.interact(action);
		System.out.println("[ACTION] Interacting with fishing spot");
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			System.out.println("[SUCCESS] Arrived at fishing spot");
		}
		if (Execution.delayUntil(()->PlayerUtils.isFishing(), 3000)) {
			System.out.println("[SUCCESS] Fishing");
			Execution.delayUntil(()->!PlayerUtils.isFishing());
			return true;
		}
		return false;
	}
	
	public static boolean chop(GameObject tree) {
		
		tree.interact(E.Action.CHOP_DOWN);
		System.out.println("[ACTION] Interacting with " + tree.getDefinition().getName());
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			System.out.println("[SUCCESS] Arrived at " + tree);
		}
		if (Execution.delayUntil(()->PlayerUtils.isChopping(), 1000)) {
			System.out.println("[SUCCESS] Chopping " + tree.getDefinition().getName());
			Execution.delayUntil(()->!PlayerUtils.isChopping());
			return true;
		}
		return false;
	}
	
	public static void chopWait(GameObject tree) {
		chop(tree);
	}
	
	
	public static void startFire(String ...logs) {

    	SpriteItem tinderBox = Inventory.getItems(E.Item.TINDERBOX).first();
    	SpriteItem log = Inventory.getItems(logs).last();
    	startFire(tinderBox, log);
	}
	
	private static void startFire(SpriteItem tinderBox, SpriteItem log) {

//    	if (tinderBox == null) {
//    		throw new ItemNotFoundException("No tinderbox in inventory");
//    	} else if (log == null) {
//    		throw new ItemNotFoundException("No logs in inventory");
//    	}
		
		System.out.println("[BEGIN] startFire()");
		
		InventoryActions.use(tinderBox, log);
    	Execution.delay(1000);
    	if (PlayerUtils.isStartingFire()) {
    		Execution.delayUntil(()->PlayerUtils.isIdle(), 20000, 30000);
    		Execution.delay(1000);
    		System.out.println("[SUCCESS] Logs catch fire");
    	}
    	Execution.delayUntil(()->PlayerUtils.isIdle(), 1000);
    	System.out.println("[END] startFire()");
    	
	}
	
	public static boolean mine(GameObject rock) {
		rock.interact(E.Action.MINE);
		System.out.println("[ACTION] Interacting with rock");
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			System.out.println("[SUCCESS] Arrived at rock");
		}
		if (Execution.delayUntil(()->PlayerUtils.isMining(), 1000)) {
			System.out.println("[SUCCESS] Mining rock");
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
		
		System.out.println("[BEGIN] cookFood()");
		
    	InventoryActions.use(food, fire);
    	Execution.delay(1000, 1500);
    	Keyboard.typeKey(" ");
		Execution.delay(1000, 1500);
		
		if (PlayerUtils.isCooking()) {
			System.out.println("[SUCCESS] Cooking " + food);
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
		System.out.println("[ACTION] Walking to self");
		Execution.delay(1000);
		if (Execution.delayUntil(()->PlayerUtils.isIdle(), 2000)) {
			System.out.println("[SUCCESS] Action cancelled");
		}
	}
	
	public static boolean attack(Npc target) {
		target.interact(E.Action.ATTACK);
		System.out.println("[ACTION] Interacting with " + target);
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			System.out.println("[SUCCESS] Arrived at " + target);
		}
		if (Execution.delayUntil(()->PlayerUtils.isInCombat2(), 1000)) {
			System.out.println("[SUCCESS] Attacking " + target);
			Execution.delayUntil(()->GameUtils.isDead(target), 60000);
			System.out.println("[SUCCESS] Killed Target");
			return true;
		}
		return false;
	}
	
	public static boolean loot(GroundItem item) {
		int quantity = Inventory.getQuantity();
		item.interact(E.Action.TAKE);
		System.out.println("[ACTION] Looting " + item.getDefinition().getName());			
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving());
			System.out.println("[SUCCESS] Arrived at " + item);
		}
		if (Inventory.getQuantity() > quantity) {
			System.out.println("[SUCCESS] Picked up: " + item);
			return true;
		}
		return false;
	}
	
	public static void lootAsync(GroundItem item) {
		item.interact(E.Action.TAKE);
		System.out.println("[ACTION] Looting " + item.getDefinition().getName());
		Execution.delayUntil(()->PlayerUtils.isMoving(), 1000);
	}
	
	public static void openBank() {
		if (!Bank.isOpen()) {
			System.out.println("[ACTION] Opening Bank");
			Bank.open();
			if (Execution.delayUntil(()->Bank.isOpen(), 5000)) {
				System.out.println("[SUCCESS] Bank is open");
				Execution.delay(1000);
			}
		}
	}
	
	public static void closeBank() {
		if (Bank.isOpen()) {
			Bank.close();
			System.out.println("[ACTION] Closing Bank");
			if (Execution.delayUntil(()->!Bank.isOpen(), 1000)) {
				System.out.println("[SUCCESS] Bank is closed");
			}
		}
	}
	
	public static void depositAll(SpriteItem item) {
		System.out.println("[ACTION] Depositing all " + item);
		Bank.deposit(item, 0);
		if (Execution.delayUntil(()->!item.isValid(), 2000)) {
			System.out.println("[SUCCESS] Item deposited");
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
