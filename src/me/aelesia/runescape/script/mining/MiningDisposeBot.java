package me.aelesia.runescape.script.mining;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.MiningTask;
import me.aelesia.runescape.tasks.general.DisposeDefault;
import me.aelesia.runescape.utils.game.Logger;

public abstract class MiningDisposeBot extends StateBot {
	
	
	class State {
		static final String MINING = "MINING";
		static final String DISPOSE = "DISPOSE";
	}
	
	@Override
	protected void registerTasks() {
    	this.taskMap.put(State.MINING, new MiningTask(config.zone.area, config.oreToMine) {
			
			@Override
			public String changeState() {
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
				return State.MINING;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.MINING;
	}
}
