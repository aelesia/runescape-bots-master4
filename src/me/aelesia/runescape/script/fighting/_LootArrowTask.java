package me.aelesia.runescape.script.fighting;

import com.runemate.game.api.hybrid.entities.GroundItem;

import me.aelesia.runescape.actions.GameActions;
import me.aelesia.runescape.tasks.base.BaseTask;
import me.aelesia.runescape.utils.game.LocationUtils;
import me.aelesia.runescape.utils.game.PlayerUtils;

public abstract class _LootArrowTask extends BaseTask {
	
	public static final int LOOT_RADIUS = 4; 
	
	protected String[] itemsToLoot;
	protected int itemsPickedUp;
	
	public _LootArrowTask(String ...itemsToLoot) {
		this.itemsToLoot = itemsToLoot;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void initialize() {
		itemsPickedUp = 0;
	}

	@Override
	public void execute() {
		if (PlayerUtils.isIdle()) {
			GroundItem item = _LootArrowTask.getAdjacentLoot(this.itemsToLoot);

			if (item!=null && item.isValid()) {
				if (GameActions.loot(item)) {
					itemsPickedUp++;
				}
			}
		}
	}
	
	public static GroundItem getAdjacentLoot(String ...itemsToLoot) {
		return LocationUtils.getGroundItemVisibleNearest(_LootArrowTask.LOOT_RADIUS, itemsToLoot);
	}
	
	public static int getNumLootInVicinity(String ...itemsToLoot) {
		return LocationUtils.getGroundItemsVisibleNearMe(_LootArrowTask.LOOT_RADIUS*2, itemsToLoot).size();
	}
}
