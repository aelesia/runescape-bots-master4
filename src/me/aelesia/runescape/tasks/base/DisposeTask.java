package me.aelesia.runescape.tasks.base;

import me.aelesia.runescape.actions.InventoryActions;
import me.aelesia.runescape.exceptions.IllegalArgumentException;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class DisposeTask extends BaseTask {
	protected String[] itemsToDispose;
	
	public DisposeTask(String ...itemsToDispose) {
		if (CommonUtils.isEmpty(itemsToDispose)) {
			throw new IllegalArgumentException("");
		}
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
