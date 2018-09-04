package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.location.Area;

import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.script.RestManager;
import me.aelesia.runescape.script.RestManager.State;
import me.aelesia.runescape.tasks.base.BaseTask;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.ThreadUtils;

public abstract class FightTask extends BaseTask {

	protected Area area;
	protected String[] monstersToKill;
	private int killCount;
	
	public FightTask(Area area, String... monstersToKill) {
		this.area = area;
		this.monstersToKill = monstersToKill;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void initialize() {
		killCount = 0;
	}

	@Override
	public void execute() {
		if (!PlayerUtils.isMoving()) {
//			Npc target = LocationUtils.getMonsterNearestAroundMe(7, monstersToKill);
//			if (target==null) {
			Npc	target;
			if (monstersToKill !=null && monstersToKill.length > 0) {
				target = LocationUtils.getMonsterNearestWithin(area, monstersToKill);
			} else {
				target = LocationUtils.getMonsterNearestWithin(area);
			}
//			}
			if (target==null) {
				System.out.println("[PAUSE] Unable to find " + target + ". Waiting.") ;
				ThreadUtils.sleepFor(1000, 5000);
			} else {
				if (!target.isVisible()) {
					LocationActions.shortWalkTo(target);
				}
				if (GameActions.attack(target)) {
					killCount++;
					System.out.println("killCount: " + killCount);
					RestManager.rest(State.OCCUPIED);
				}
			}
		}
	}

	public int killCount() {
		return this.killCount;
	}
}
