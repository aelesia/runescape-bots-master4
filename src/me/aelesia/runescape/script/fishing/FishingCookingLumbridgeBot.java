package me.aelesia.runescape.script.fishing;

import java.util.List;

import com.runemate.game.api.hybrid.entities.Npc;
import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.region.Npcs;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.Config;
import me.aelesia.runescape.script.StateBot;
import me.aelesia.runescape.tasks.base.FishingTask;
import me.aelesia.runescape.tasks.multi.CookFireDisposeChop;
import me.aelesia.runescape.utils.game.DetermineUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public class FishingCookingLumbridgeBot extends FishingCookingBot {
	@Override
	protected void initialize() {
		config.treesToChop = new String[] { E.Object.TREE, E.Object.DEAD_TREE };
		config.logsToBurn = Category.TINDER_LOGS;
		config.itemsToDispose = CommonUtils.mergeArrays(Category.BURNT_FOOD, Category.COOKED_FOOD, Category.RAW_FOOD);
		config.itemsToCook = Category.RAW_FOOD;
		config.fishingSpotAction = new String[] { E.NPC.ROD_FISHING_SPOT, E.Action.LURE };
		config.zone = DetermineUtils.findZone(10);
		System.out.println("[CONFIG] " + "\n" + config);
	}
}
