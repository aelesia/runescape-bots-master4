package me.aelesia.runescape.utils.game;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.Players;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.exceptions.IllegalStateException;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;

public class DetermineUtils {
	
//	public static String zoneName() {
//		Coordinate currentPos = Players.getLocal().getPosition();
//		
//		for (Map.Entry<String, Area> entry : Category.ZONES.entrySet()) {
//		    String name = entry.getKey();
//		    Area area = entry.getValue();
//			if (area.contains(currentPos)) {
//				return name;
//			}
//		}
//		return null;
//	}
	
	public static Zone findZone(int radius) {
		Coordinate currentPos = Players.getLocal().getPosition();
		for (Zone z : Zones.ALL) {
			if (z.area.contains(currentPos)) {
				return z;
			}
		}
		return new Zone(null, LocationUtils.getSurroundingArea(radius));
	}
	
	public static String[] findFishingspotAction() {
		String[] fishingSpotAction = new String[2];
		
		Npc fishingSpot = LocationUtils.getNpcNearest(E.NPC.FISHING_SPOT, E.NPC.ROD_FISHING_SPOT);
		if (fishingSpot==null) {
			throw new ObjectNotFoundException("No fishing spot found");
		}
		fishingSpotAction[0] = fishingSpot.getName();
		
		if (Inventory.contains(E.Item.SMALL_FISHING_NET) && fishingSpotAction[0].equals(E.NPC.FISHING_SPOT)) {
			fishingSpotAction[1] = E.Action.NET;
		} else if (Inventory.contains(E.Item.FLY_FISHING_ROD) && fishingSpotAction[0].equals(E.NPC.ROD_FISHING_SPOT)) {
			fishingSpotAction[1] = E.Action.LURE;
		} else {
			throw new IllegalStateException("No valid equipment found for " + fishingSpotAction[0]);
		}
		
		return fishingSpotAction;
	}
}
