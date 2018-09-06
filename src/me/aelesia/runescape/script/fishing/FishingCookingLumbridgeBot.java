package me.aelesia.runescape.script.fishing;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.utils.game.Zone;
import me.aelesia.runescape.utils.general.CommonUtils;

public class FishingCookingLumbridgeBot extends FishingCookingBase {

	@Override
	protected void initialize() {
		config.fishingSpot = E.NPC.ROD_FISHING_SPOT;
		config.fishingAction = E.Action.LURE;
		config.itemsToCook = Category.RAW_FOOD;
		config.logsToBurn = Category.TINDER_LOGS;
		config.itemsToDispose = CommonUtils.mergeArrays(Category.BURNT_FOOD, Category.COOKED_FOOD, Category.RAW_FOOD);
		config.treesToChop = new String[] {E.Object.TREE, E.Object.DEAD_TREE};
		config.zone = Zones.LUMBRIDGE_GOBLIN_HUT;
	}
}
