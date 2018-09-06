package me.aelesia.runescape.tasks.general;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.Players;

import me.aelesia.runescape.tasks.base.TravelTask;
import me.aelesia.runescape.utils.game.Logger;

public abstract class TravelDefault extends TravelTask {

	public TravelDefault(Area area) {
		super(area);
	}

	@Override
	public String changeState() {
		if (this.area.contains(Players.getLocal().getPosition())) {
			Logger.state("Arrived at area");
			return stateIfArrived();
		}
		return null;
	}

	public abstract String stateIfArrived();
}