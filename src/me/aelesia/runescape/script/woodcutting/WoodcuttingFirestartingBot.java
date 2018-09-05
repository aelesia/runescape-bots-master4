package me.aelesia.runescape.script.woodcutting;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.Config;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.ChopTask;
import me.aelesia.runescape.tasks.base.DisposeTask;
import me.aelesia.runescape.tasks.base.StartFireTask;
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public class WoodcuttingFirestartingBot extends StateBot {

	Config config = new Config();
	
	class State {
		static final String CHOPPING = "CHOPPING";
		static final String DISPOSING = "DISPOSING";
		static final String FIRESTARTING = "FIRESTARTING";
	}
	
	@Override
	protected void initialize() {
		config.treesToChop = new String[] { E.Object.TREE };
		config.logsToBurn = Category.TINDER_LOGS;
		config.zone = DetermineUtils.findZone(12);
		System.out.println(config);
	}
	
	@Override
	protected void registerTasks() {
    	this.taskMap.put(State.CHOPPING, new ChopTask(config.zone.area, config.treesToChop) {			
			@Override
			public String changeState() {
				if (Inventory.getItems(config.logsToBurn).size() > 16) {
					System.out.println("[STATE] Acquired too many logs: " + Inventory.getItems(Category.TINDER_LOGS).size()) ;
					return State.DISPOSING;
				} else if (Inventory.getItems(config.logsToBurn).size() > 8) {
					System.out.println("[STATE] Acquired sufficient number of logs: " + Inventory.getItems(Category.TINDER_LOGS).size()) ;
					return State.FIRESTARTING;
				}
				return null;
			}
		});
    	
    	this.taskMap.put(State.DISPOSING, new DisposeTask(CommonUtils.mergeStringArray(E.Item.ASHES, config.logsToBurn)) {
			@Override
			public String changeState() {
				if (!InventoryUtils.contains(this.itemsToDispose)) {
					return State.CHOPPING;
				}
				return null;
			}
		});
    	
    	this.taskMap.put(State.FIRESTARTING, new StartFireTask(config.zone.area, config.logsToBurn) {
			@Override
			public String changeState() {
				if (!InventoryUtils.contains(this.logsToBurn)) {
					return State.CHOPPING;
				}
				return null;
			}
		});
	}
	
	@Override
	protected String startingState() {
		return State.CHOPPING;
	}
}
