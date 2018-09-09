package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.exceptions.ObjectNotFoundException;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

@Deprecated
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
	public boolean execute() {
		if (PlayerUtils.isIdle()) {
			return LocationActions.shortWalkTo(coordinate);
		}
		return false;
	}
}
