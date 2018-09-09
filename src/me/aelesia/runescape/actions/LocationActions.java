package me.aelesia.runescape.actions;

import com.runemate.game.api.hybrid.entities.LocatableEntity;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.Traversal;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.exceptions.OutOfBoundsException;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;

public class LocationActions {
	
	public static boolean shortWalkTo(LocatableEntity locatable) {
        Path p = BresenhamPath.buildTo(locatable);
        return shortWalkTo(p);
	}
	
	public static boolean shortWalkTo(Coordinate c) {
        Path p = BresenhamPath.buildTo(c);
		return shortWalkTo(p);
	}
	
	private static boolean shortWalkTo(Path p) {
        if (p == null) {
        	throw new OutOfBoundsException("[ERROR] No valid path");
        }
        Logger.action("Walking to destination");
        p.step();
        if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
        	Execution.delayUntil(()->!PlayerUtils.isMoving());
        	Logger.success("Arrived at destination");
        	return true;
		}
        return false;
	}
	
	public static boolean walkHere(Coordinate c) {
		c.interact(E.Action.WALK_HERE);
        Logger.action("WALK HERE to destination");
        if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
        	Execution.delayUntil(()->!PlayerUtils.isMoving());
        	Logger.success("Arrived at destination");
        	return true;
        }
        return false;
	}
	
	public static void traverseTo(Coordinate c) {
    	if (LocationUtils.distanceTo(c) < 3) {
    		LocationActions.walkHere(c);
    	} else {
			Path p = Traversal.getDefaultWeb().getPathBuilder().buildTo(c);
	        if (p == null) {
        		throw new OutOfBoundsException("[ERROR] No valid path. Current pos: " + Players.getLocal().getPosition() + ", Target: " + c);
	        }
	        Logger.action("Heading to destination");
	        p.step();
		}
	}
}
