package me.aelesia.runescape.script.debug;

import java.util.List;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.framework.LoopingBot;

import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.utils.game.LocationUtils;

public class DebugBot extends LoopingBot {

	private Coordinate coordinate;
	private Coordinate lastCoordinate;
	
	private int animationId;
	private int lastAnimationId;
//
//	private boolean idle;
//	private boolean wasIdle;
//	
//	private boolean inCombat;
//	private boolean wasInCombat;
	

	GroundItem item;
	
	@Override
	public void onStart(String... arguments) {
		super.onStart(arguments);
		this.setLoopDelay(200);
	}
	
	@Override
	public void onLoop() {
		coordinate = Players.getLocal().getPosition();
		if (!coordinate.equals(lastCoordinate)) {
			System.out.println("Coordinates: " + coordinate);
			lastCoordinate = coordinate;
		}
		animationId = Players.getLocal().getAnimationId();
		if (animationId != lastAnimationId) {
			System.out.println("Animation: " + animationId);
			lastAnimationId = animationId;
		}
		
//		List<GameObject> list = LocationUtils.getGameObjectsAroundMe(1);
//		for (GameObject obj : list) {
//			System.out.println(obj + " , " + obj.getType());
//		}
//		while (Inventory.contains(E.Item.BONES)) {
//			SpriteItem bones = Inventory.getItems(E.Item.BONES).first();
//			InventoryActions.interact(E.Action.BURY, bones);
//			System.out.println("Loop");
//		}
//		System.out.println("equipment: " + Equipment.getItems(E.Item.RUNE_AXE));
//		System.out.println("equipment: " + Equipment.getItems().asList());
//		Coordinate c = Zones.DRAYNOR_OAKS.area.getRandomCoordinate();
//		System.out.println(c);
//		Path p = Traversal.getDefaultWeb().getPathBuilder().buildTo(c);
//        if (p == null) {
//    		throw new OutOfBoundsException("[ERROR] No valid path. Current pos: " + Players.getLocal().getPosition() + ", Target: " + c);
//        }
//		System.out.println(Zones.DRAYNOR_OAKS_2_EMPTY.area.contains(Players.getLocal().getPosition()));
		
//		Coordinate c = Zones.DRAYNOR_OAKS_2_EMPTY.area.getRandomCoordinate();
//		System.out.println(c + ", " + Zones.DRAYNOR_OAKS_2_EMPTY.area.contains(c));
//		LocationActions.traverseTo(Zones.DRAYNOR_OAKS_2_EMPTY.area.getRandomCoordinate());
		
//        WebPath path = Traversal.getDefaultWeb().getPathBuilder().buildTo(new Coordinate(3102, 3284, 0));
//        if (path==null) {
//        	System.out.println("Path not found");
//        } else {
//        	System.out.println("Walking");
//        	path.step();
//        	Execution.delay(1000);
//        	Execution.delayUntil(()->!PlayerUtils.isMoving());
//        	System.out.println("Arrived");
//        }
//		
//		GameObject rock = LocationUtils.getMineNearestAroundMe(1, E.Ore.COPPER);
//		if (rock!=null && PlayerUtils.isIdle()) {
//			System.out.println("mining");
//			rock.interact(E.Action.MINE);
//		} else {
//			System.out.println("waiting");
//		}

		
		
//		InventoryActions.open();
//		ThreadUtils.sleepFor(3000);
//		InventoryActions.close();
//		ThreadUtils.sleepFor(3000);
		
//		GameObject tree = LocationUtils.getGameObjectNearest(E.Object.DEAD_TREE);
//		if (tree!=null) {
//			WebPath p = Traversal.getDefaultWeb().getPathBuilder().buildTo(tree);
//			System.out.println(p.getTraversalCost());
//		}
//		System.out.println();
		
//		System.out.println("inventory: " + Inventory.getUsedSlots());
		
//		GameActions.lootNearby(3, 15, E.Item.BONES, E.Item.FEATHER);
//		while(InventoryUtils.contains(E.Item.BONES)) {
//			InventoryActions.buryBones();
//		}
//		
//		System.out.println(LocationUtils.getGameObjectsAroundMe(15, E.Object.OAK).size());
//		Execution.delayUntil(()->PlayerUtils.isIdle(), 1500);
//		item = GroundItems.newQuery().within(LocationUtils.getRadiusArea(6)).names("Raw chicken").visible().filter(groundItem -> { return !groundItem.equals(item); }).results().nearest();
//		if (item!=null) {
//		double distance = Players.getLocal().distanceTo(item);
//		long start = System.currentTimeMillis();
//		GameActions.lootAsync(item);
//		System.out.println("distance: " + distance);
//		System.out.println("time taken: " + (System.currentTimeMillis() - start));
//	}
//		}
		
//		List<GameObject> obj = LocationUtils.getGameObjectsAroundMe(0);
//		if (!obj.isEmpty()) {
//			System.out.println(obj.get(0).getType());
//		}
		
//		idle = PlayerUtils.isIdle();
//		if (wasIdle != idle) {
//			System.out.println( idle ? "idle" : "not idle");
//			wasIdle = idle;
//		}
//		
//		if (PlayerUtils.isInCombat2()) {
//			System.out.println("in combat");
//		} else {
//			System.out.println("not in combat");
//		}
//		Npcs.newQuery().actions(Action.ATTACK).targetedBy(Players.getLocal()).results().isEmpty();
//		Npcs.newQuery().actions(Action.ATTACK).targeting(Players.getLocal()).results().isEmpty();
//		}
//		System.out.println("targetted: " + Npcs.newQuery().actions(Action.ATTACK).targeting(Players.getLocal()).results().isEmpty());
		
//		inCombat = PlayerUtils.isInCombat();
//		System.out.println(inCombat);
//		if (inCombat != wasInCombat) {
//			System.out.println( inCombat ? "fighting" : "not fighting");
//			wasInCombat = inCombat;
//		}
		
//		System.out.println("Distance: " + LocationUtils.distanceTo(LocationUtils.getGameObjectNearest(E.Object.OAK)));
		
//		for (GameObject obj : LocationUtils.getGameObjectsAroundMe(1)) {
//			System.out.println(obj);
//		}
//		Coordinate c  = LocationUtils.getEmptySpaceAroundMe();
		
//		GameObject obj = LocationUtils.getGameObjectNearestAround(3, E.Object.TREE, E.Object.OAK);
//		if (obj!=null) {
//			System.out.println(obj.getPosition());
//			System.out.println(obj.getArea());
//			for (Coordinate c : obj.getArea().getCoordinates()) {
//				System.out.println(c);
//			}
//			System.out.println("_____________");
//		}
		
//		List<GroundItem> items = LocationUtils.getGroundItemsVisibleNearMe(5, E.Item.BONES);
//		System.out.println(items.size());
	}

}
