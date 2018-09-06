package me.aelesia.runescape.script.mining;

import java.awt.Color;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.Config;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.FishingTask;
import me.aelesia.runescape.tasks.base.MiningTask;
import me.aelesia.runescape.tasks.general.DisposeDefault;
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.Zone;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class MiningDisposeBase extends StateBot {
	
	
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
