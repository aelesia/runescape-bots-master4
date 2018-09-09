package me.aelesia.runescape.tasks.base;

import com.runemate.game.api.hybrid.entities.GroundItem;

import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.exceptions.IllegalArgumentException;
import me.aelesia.runescape.tasks.base.BaseTask;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class LootingTask extends BaseTask {
	
	public static final int LOOT_RADIUS = 4; 
	
	protected String[] itemsToLoot;
	protected int itemsPickedUp;
	
	public LootingTask(String ...itemsToLoot) {
		if (CommonUtils.isEmpty(itemsToLoot)) {
			throw new IllegalArgumentException("");
		}
		this.itemsToLoot = itemsToLoot;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void initialize() {
		itemsPickedUp = 0;
	}

	@Override
	public boolean execute() {
		if (PlayerUtils.isIdle()) {
			GroundItem item = LootingTask.getAdjacentLoot(this.itemsToLoot);

			if (item!=null && item.isValid()) {
				if (GameActions.loot(item)) {
					itemsPickedUp++;
					return true;
				}
			}
		}
		return false;
	}
	
	public static GroundItem getAdjacentLoot(String ...itemsToLoot) {
		return LocationUtils.getGroundItemVisibleNearest(LootingTask.LOOT_RADIUS, itemsToLoot);
	}
	
	public static int getNumLootInVicinity(String ...itemsToLoot) {
		return LocationUtils.getGroundItemsVisibleNearMe(LootingTask.LOOT_RADIUS*2, itemsToLoot).size();
	}
}
