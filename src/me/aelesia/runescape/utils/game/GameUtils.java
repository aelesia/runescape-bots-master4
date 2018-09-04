package me.aelesia.runescape.utils.game;


import com.runemate.game.api.hybrid.entities.Npc;

public class GameUtils {
//	
//	public static void pickUp(GroundItem item) {
//		if (item.isValid()) {
//			System.out.println("Picking up " + item.getDefinition().getName());
//			item.interact(E.Action.TAKE);
//			Execution.delay(1000);
//			Execution.delayUntil(()->PlayerUtils.isIdle(), 5000);
//		} else {
//			System.out.println("Item not valid");
//		}
//	}
//	
//	public static void attack(Npc target) {
//		System.out.println("Attacking: " + target);
//		target.interact(E.Action.ATTACK);
//		Execution.delay(5000);
//		Execution.delayUntil(()->(isDead(target) || PlayerUtils.isIdle()));
//		System.out.println("Finished fighting " + target.getName());
//	}
//	
////	public static boolean isFullHealth(Npc target) {
////		return target.getHealthGauge().getPercent() == 100;
////	}
//	
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
