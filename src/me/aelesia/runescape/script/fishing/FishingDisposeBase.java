package me.aelesia.runescape.script.fishing;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.FishingTask;
import me.aelesia.runescape.tasks.general.DisposeDefault;
import me.aelesia.runescape.utils.game.Logger;

public abstract class FishingDisposeBase extends StateBot {
	
	class State {
		static final String FISHING = "FISHING";
		static final String DISPOSE = "DISPOSE";
	}
	
	@Override
	protected void registerTasks() {
    	this.taskMap.put(State.FISHING, new FishingTask(config.fishingSpot, config.fishingAction) {
			@Override public String changeState() {
				if (Inventory.isFull()) {
					Logger.state("Inventory is full");
					return State.DISPOSE;
				}
				return null;
			}
    	});
    	
    	this.taskMap.put(State.DISPOSE, new DisposeDefault(config.itemsToDispose) {
			@Override
			public String stateIfNoMoreItemsToDispose() {
				return State.FISHING;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.FISHING;
	}
}
