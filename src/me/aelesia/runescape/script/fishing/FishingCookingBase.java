package me.aelesia.runescape.script.fishing;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.FishingTask;
import me.aelesia.runescape.tasks.general.TravelDefault;
import me.aelesia.runescape.tasks.multi.CookFireDisposeChop;

public abstract class FishingCookingBase extends StateBot {
	
	class State {
		static final String INITIAL_TRAVEL = "INITIAL_TRAVEL";
		static final String FISHING = "FISHING";
		static final String COOKING_FIRE_DISPOSE_CHOP = "COOKING_FIRE_DISPOSE_CHOP";
	}
	
	@Override
	protected void registerTasks() {
		
    	this.taskMap.put(State.INITIAL_TRAVEL, new TravelDefault(config.zone) {
			@Override
			public String stateIfArrived() {
				return State.FISHING;
			}
    	});
		
    	this.taskMap.put(State.FISHING, new FishingTask(config.fishingSpot, config.fishingAction) {
			@Override public String changeState() {
				if (Inventory.isFull()) {
					return State.COOKING_FIRE_DISPOSE_CHOP;
				}
				return null;
			}
    	});
    	
    	this.taskMap.put(State.COOKING_FIRE_DISPOSE_CHOP, new CookFireDisposeChop (
    			config.itemsToCook,
    			config.logsToBurn,
    			config.itemsToDispose,
    			config.treesToChop,
    			config.zone.area) {
    		
			@Override
			public String nextState() {
				return State.FISHING;
			}
		});
	}


	@Override
	protected String startingState() {
		return State.INITIAL_TRAVEL;
	}
}
