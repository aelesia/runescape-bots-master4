package me.aelesia.runescape.script.fighting;

import me.aelesia.runescape.consts.Category;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;
import me.aelesia.runescape.utils.game.DetermineUtils;

public class FightingChickenBot extends FightingBase {
	@Override
	protected void initialize() {
		config.monstersToFight = new String[] { E.NPC.CHICKEN };
		config.itemsToLoot = new String[] { E.Item.BONES, E.Item.FEATHER };
		config.bonesToBury = Category.BONES;
		config.zone = DetermineUtils.findZone(6);
		
		config.minLoot = 5;
		config.maxLoot = config.minLoot * 2;
		config.kills = 4;
	}
	
}
