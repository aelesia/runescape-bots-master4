package me.aelesia.runescape.script.woodcutting;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;

public class WoodcuttingBurningDraynorBot extends WoodcuttingBurningBase {

	@Override
	protected void initialize() {
		config.treesToChop = new String[] { E.Object.OAK };
		config.logsToBurn = Category.TINDER_LOGS;
		config.zone = Zones.DRAYNOR_OAKS;
	}

}
