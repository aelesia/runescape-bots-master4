package me.aelesia.runescape.tasks.base;

import java.util.List;

import com.runemate.game.api.hybrid.local.hud.interfaces.Inventory;
import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;

import me.aelesia.runescape.actions.InventoryActions;
import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.script.RestManager.State;
import me.aelesia.runescape.utils.general.CommonUtils;

public abstract class BuryTask extends BaseTask {
	
	protected String[] bonesToBury;
	private List<SpriteItem> bonesInInventory;
	
	public BuryTask(String[] bonesToBury) {
		this.bonesToBury = bonesToBury;
	}
	
	@Override
	public void validate() {}
	
	@Override
	public void initialize() {
		this.bonesInInventory = CommonUtils.sortInventoryByIndex(Inventory.getItems(bonesToBury).asList());
		System.out.println("[INIT] " + this.bonesInInventory.size() + " bones to bury");
	}
	
	@Override
	public void execute() {
		if (!this.bonesInInventory.isEmpty()) {
			SpriteItem bone = this.bonesInInventory.get(0);
			InventoryActions.interact(E.Action.BURY, bone);
			if (!bone.isValid()) {
				this.bonesInInventory.remove(bone);
			}
		}
	}
}
