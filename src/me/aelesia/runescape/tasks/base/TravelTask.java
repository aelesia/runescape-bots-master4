package me.aelesia.runescape.tasks.base;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GameObject.Type;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.hybrid.region.Players;
import com.runemate.game.api.script.Execution;

import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.exceptions.OutOfBoundsException;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.game.Zone;
import me.aelesia.runescape.utils.general.RandomUtils;

public abstract class TravelTask extends BaseTask {

	protected Zone zone;
	protected Area area;
	Coordinate c;
	Coordinate emptyC;
	int retryCount;
	
	LinkedList<Coordinate> positionQueue = new LinkedList<Coordinate>();
	
	public TravelTask(Area area) {
		this.area = area;
	}
	
	public TravelTask(Zone zone) {
		this.zone = zone;
		this.area = zone.area;
	}
	
	@Override
	public void initialize() {
		c = area.getRandomCoordinate();
		emptyC = null;
		if (this.zone!=null) {
			Logger.init("Travelling to " + this.zone.name);
		} else {
			Logger.init("Coordinate: " + c);	
		}
		retryCount = 0;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void execute() {
		List<GameObject> potentialBlockers = GameObjects.newQuery().types(Type.PRIMARY, Type.BOUNDARY).within(area).results().asList();
		if (!potentialBlockers.isEmpty() && emptyC == null) {
			List<Coordinate> emptyArea = area.getCoordinates();
			for (GameObject obj : potentialBlockers) {
				emptyArea.removeAll(obj.getArea().getCoordinates());
			}
			emptyC = emptyArea.get(RandomUtils.randomInt(0, emptyArea.size()-1));
			Logger.info("Empty coordinates: " + emptyC);
		}
		if (emptyC==null) {
			try {
				LocationActions.traverseTo(c);
			} catch (OutOfBoundsException e) {
				retryCount++;
				c = area.getRandomCoordinate();
				Logger.info("Attempting to find new coordinate: " + c + ". Retry count: " + retryCount);
				if (retryCount >= 10) {
					throw new OutOfBoundsException("Unable to find path to " + c);
				}
			}
		} else {
			try {
				LocationActions.traverseTo(emptyC);
			} catch (OutOfBoundsException e) {
				retryCount++;
				emptyC = null;
				if (retryCount >= 10) {
					throw new OutOfBoundsException("Unable to find path to " + c);
				}
			}
		}
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving(), 1000);
		}
	}
}
