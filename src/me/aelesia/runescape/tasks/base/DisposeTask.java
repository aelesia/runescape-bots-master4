package me.aelesia.runescape.tasks.base;

import me.aelesia.runescape.actions.InventoryActions;
import me.aelesia.runescape.script.RestManager.State;
import me.aelesia.runescape.utils.game.InventoryUtils;

public abstract class DisposeTask extends BaseTask {
	protected String[] itemsToDispose;
	
	public DisposeTask(String ...itemsToDispose) {
		this.itemsToDispose = itemsToDispose;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void exit() {
		InventoryActions.close();
	}
	
	@Override
	public void execute() {
		if (InventoryUtils.contains(itemsToDispose)) {
			InventoryActions.disposeAll(itemsToDispose);
		}
	}
}
