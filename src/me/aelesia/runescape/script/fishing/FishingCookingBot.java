package me.aelesia.runescape.script.fishing;

import java.util.List;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.Npcs;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.Config;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.FishingTask;
import me.aelesia.runescape.tasks.multi.CookFireDisposeChop;
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public class FishingCookingBot extends StateBot {
	
	Config config = new Config();
	
	class State {
		static final String FISHING = "FISHING";
		static final String COOKING_FIRE_DISPOSE_CHOP = "COOKING_FIRE_DISPOSE_CHOP";
	}

	@Override
	protected void initialize() {
		config.treesToChop = new String[] { E.Object.TREE, E.Object.DEAD_TREE };
		config.logsToBurn = Category.TINDER_LOGS;
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
		return State.FISHING;
	}
}
