package me.aelesia.runescape.tasks.multi;

import com.runemate.game.api.hybrid.location.Area;

import me.aelesia.runescape.tasks.general.BankDepositDefault;
import me.aelesia.runescape.tasks.general.TravelDefault;

public abstract class TravelBankDepositTask extends MultiTask {
	
	class State {
		static final String END = "END";
		static final String TRAVEL_BANK = "TRAVEL_BANK";
		static final String BANKING = "BANKING";
		static final String TRAVEL_RETURN = "TRAVEL_RETURN";
	}
	
	public TravelBankDepositTask(Area returnArea, Area bankArea, String[] itemsToDeposit) {
		
		this.taskMap.put(State.TRAVEL_BANK, new TravelDefault(bankArea) {
			@Override
			public String stateIfArrived() {
				return State.BANKING;
			}
		});
		
		this.taskMap.put(State.BANKING, new BankDepositDefault(itemsToDeposit) {
			
			@Override
			public String stateIfDepositedAll() {
				return State.TRAVEL_RETURN;
			}
		});
		
		this.taskMap.put(State.TRAVEL_RETURN, new TravelDefault(returnArea) {
			@Override
			public String stateIfArrived() {
				return State.END;
			}
		});
	}
	
	@Override
	public String startState() {
		return State.TRAVEL_BANK;
	}

}
