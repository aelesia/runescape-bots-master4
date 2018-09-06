package me.aelesia.runescape.tasks.base;

import java.util.List;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;

import me.aelesia.runescape.actions.InventoryActions;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.exceptions.IllegalArgumentException;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class BuryTask extends BaseTask {
	
	protected String[] bonesToBury;
	private List<SpriteItem> bonesInInventory;
	int retryCount = 0;
	
	public BuryTask(String[] bonesToBury) {
		if (CommonUtils.isEmpty(bonesToBury)) {
			throw new IllegalArgumentException("");
		}
		this.bonesToBury = bonesToBury;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void initialize() {
		this.bonesInInventory = CommonUtils.sortInventoryByIndex(Inventory.getItems(bonesToBury).asList());
		Logger.init(this.bonesInInventory.size() + " bones to bury");
	}
	
	@Override
	public void execute() {
		if (!this.bonesInInventory.isEmpty()) {
			SpriteItem bone = this.bonesInInventory.get(0);
			if (InventoryActions.interact(E.Action.BURY, bone)) {
				this.bonesInInventory.remove(bone);
				retryCount = 0;
			} else {
				retryCount++;
				if (retryCount>=3) {
					this.bonesInInventory.remove(bone);
					retryCount = 0;
				}
			}
		}
	}
}
