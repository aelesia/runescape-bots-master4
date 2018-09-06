package me.aelesia.runescape.script.fighting;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.Config;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.BuryTask;
import me.aelesia.runescape.tasks.base.FightTask;
import me.aelesia.runescape.tasks.base.LootingTask;
import me.aelesia.runescape.tasks.general.BuryDefault;
import me.aelesia.runescape.tasks.multi.CookFireDisposeChop;
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.general.CommonUtils;

public class FightingCowCookBot extends StateBot {
	
	Config config = new Config();
	
	class State {
		static final String FIGHTING = "FIGHTING";
		static final String LOOTING = "LOOTING";
		static final String BURYING = "BURYING";
		static final String COOKING_FIRE_DISPOSE_CHOP = "COOKING_FIRE_DISPOSE_CHOP";
	}

	@Override
	protected void initialize() {
		config.monstersToFight = new String[] { E.NPC.COW, E.NPC.COW_CALF };
		config.itemsToCook = Category.RAW_FOOD;
		config.logsToBurn = Category.TINDER_LOGS;
		config.treesToChop = new String[] { E.Object.TREE };
		config.itemsToDispose = CommonUtils.mergeArrays(Category.RAW_FOOD, Category.BURNT_FOOD, Category.COOKED_FOOD);
		config.itemsToLoot = new String[] { E.Item.BONES, E.Item.RAW_BEEF };
		config.bonesToBury = Category.BONES;
		config.zone = DetermineUtils.findZone(8);
		
		config.minLoot = 6;
		config.maxLoot = config.minLoot * 2;
		config.kills = 4;
	}
	
	@Override
	protected void registerTasks() {
		this.taskMap.put(State.FIGHTING, new FightTask(config.zone.area, config.monstersToFight) {
			@Override
			public String changeState() {
				if (killCount() >= config.kills && LootingTask.getNumLootInVicinity(config.itemsToLoot) >= config.minLoot) {
					return State.LOOTING;
				}
				return null;
			}
		});
		
		this.taskMap.put(State.LOOTING, new LootingTask(config.itemsToLoot) {
			@Override
			public String changeState() {
				if (Inventory.isFull()) {
					Logger.state("Inventory too many items");
					return State.BURYING;
					
				} else if (this.itemsPickedUp >= config.maxLoot) {
					Logger.state("Picked up " + this.itemsPickedUp + " items");
					return State.FIGHTING;
					
				} else if (LootingTask.getAdjacentLoot(this.itemsToLoot) == null) {
					if (Inventory.getEmptySlots() <= config.minLoot) {
						Logger.state("Inventory too many items");
						return State.BURYING;
					} else {
						return State.FIGHTING;
					}
				}
				return null;
			}
		});
		
		this.taskMap.put(State.BURYING, new BuryDefault(config.bonesToBury) {
			@Override
			public String stateIfNoMoreBones() {
				if (Inventory.getEmptySlots() <= config.minLoot) {
					Logger.state("Inventory too many items");
					return State.COOKING_FIRE_DISPOSE_CHOP;
				} else {
					return State.FIGHTING;
				}
			}
		});
		
		this.taskMap.put(State.COOKING_FIRE_DISPOSE_CHOP, new CookFireDisposeChop(
				config.itemsToCook,
    			config.logsToBurn,
    			config.itemsToDispose,
    			config.treesToChop,
    			config.zone.area) {
			
			@Override
			public String nextState() {
				return State.FIGHTING;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.FIGHTING;
	}
}
