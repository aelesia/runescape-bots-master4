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
import me.aelesia.runescape.utils.general.CommonUtils;

public class MiningDisposeBot extends StateBot {
	
	Config config = new Config();
	
	class State {
		static final String MINING = "MINING";
		static final String DISPOSE = "DISPOSE";
	}

	@Override
	protected void initialize() {
		config.oreToMine = new Color[] { E.Ore.IRON, E.Ore.COPPER };
		config.itemsToDispose = new String[] { E.Item.IRON_ORE, E.Item.COPPER_ORE };
		config.zone = DetermineUtils.findZone(2);
		System.out.println("[CONFIG] " + "\n" + config);
	}
	
	@Override
	protected void registerTasks() {
    	this.taskMap.put(State.MINING, new MiningTask(config.zone.area, config.oreToMine[0]) {
			
			@Override
			public String changeState() {
				if (Inventory.isFull()) {
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
