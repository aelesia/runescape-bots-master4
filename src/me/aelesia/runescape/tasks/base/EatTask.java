package me.aelesia.runescape.tasks.base;

import java.util.Arrays;

import com.runemate.game.api.hybrid.local.hud.interfaces.Health;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.actions.InventoryActions;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.exceptions.IllegalArgumentException;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class EatTask extends BaseTask {
	protected String[] itemsToEat;
	protected int threshold;
	
	public EatTask(int threshold, String ...itemsToEat) {
		if (threshold <= 0 || threshold >= 100 || CommonUtils.isEmpty(itemsToEat)) {
			throw new IllegalArgumentException("");
		}
		this.threshold = threshold;
		this.itemsToEat = itemsToEat;
	}
	
	@Override
	public void validate() {
		if (!InventoryUtils.contains(this.itemsToEat)) {
			throw new ObjectNotFoundException("No more food");
		}
	}
	
	@Override
	public void execute() {
		if (this.isBelowHealthThreshold()) {
			InventoryActions.interact(E.Action.EAT, Inventory.getItems(itemsToEat).first());
		}
	}
	
	public boolean isBelowHealthThreshold() {
		if (Health.getCurrentPercent() <= threshold) {
			return true;
		}
		return false;
	}
}
