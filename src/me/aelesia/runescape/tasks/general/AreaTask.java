package me.aelesia.runescape.tasks.general;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.Players;

import me.aelesia.runescape.exceptions.OutOfBoundsException;
import me.aelesia.runescape.tasks.base.BaseTask;


abstract class AreaTask extends BaseTask {
	
	protected Area area;
	
	public AreaTask(Area area) {
		this.area = area;
	}

	protected void validateWithinArea() {
		if (this.area!= null && !this.area.contains(Players.getLocal().getPosition())) {
			throw new OutOfBoundsException("Expected Area: " + this.area + ", Position: " + Players.getLocal().getPosition());
		}
	}
}
