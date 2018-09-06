package me.aelesia.runescape.tasks.multi;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.tasks.base.CookTask;
import me.aelesia.runescape.tasks.base.StartFireTask;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;

public abstract class CookFireTask extends MultiTask {
	
	class State {
		static final String END = "END";
		static final String COOKING = "COOKING";
		static final String FIRE_STARTING = "FIRE_STARTING";
	}
	
	public CookFireTask(String[] itemsToCook, String[] logsToBurn) {
		taskMap.put(State.COOKING, new CookTask(itemsToCook) {
			@Override
			public String changeState() {
				if (!InventoryUtils.contains(itemsToCook)) {
					Logger.state("No more items to cook");
					return State.END;
				}
				else if (LocationUtils.getGameObjectNearestVisibleAroundMe(2, E.Object.FIRE) == null) {
					Logger.state("No fire found near me");
					return State.FIRE_STARTING;
				}
				return null;
			}
		});
		
		taskMap.put(State.FIRE_STARTING, new StartFireTask(logsToBurn) {	
			@Override
			public String changeState() {				
				if (LocationUtils.getGameObjectNearestVisibleAroundMe(2, E.Object.FIRE) != null) {
					Logger.state("Fire found");
					return State.COOKING;
				} else if (!InventoryUtils.contains(logsToBurn)) {
					Logger.state("No logs in inventory");
					return State.END;
				}
				return null;
			}
		});
	}
	
	@Override
	public String startState() {
		return State.COOKING;
	}

}
