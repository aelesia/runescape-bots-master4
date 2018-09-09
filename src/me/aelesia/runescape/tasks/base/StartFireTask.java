package me.aelesia.runescape.tasks.base;


import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Area;

import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.exceptions.IllegalArgumentException;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;
import me.aelesia.runescape.script.RestManager;
import me.aelesia.runescape.script.Rest.State;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class StartFireTask extends BaseTask {

	protected Area area;
	protected String[] logsToBurn;
	protected int numLogsBurned;
	
	public StartFireTask(String[] logsToBurn) {
		if (CommonUtils.isEmpty(logsToBurn)) {
			throw new IllegalArgumentException("");
		}
		this.logsToBurn = logsToBurn;
	}
	
//	public StartFireTask(Area area, String[] logsToBurn) {
//		this.area = area;
//		this.logsToBurn = logsToBurn;
//	}
	
	@Override
	public void initialize() {
		numLogsBurned=0;
	}
	
	@Override
	public void validate() {
		if (!Inventory.contains(E.Item.TINDERBOX)) {
			throw new ObjectNotFoundException(E.Item.TINDERBOX);
		}
	}

	@Override
	public boolean execute() {
		if (PlayerUtils.isIdle()) {
			if (LocationUtils.isStandingOnObject()) {
	    		Logger.info("I'm standing on something");
	    		if (area!=null) {
	    			return LocationActions.walkHere(LocationUtils.getEmptySpaceAroundMeWithin(this.area));
	    		} else {
	    			return LocationActions.walkHere(LocationUtils.getEmptySpaceBesideMe());
	    		}
			} else {
				if (GameActions.startFire(logsToBurn)) {
					numLogsBurned++;
					RestManager.get(PlayerUtils.name()).rest(State.DISTRACTED);
					return true;
				}
			}
		}
		return false;
	}
	
	public int numLogsBurned() {
		return this.numLogsBurned;
	}
}
