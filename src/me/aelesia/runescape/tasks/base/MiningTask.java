package me.aelesia.runescape.tasks.base;

import java.awt.Color;
import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Area;

import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.exceptions.IllegalArgumentException;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;
import me.aelesia.runescape.script.RestManager;
import me.aelesia.runescape.script.Rest.State;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.ThreadUtils;

public abstract class MiningTask extends BaseTask {

	protected Area area;
	protected Color[] ore;
	
	public MiningTask(Area area, Color ...ore) {
		if (area == null || ore == null) {
			throw new IllegalArgumentException("");
		}
		this.area = area;
		this.ore = ore;
	}

	@Override
	public void validate() {
		if (!InventoryUtils.contains(Category.PICKAXE)) {
			throw new ObjectNotFoundException("No pick found");
    	}
	}
	
	@Override
	public void execute() {
		if (PlayerUtils.isIdle()) {
			GameObject rock = LocationUtils.getMineNearestWithin(this.area, this.ore);
			if (rock==null) {
				Logger.pause("Unable to find " + rock + ". Waiting.") ;
				ThreadUtils.sleepFor(1000, 5000);
			} else if (rock.isVisible() && LocationUtils.isNearby(rock)) {
				if (GameActions.mine(rock)) {
					RestManager.get(PlayerUtils.name()).rest(State.OCCUPIED);
				}
			} else {
				LocationActions.shortWalkTo(rock);
			}
		}
	}
}
