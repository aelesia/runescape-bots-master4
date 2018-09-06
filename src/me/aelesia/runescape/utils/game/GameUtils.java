package me.aelesia.runescape.utils.game;


import com.runemate.game.api.hybrid.entities.Npc;

public class GameUtils {
	
	public static boolean isDead(Npc target) {
		if (target==null) {
			return true;
		}
		else if (target.getHealthGauge() != null) {
			return target.getHealthGauge().getPercent() == 0;
		}
		return false;
	}
}
