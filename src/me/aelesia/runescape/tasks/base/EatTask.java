package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.entities.Player;
import com.runemate.game.api.hybrid.region.Players;

import me.aelesia.runescape.actions.InventoryActions;
import me.aelesia.runescape.script.RestManager.State;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;

public abstract class EatTask extends BaseTask {
	protected String[] itemsToEat;
	
	public EatTask(String ...itemsToEat) {
		this.itemsToEat = itemsToEat;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void execute() {
//		if (Players) {
//			InventoryActions.disposeAll(itemsToDispose);
//		}
	}
}
