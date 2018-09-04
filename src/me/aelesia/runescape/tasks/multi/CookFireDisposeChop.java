package me.aelesia.runescape.tasks.multi;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.tasks.base.ChopTask;
import me.aelesia.runescape.tasks.base.DisposeTask;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.general.RandomUtils;

public abstract class CookFireDisposeChop extends MultiTask {
	
	class State {
		static final String COOK_FIRE = "COOK_FIRE";
		static final String DISPOSING = "DISPOSING";
		static final String CHOPPING = "CHOPPING";
	}
	
	public CookFireDisposeChop(String[] itemsToCook, String[] logsToBurn, String[] itemsToDispose, String[] treesToChop, Area treeArea) {
		
		taskMap.put(State.COOK_FIRE, new CookFireTask(itemsToCook, logsToBurn) {
			@Override
			public String nextState() {
				return CookFireDisposeChop.State.DISPOSING;
			}
		});
		
		taskMap.put(State.DISPOSING, new DisposeTask(itemsToDispose) {
			@Override
			public String changeState() {
				if (!InventoryUtils.contains(this.itemsToDispose)) {
					System.out.println("[STATE] No more items to dispose of");
					if (Inventory.getQuantity(Category.TINDER_LOGS) >= 2) {
						return END;
					} else {
						System.out.println("[STATE] Not enough logs left");
						return State.CHOPPING;
					}
				}
				return null;
			}
		});
		
		taskMap.put(State.CHOPPING, new ChopTask(treeArea, treesToChop) {
			int numLogsToAcquire;
			@Override
			public void initialize() {
				super.initialize();
				numLogsToAcquire = RandomUtils.randomInt(4, 6);
			}
			
			@Override
			public String changeState() {
				if (Inventory.getItems(logsToBurn).size() >= numLogsToAcquire) {
					System.out.println("[STATE] Acquired enough logs: " + Inventory.getItems(Category.TINDER_LOGS).size() + " / " + numLogsToAcquire) ;
					return END;
				} else if (Inventory.getItems(logsToBurn).size() >=2 && LocationUtils.getGameObjectNearestWithin(this.area, treesToChop) == null) {
					System.out.println("[STATE] No trees left to chop in area but acquired sufficient logs");
					return END;
				}
				return null;
			}
		});
	}
	
	@Override
	public String startState() {
		return State.COOK_FIRE;
	}
}
