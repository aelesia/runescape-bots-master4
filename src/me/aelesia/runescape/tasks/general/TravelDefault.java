package me.aelesia.runescape.tasks.general;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.region.Players;

import me.aelesia.runescape.tasks.base.TravelTask;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.game.Zone;

public abstract class TravelDefault extends TravelTask {

	public TravelDefault(Area area) {
		super(area);
	}
	
	public TravelDefault(Zone zone) {
		super(zone);
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