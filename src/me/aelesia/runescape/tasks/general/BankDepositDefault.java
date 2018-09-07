package me.aelesia.runescape.tasks.general;

import me.aelesia.runescape.tasks.base.BankDepositTask;
import me.aelesia.runescape.utils.game.InventoryUtils;
import me.aelesia.runescape.utils.game.Logger;

public abstract class BankDepositDefault extends BankDepositTask {

	public BankDepositDefault(String[] itemsToDeposit) {
		super(itemsToDeposit);
	}

	@Override
	public String changeState() {
		if (!InventoryUtils.contains(this.itemsToDeposit)) {
			Logger.state("No more items to deposit");
			return this.stateIfDepositedAll();
		}
		return null;
	}

	public abstract String stateIfDepositedAll();
}