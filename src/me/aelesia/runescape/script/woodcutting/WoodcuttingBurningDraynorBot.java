package me.aelesia.runescape.script.woodcutting;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.script.Config;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.ChopTask;
import me.aelesia.runescape.tasks.base.DisposeTask;
import me.aelesia.runescape.tasks.base.StartFireTask;
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public class WoodcuttingBurningDraynorBot extends WoodcuttingBurningBase {

	@Override
	protected void initialize() {
		config.treesToChop = new String[] { E.Object.OAK };
		config.logsToBurn = Category.TINDER_LOGS;
		config.zone = Zones.DRAYNOR_OAKS;
	}

}
