package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.entities.GameObject;
import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.RestManager;
import me.aelesia.runescape.script.RestManager.State;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;

public abstract class CookTask extends BaseTask {

	protected String[] itemsToCook;
	
	public CookTask(String ...itemsToCook) {
		this.itemsToCook = itemsToCook;
	}

	@Override
	public void validate() {}
	
	@Override
	public void execute() {
		if (PlayerUtils.isIdle()) {
			GameObject fire = LocationUtils.getGameObjectNearestVisibleAroundMe(1, E.Object.FIRE);
			if (fire != null) {
				if (GameActions.cookFood(fire, itemsToCook)) {
					RestManager.rest(State.DISTRACTED);
				}
			}
		}
	}
}
