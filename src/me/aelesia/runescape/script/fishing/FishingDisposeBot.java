package me.aelesia.runescape.script.fishing;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.Config;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.FishingTask;
import me.aelesia.runescape.tasks.general.DisposeDefault;
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public class FishingDisposeBot extends StateBot {
	
	Config config = new Config();
	
	class State {
		static final String FISHING = "FISHING";
		static final String DISPOSE = "DISPOSE";
	}

	@Override
	protected void initialize() {
		config.itemsToDispose = CommonUtils.mergeArrays(Category.BURNT_FOOD, Category.COOKED_FOOD, Category.RAW_FOOD);
		config.itemsToCook = Category.RAW_FOOD;
		config.fishingSpotAction = DetermineUtils.findFishingspotAction();
		config.zone = DetermineUtils.findZone(10);
		System.out.println("[CONFIG] " + "\n" + config);
	}
	
	@Override
	protected void registerTasks() {
    	this.taskMap.put(State.FISHING, new FishingTask(config.fishingSpotAction[0], config.fishingSpotAction[1]) {
			@Override public String changeState() {
				if (Inventory.isFull()) {
					System.out.println("[STATE] Inventory is full");
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
