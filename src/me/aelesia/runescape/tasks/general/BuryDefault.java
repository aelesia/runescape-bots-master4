package me.aelesia.runescape.tasks.general;

import me.aelesia.runescape.tasks.base.BuryTask;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.Logger;

public abstract class BuryDefault extends BuryTask {

	public BuryDefault(String[] bonesToBury) {
		super(bonesToBury);
	}

	@Override
	public String changeState() {
		if (!InventoryUtils.contains(this.bonesToBury)) {
			Logger.state("No more bones to bury");
			return this.stateIfNoMoreBones();
		}
		return null;
	}

	public abstract String stateIfNoMoreBones();
}