package me.aelesia.runescape.utils.game;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GameObject.Type;
import com.runemate.game.api.hybrid.entities.GroundItem;
import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.GroundItems;
import com.runemate.game.api.hybrid.region.Npcs;
import com.runemate.game.api.hybrid.region.Players;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.utils.general.RandomUtils;


public class LocationUtils {
	
//	public static List<GameObject> getGameObjectsNearMe(int radius) {
//		return GameObjects.newQuery().within(CommonUtils.getPosArea(radius)).results().asList();
//	}
	
//	public static List<GroundItem> getGroundItemsNearMe(int radius, String ...names) {
//		return GroundItems.newQuery().within(LocationUtils.getRadiusArea(radius)).names(names).results().asList();
//	}
//	
	public static List<GroundItem> getGroundItemsVisibleNearMe(int radius, String ...names) {
		return GroundItems.newQuery().within(LocationUtils.getSurroundingArea(radius)).names(names).visible().results().asList();
	}
	
	public static GroundItem getGroundItemVisibleNearest(int radius, String ...names) {
		return GroundItems.newQuery().within(LocationUtils.getSurroundingArea(radius)).names(names).visible().results().nearest();
	}

	public static List<GameObject> getGameObjectsAroundMe(int radius) {
		return GameObjects.newQuery().within(LocationUtils.getSurroundingArea(radius)).results().asList();
	}
	
	public static List<GameObject> getGameObjectsAroundMe(int radius, Type type) {
		return GameObjects.newQuery().types(type).within(LocationUtils.getSurroundingArea(radius)).results().asList();
	}

	public static List<GameObject> getGameObjectsAroundMe(int radius, String ...names) {
		return GameObjects.newQuery().within(LocationUtils.getSurroundingArea(radius)).names(names).results().asList();
	}
	
	public static GameObject getGameObjectNearestVisible(String action, String ...names) {
		return GameObjects.newQuery().names(names).actions(action).visible().results().nearest();
	}
	
	public static GameObject getGameObjectNearestVisibleAroundMe(int radius, String name) {
		return GameObjects.newQuery().within(LocationUtils.getSurroundingArea(radius)).names(name).visible().results().nearest();
	}
	
	public static GameObject getGameObjectNearestAroundMe(int radius, String ...names) {
		return GameObjects.newQuery().within(LocationUtils.getSurroundingArea(radius)).names(names).results().nearest();
	}
	
	public static GameObject getGameObjectNearest(String ...names) {
		return GameObjects.newQuery().names(names).results().nearest();
	}
	
	public static GameObject getGameObjectNearestWithin(Area area, String ...names) {
		return GameObjects.newQuery().within(area).names(names).results().nearest();
	}
	
//	public static GameObject getGameObjectNearestWithin(String[] names, String action, Area area) {
//		return GameObjects.newQuery().within(area).names(names).actions(action).results().nearest();
//	}
//	
//	public static GameObject getGameObjectNearestWithin(String name, String action, Area area) {
//		return GameObjects.newQuery().within(area).names(name).actions(action).results().nearest();
//	}
	
	public static List<Npc> getNpcsAroundMe(int radius, String ...names) {
		return Npcs.newQuery().within(LocationUtils.getSurroundingArea(radius)).names(names).results().asList();
	}
	
//	public static Npc getNpcsNearestVisible(String action, String ...names) {
//		if (CommonUtils.isBlank(action)) {
//			return Npcs.newQuery().names(names).actions(action).visible().results().nearest();	
//		}
//		return Npcs.newQuery().names(names).actions(action).visible().results().nearest();
//	}
	
	public static Npc getMonsterNearestAroundMe(int radius, String ...names) {
		return Npcs.newQuery().within(LocationUtils.getSurroundingArea(radius)).names(names).actions(E.Action.ATTACK).visible().filter(target -> {
		    return (target.getHealthGauge()==null);
		}).results().nearest();
	}
	
	public static Npc getMonsterNearestWithin(Area area) {
		return Npcs.newQuery().within(area).actions(E.Action.ATTACK).visible().filter(target -> {
		    return (target.getHealthGauge()==null);
		}).results().nearest();
	}
	
	public static Npc getMonsterNearestWithin(Area area, String ...names) {
		return Npcs.newQuery().within(area).names(names).actions(E.Action.ATTACK).visible().filter(target -> {
		    return (target.getHealthGauge()==null);
		}).results().nearest();
	}
	
	public static GameObject getMineNearest(Color ore) {
		return GameObjects.newQuery().names(E.Object.ROCKS).actions(E.Action.MINE).filter(target -> {
			return (!target.getDefinition().getColorSubstitutions().isEmpty()
					&& target.getDefinition().getColorSubstitutions().get(E.Ore.EMPTY).equals(ore));
		}).results().nearest();
	}
	
	public static GameObject getMineNearestWithin(Area area, Color ore) {
		return GameObjects.newQuery().names(E.Object.ROCKS).actions(E.Action.MINE).within(area).filter(target -> {
			return (!target.getDefinition().getColorSubstitutions().isEmpty()
					&& target.getDefinition().getColorSubstitutions().get(E.Ore.EMPTY).equals(ore));
		}).results().nearest();
	}
	
	public static GameObject getMineNearestAroundMe(int radius, Color ore) {
		return GameObjects.newQuery().names(E.Object.ROCKS).actions(E.Action.MINE).within(LocationUtils.getSurroundingArea(radius)).filter(target -> {
			return (!target.getDefinition().getColorSubstitutions().isEmpty()
					&& target.getDefinition().getColorSubstitutions().get(E.Ore.EMPTY).equals(ore));
		}).results().nearest();
	}
	
	public static Npc getNpcNearest(String ...names) {
		return Npcs.newQuery().names(names).results().nearest();
	}
	
	public static Npc getNpcNearestVisible(String ...names) {
		return Npcs.newQuery().names(names).visible().results().nearest();
	}
	
	public static Npc getNpcByActionNearestVisible(String action, String ...names) {
		return Npcs.newQuery().names(names).actions(action).visible().results().nearest();
	}
	
//	public static boolean isNpcVisible(String ...names) {
//		return (LocationUtils.getNpcNearestVisible(names) != null);
//	}
	
	public static boolean isStandingOnObject() {
		return !LocationUtils.getGameObjectsAroundMe(0, Type.PRIMARY).isEmpty();
	}
	
//	public static boolean isNpcVisible(String action, String ...names) {
//		return (LocationUtils.getNpcsNearestVisible(action, names) != null);
//	}
	
	public static Coordinate getEmptySpaceAroundMe() {
		List<Coordinate> emptySpaces = getEmptySpacesAroundMe(1);
    	Coordinate emptySpace = emptySpaces.get(RandomUtils.randomInt(0, emptySpaces.size()-1));
    	return emptySpace;
	}
	
	public static Coordinate getEmptySpaceAroundMeWithin(Area area) {
		List<Coordinate> emptySpaces = getEmptySpacesAroundMe(1);
		for (Iterator<Coordinate> iterator = emptySpaces.iterator(); iterator.hasNext();) {
			Coordinate c = iterator.next();
		    if (area!=null && !area.contains(c)) {
		    	iterator.remove();
		    }
		}
    	Coordinate emptySpace = emptySpaces.get(RandomUtils.randomInt(0, emptySpaces.size()-1));
    	return emptySpace;
	}
	
	public static List<Coordinate> getEmptySpacesAroundMe(int radius) {
    	List<GameObject> objectsNearMe = LocationUtils.getGameObjectsAroundMe(radius+2, Type.PRIMARY);
    	List<Coordinate> emptySpaces = LocationUtils.getSurroundingArea(radius).getCoordinates();
    	for (GameObject object : objectsNearMe) {
    		emptySpaces.removeAll(object.getArea().getCoordinates());
    		if (emptySpaces.contains(object.getPosition())) {
    			emptySpaces.remove(object.getPosition());
 	  	  	}
     	}

		if (emptySpaces.size() == 0) {
			if (radius==1) {
				emptySpaces = getEmptySpacesAroundMe(radius+1);
			}
		}
		return emptySpaces;
	}
	
	public static double distanceTo(Locatable locatable) {
		return Players.getLocal().distanceTo(locatable);
	}
	
	public static boolean isNearby(Locatable locatable) {
		return distanceTo(locatable) <= 5;
	}
	
	public static boolean isWithin(Area area) {
		return area.contains(Players.getLocal().getPosition());
	}
	
	public static Area getSurroundingArea(int radius) {
		Coordinate pos = Players.getLocal().getPosition();
		Coordinate c1 = new Coordinate(pos.getX()-radius, pos.getY()-radius, 0);
		Coordinate c2 = new Coordinate(pos.getX()+radius, pos.getY()+radius, 0);
		return new Area.Rectangular(c1, c2);
	}
	
//	public static List<Coordinate> getCoordinateArea(int radius) {
//		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
//		Coordinate pos = Players.getLocal().getPosition();
//		for (int x=-radius; x<=radius; x++) {
//			for (int y=-radius; y<=radius; y++) {
//				coordinateList.add(new Coordinate(pos.getX()+x, pos.getY()+y, 0));
//			}
//		}
//		return coordinateList;
//	}
	
//	public static List<Coordinate> coordinatesFromArea(int radius) {
//		List<Coordinate> coordinateList = new ArrayList<Coordinate>();
//		Coordinate pos = Players.getLocal().getPosition();
//		for (int x=-radius; x<=radius; x++) {
//			for (int y=-radius; y<=radius; y++) {
//				coordinateList.add(new Coordinate(pos.getX()+x, pos.getY()+y, 0));
//			}
//		}
//		return coordinateList;
//	}
}
