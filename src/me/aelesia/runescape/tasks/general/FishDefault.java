package me.aelesia.runescape.tasks.general;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;

import me.aelesia.runescape.tasks.base.FishingTask;
import me.aelesia.runescape.utils.game.Logger;

abstract class FishDefault extends FishingTask {

	public FishDefault(String fishingSpot, String action) {
		super(fishingSpot, action);
	}

	@Override
	public String changeState() {
		if (Inventory.isFull()) {
			Logger.state("Inventory is full");
			return stateIfInventoryIsFull();
		}
		return null;
	}

	public abstract String stateIfInventoryIsFull();
}
