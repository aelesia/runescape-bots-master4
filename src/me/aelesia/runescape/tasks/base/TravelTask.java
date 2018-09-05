package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;
import com.runemate.game.api.script.Execution;

import me.aelesia.runescape.actions.LocationActions;
import me.aelesia.runescape.script.Rest.State;
import me.aelesia.runescape.utils.game.PlayerUtils;

public abstract class TravelTask extends BaseTask {

	protected Area area;
	Coordinate c;
	
	public TravelTask(Area area) {
		this.area = area;
	}
	
	@Override
	public void initialize() {
		c = area.getRandomCoordinate();
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void execute() {
		LocationActions.traverseTo(c);
		if (Execution.delayUntil(()->PlayerUtils.isMoving(), 1000)) {
			Execution.delayUntil(()->!PlayerUtils.isMoving(), 1000);
		}
	}
}
