package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.location.Area;
import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.exceptions.IllegalArgumentException;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;
import me.aelesia.runescape.script.RestManager;
import me.aelesia.runescape.script.Rest.State;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.CommonUtils;
import me.aelesia.runescape.utils.general.ThreadUtils;

public abstract class ChopTask extends BaseTask {

	protected Area area;
	protected String[] treesToChop;
	
	public ChopTask(Area area, String ...treesToChop) {
		if (area == null || CommonUtils.isEmpty(treesToChop)) {
			throw new IllegalArgumentException("");
		}
		this.area = area;
		this.treesToChop = treesToChop;
	}

	public void validate() {
		if (!InventoryUtils.contains(Category.AXE) && !InventoryUtils.equipped(Category.AXE)) {
			throw new ObjectNotFoundException("No axe found");
		} 
		else if (LocationUtils.getGameObjectNearestWithin(this.area, 
				CommonUtils.mergeStringArray(E.Object.TREE_STUMP, this.treesToChop)) == null) {
			ThreadUtils.sleepFor(5000);
			if (LocationUtils.getGameObjectNearestWithin(this.area, 
				CommonUtils.mergeStringArray(E.Object.TREE_STUMP, this.treesToChop)) == null ) {
				throw new ObjectNotFoundException("No trees found");
			}
		}
	}
	
	@Override
	public void execute() {
		if (PlayerUtils.isIdle()) {
			GameObject tree = LocationUtils.getGameObjectNearestWithin(this.area, treesToChop);
			if (tree==null) {
				Logger.pause("Unable to find " + tree + ". Waiting.");
				ThreadUtils.sleepFor(1000, 5000);
			} else if (tree.isVisible() && LocationUtils.isNearby(tree)) {
				if (GameActions.chop(tree)) {
					RestManager.get(PlayerUtils.name()).rest(State.OCCUPIED);
				}
			} else {
				LocationActions.shortWalkTo(tree);
			}
		}
	}
}
