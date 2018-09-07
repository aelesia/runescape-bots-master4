package me.aelesia.runescape.script.mining;

import java.awt.Color;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.LootingTask;
import me.aelesia.runescape.tasks.base.MiningTask;
import me.aelesia.runescape.tasks.general.BankDepositDefault;
import me.aelesia.runescape.tasks.general.DisposeDefault;
import me.aelesia.runescape.tasks.general.TravelDefault;
import me.aelesia.runescape.tasks.multi.TravelBankDepositTask;
import me.aelesia.runescape.utils.game.Logger;

public class MiningBankingBot extends StateBot {
	
	
	class State {
		static final String INITIAL_TRAVEL = "INITIAL_TRAVEL";
		static final String MINING = "MINING";
		static final String LOOTING = "LOOTING";
		static final String TRAVEL_BANK_DEPOSIT = "TRAVEL_BANK_DEPOSIT";
	}
	
	@Override
	protected void initialize() {
		config.zone = Zones.MINE_VARROCK_EAST;
		config.bank = Zones.BANK_VARROCK_EAST;
		config.oreToMine = new Color[] { E.Ore.IRON };
		config.itemsToLoot = new String[] { E.Item.IRON_ORE };
		config.itemsToDeposit = new String[] { E.Item.IRON_ORE };
		config.minLoot = 5;
	}
	
	@Override
	protected void registerTasks() {
    	
		this.taskMap.put(State.INITIAL_TRAVEL, new TravelDefault(config.zone) {
			@Override
			public String stateIfArrived() {
				return State.MINING;
			}
    	});
		
    	this.taskMap.put(State.MINING, new MiningTask(config.zone.area, config.oreToMine) {
			
			@Override
			public String changeState() {
				if (Inventory.isFull()) {
					Logger.state("Inventory is full");
					return State.TRAVEL_BANK_DEPOSIT;
				} else if (LootingTask.getNumLootInVicinity(config.itemsToLoot) >= config.minLoot) {
					Logger.state("Items to loot");
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
					return State.TRAVEL_BANK_DEPOSIT;
				} else if (LootingTask.getAdjacentLoot(this.itemsToLoot) == null) {
					Logger.state("No more items to loot");
					return State.MINING;
				}
				return null;
			}
		});
    	
		this.taskMap.put(State.TRAVEL_BANK_DEPOSIT, new TravelBankDepositTask(
				config.zone.area, config.bank.area, config.itemsToDeposit) {
			@Override
			public String nextState() {
				return State.MINING;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.INITIAL_TRAVEL;
	}
}
