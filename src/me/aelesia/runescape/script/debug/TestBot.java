package me.aelesia.runescape.script.debug;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.general.DisposeDefault;
import me.aelesia.runescape.tasks.general.TravelDefault;
import me.aelesia.runescape.utils.general.ThreadUtils;

public class TestBot extends StateBot {
	
	class State {
		static final String TRAVEL_OAK = "TEST";
		static final String TRAVEL_BANK = "TEST2";
	}
	
	
	@Override
	protected void registerTasks() {
		this.taskMap.put(State.TRAVEL_OAK, new TravelDefault(Zones.DRAYNOR_OAKS_2.area) {			
			@Override
			public String stateIfArrived() {
				return State.TRAVEL_BANK;
			}
		});
		this.taskMap.put(State.TRAVEL_BANK, new TravelDefault(Zones.DRAYNOR_OAKS_2.area) {			
			@Override
			public String stateIfArrived() {
				return State.TRAVEL_OAK;
			}
		});
//		this.taskMap.put(State.TRAVEL_OAK, new DisposeDefault(E.Item.LOGS) {
//			
//			@Override
//			public String stateIfNoMoreItemsToDispose() {
//				ThreadUtils.sleepFor(1000);
//				return State.TRAVEL_OAK;
//			}
//		});
	}
	
	@Override
	protected String startingState() {
		return State.TRAVEL_OAK;
	}

	@Override
	protected void initialize() {
	}
}

