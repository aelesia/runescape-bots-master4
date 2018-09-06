package me.aelesia.runescape.tasks.general;

import me.aelesia.runescape.tasks.base.DisposeTask;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.Logger;

public abstract class DisposeDefault extends DisposeTask {

	public DisposeDefault(String ...itemsToDispose) {
		super(itemsToDispose);
	}
	
	@Override
	public String changeState() {
		if (!InventoryUtils.contains(this.itemsToDispose)) {
			Logger.state("No more items to dispose of");
			return stateIfNoMoreItemsToDispose();
		}
		return null;
	}
	
	public abstract String stateIfNoMoreItemsToDispose();
}