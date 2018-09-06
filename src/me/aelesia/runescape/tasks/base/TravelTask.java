package me.aelesia.runescape.tasks.base;

import java.util.List;

import com.runemate.game.api.hybrid.entities.GameObject;
import com.runemate.game.api.hybrid.entities.GameObject.Type;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.region.GameObjects;
import com.runemate.game.api.script.Execution;

import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.exceptions.OutOfBoundsException;
import me.aelesia.runescape.script.Rest.State;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.RandomUtils;

public abstract class TravelTask extends BaseTask {

	protected Area area;
	Coordinate c;
	int retryCount;
	
	public TravelTask(Area area) {
		this.area = area;
	}
	
	@Override
	public void initialize() {
		c = area.getRandomCoordinate();
		Logger.init("Coordinate: " + c);
		retryCount = 0;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void execute() {
//		List<GameObject> potentialBlockers = GameObjects.newQuery().types(Type.PRIMARY).within(area).results().asList();
//		if (!potentialBlockers.isEmpty() && emptyC == null) {
//			List<Coordinate> emptyArea = area.getCoordinates();
//			for (GameObject obj : potentialBlockers) {
//				emptyArea.removeAll(obj.getArea().getCoordinates());
//			}
//			Logger.info("Located empty coordinate within area");
//			emptyC = emptyArea.get(RandomUtils.randomInt(0, emptyArea.size()-1));
//		}
//		if (emptyC==null) {
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
//		} else {
//			LocationActions.traverseTo(emptyC);
//		}
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving(), 1000);
		}
	}
}
