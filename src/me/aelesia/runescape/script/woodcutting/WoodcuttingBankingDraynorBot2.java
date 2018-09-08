package me.aelesia.runescape.script.woodcutting;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.ChopTask;
import me.aelesia.runescape.tasks.base.StartFireTask;
import me.aelesia.runescape.tasks.general.BankDepositDefault;
import me.aelesia.runescape.tasks.general.TravelDefault;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.general.RandomUtils;

public class WoodcuttingBankingDraynorBot2 extends StateBot {
	
	class State {
		static final String CHOPPING = "CHOPPING";
		static final String FIRESTARTING = "FIRESTARTING";
		static final String TRAVEL_BANK = "TRAVEL_BANK";
		static final String BANKING = "BANKING";
		static final String TRAVEL_CHOP = "TRAVEL_CHOP";
	}
	
	@Override
	protected void initialize() {
		config.treesToChop = new String[] { E.Object.OAK };
		config.zone = Zones.DRAYNOR_OAKS_2;
		config.bank = Zones.BANK_DRAYNOR;
		config.logsToBurn = new String[] { E.Item.OAK_LOGS };
		config.itemsToDeposit = new String[] { E.Item.OAK_LOGS };
	}
	
	@Override
	protected void registerTasks() {
		
    	this.taskMap.put(State.CHOPPING, new ChopTask(config.zone.area, config.treesToChop) {			
			@Override
			public String changeState() {
				if (Inventory.isFull()) {
					Logger.state("Inventory is full");
					if (RandomUtils.randomChance(0.15)) {
						return State.FIRESTARTING;
					} else {
						return State.TRAVEL_BANK;
					}
				} 
				return null;
			}
		});
    	
    	this.taskMap.put(State.FIRESTARTING, new StartFireTask(config.logsToBurn) {
    		int numLogsToBurn;
    		
    		@Override
    		public void initialize() {
    			super.initialize();
    			this.numLogsToBurn = RandomUtils.randomInt(8, 12);
    			Logger.init("Will burn " + this.numLogsToBurn + " logs");
    		}
    		
			@Override
			public String changeState() {
//				if (!InventoryUtils.contains(config.logsToBurn)) {
//					Logger.state("No more logs to burn");
//					return State.CHOPPING;
//				}
				if (this.numLogsBurned() >= numLogsToBurn) {
					Logger.state("Burned " + this.numLogsToBurn + " logs");
					return State.CHOPPING;
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
