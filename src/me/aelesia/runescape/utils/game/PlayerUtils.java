package me.aelesia.runescape.utils.game;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;

import me.aelesia.runescape.consts.Animation;
import me.aelesia.runescape.consts.E.Action;
import me.aelesia.runescape.utils.general.ThreadUtils;


public class PlayerUtils {
	
	public static boolean isIdle() {
		if (Players.getLocal().getAnimationId()==-1 && !Players.getLocal().isMoving() && Players.getLocal().getHealthGauge()==null) {
			return true;
		}
		return false;
	}
	
	public static boolean isMoving() {
		if (Players.getLocal().isMoving()) {
			return true;
		}
		return false;
	}
	
	@Deprecated
	public static boolean isInCombat() {
		for (int i=0; i<1500; i+=10) {
			ThreadUtils.sleepFor(10);
			if (Animation.COMBAT.contains(Players.getLocal().getAnimationId())) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInCombat2() {
		Npc monster = Npcs.newQuery().actions(Action.ATTACK).targetedBy(Players.getLocal()).results().first();
		if (monster!=null) {
			if (monster.getHealthGauge()!=null) {
				if (monster.getHealthGauge().getPercent() == 0) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static boolean isStartingFire() {
		if (Players.getLocal().getAnimationId() == Animation.STARTING_FIRE) {
			return true;
		}
		return false;
	}
	
	public static boolean isFishing() {
		if (Animation.FISHING.contains(Players.getLocal().getAnimationId())) {
			return true;
		}
		return false;
	}
	
	public static boolean isMining() {
		if (Animation.MINING.contains(Players.getLocal().getAnimationId())) {
			return true;
		}
		return false;
	}
	
	public static boolean isChopping() {
		if (Animation.CHOPPING.contains(Players.getLocal().getAnimationId())) {
			return true;
		}
		return false;
	}
	
	public static boolean isCooking() {
		for (int i=0; i<500; i+=10) {
			ThreadUtils.sleepFor(10);
			if (Players.getLocal().getAnimationId() == Animation.COOKING) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isIdleFor(long ms) {
		if (isIdle()) {
			for (int i=0; i<ms; i+=10) {
				ThreadUtils.sleepFor(10);
				if (!PlayerUtils.isIdle()) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	public static String name() {
		return Players.getLocal().getName();
	}
}
