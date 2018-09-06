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
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;

public abstract class FightingBase extends StateBot {
	
	class State {
		static final String FIGHTING = "FIGHTING";
		static final String LOOTING = "LOOTING";
		static final String BURYING = "BURYING";
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
				return State.FIGHTING;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.FIGHTING;
	}
}
