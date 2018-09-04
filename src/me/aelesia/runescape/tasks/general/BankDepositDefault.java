package me.aelesia.runescape.tasks.general;

import me.aelesia.runescape.tasks.base.BankDepositTask;
import me.aelesia.runescape.utils.game.InventoryUtils;

public abstract class BankDepositDefault extends BankDepositTask {

	public BankDepositDefault(String[] itemsToDeposit) {
		super(itemsToDeposit);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String changeState() {
		if (!InventoryUtils.contains(this.itemsToDeposit)) {
			System.out.println("[STATE] No more items to deposit");
			return this.stateIfDepositedAll();
		}
		return null;
	}

	public abstract String stateIfDepositedAll();
}