package me.aelesia.runescape.tasks.general;

import me.aelesia.runescape.tasks.base.BuryTask;
import me.aelesia.runescape.utils.game.InventoryUtils;

public abstract class BuryDefault extends BuryTask {

	public BuryDefault(String[] bonesToBury) {
		super(bonesToBury);
	}

	@Override
	public String changeState() {
		if (!InventoryUtils.contains(this.bonesToBury)) {
			return this.stateIfNoMoreBones();
		}
		return null;
	}

	public abstract String stateIfNoMoreBones();
}