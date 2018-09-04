package me.aelesia.runescape.script.debug;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.StartFireTask;
import me.aelesia.runescape.tasks.general.TravelDefault;

public class TestBot extends StateBot {
	
	class State {
		static final String TRAVEL_OAK = "TEST";
		static final String TRAVEL_BANK = "TEST2";
	}
	
	
	@Override
	protected void registerTasks() {
		this.taskMap.put(State.TRAVEL_OAK, new TravelDefault(Zones.DRAYNOR_OAKS_2_EMPTY.area) {			
			@Override
			public String stateIfArrived() {
				return State.TRAVEL_BANK;
			}
		});
		this.taskMap.put(State.TRAVEL_BANK, new TravelDefault(Zones.BANK_DRAYNOR.area) {			
			@Override
			public String stateIfArrived() {
				return State.TRAVEL_OAK;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.TRAVEL_OAK;
	}

	@Override
	protected void initialize() {
	}
}

