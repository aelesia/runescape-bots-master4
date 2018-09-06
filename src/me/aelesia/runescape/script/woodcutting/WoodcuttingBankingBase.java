package me.aelesia.runescape.script.woodcutting;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.ChopTask;
import me.aelesia.runescape.tasks.general.BankDepositDefault;
import me.aelesia.runescape.tasks.general.TravelDefault;
import me.aelesia.runescape.utils.game.Logger;

public abstract class WoodcuttingBankingBase extends StateBot {
	
	class State {
		static final String CHOPPING = "CHOPPING";
		static final String TRAVEL_BANK = "TRAVEL_BANK";
		static final String BANKING = "BANKING";
		static final String TRAVEL_CHOP = "TRAVEL_CHOP";
	}
	
	@Override
	protected void registerTasks() {
		
    	this.taskMap.put(State.CHOPPING, new ChopTask(config.zone.area, config.treesToChop) {			
			@Override
			public String changeState() {
				if (Inventory.isFull()) {
					Logger.state("Inventory is full");
					return State.TRAVEL_BANK;
				} 
				return null;
			}
		});
		
		this.taskMap.put(State.TRAVEL_BANK, new TravelDefault(config.bank.area) {
			@Override
			public String stateIfArrived() {
				return State.BANKING;
			}
		});
		
		this.taskMap.put(State.BANKING, new BankDepositDefault(config.itemsToDeposit) {
			
			@Override
			public String stateIfDepositedAll() {
				return State.TRAVEL_CHOP;
			}
		});
		
		this.taskMap.put(State.TRAVEL_CHOP, new TravelDefault(config.zone.area) {
			@Override
			public String stateIfArrived() {
				return State.CHOPPING;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.TRAVEL_CHOP;
	}
}
