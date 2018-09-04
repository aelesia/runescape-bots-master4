package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.entities.details.Locatable;
import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.hybrid.location.navigation.Path;
import com.runemate.game.api.hybrid.location.navigation.basic.BresenhamPath;
import com.runemate.game.api.hybrid.region.GameObjects;

import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;
import me.aelesia.runescape.exceptions.OutOfBoundsException;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class ShortTravelTask extends BaseTask {

	protected Area area;
	protected String locatableName;
	protected Coordinate coordinate;
	
	public ShortTravelTask(Area area) {
		this.area = area;
	}
	
	public ShortTravelTask(String locatableName) {
		this.locatableName = locatableName;
	}
	
	public ShortTravelTask(Coordinate coordinate) {
		this.coordinate = coordinate;
	}
	
	@Override
	public void initialize() {
		if (area!=null) {
			coordinate = area.getRandomCoordinate();
		} else if (!CommonUtils.isBlank(locatableName)) {
			coordinate = LocationUtils.getGameObjectNearestAroundMe(10, locatableName).getPosition();
		}
	}
	
	@Override
	public void validate() {
		if (coordinate == null) {
			throw new ObjectNotFoundException("No coordinates provided");
		}
	}
	
	@Override
	public void execute() {
		if (PlayerUtils.isIdle()) {
			LocationActions.shortWalkTo(coordinate);
		}
	}
}
