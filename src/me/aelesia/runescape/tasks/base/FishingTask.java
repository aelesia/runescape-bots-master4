package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;
import me.aelesia.runescape.script.RestManager;
import me.aelesia.runescape.script.RestManager.State;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;

public abstract class FishingTask extends BaseTask {

	String fishingSpot;
	String action;
	
	public FishingTask(String fishingSpot, String action) {
		this.fishingSpot = fishingSpot;
		this.action = action;
	}

	@Override
	public void validate() {
		if (E.NPC.ROD_FISHING_SPOT.equals(this.fishingSpot)) {
    		if (E.Action.LURE.equals(this.action) && !Inventory.contains(E.Item.FEATHER)) {
				throw new ObjectNotFoundException(E.Item.FEATHER);
    		} else if (E.Action.BAIT.equals(this.action) && !Inventory.contains(E.Item.FISHING_BAIT)) {
				throw new ObjectNotFoundException(E.Item.FISHING_BAIT);
    		}
    	}
		if (LocationUtils.getNpcNearest(this.fishingSpot) == null) {
			throw new ObjectNotFoundException(this.fishingSpot);
		}
	}
	
	@Override
	public void execute() {
		if (PlayerUtils.isIdle()) {
			Npc fishingSpotObj = LocationUtils.getNpcNearest(this.fishingSpot);
			if (fishingSpotObj.isVisible()) {
				if (GameActions.fish(fishingSpotObj, action)) {
					RestManager.rest(State.OCCUPIED);
				}
    		} else {
    			LocationActions.shortWalkTo(fishingSpotObj);
    		}
		}
	}
}
