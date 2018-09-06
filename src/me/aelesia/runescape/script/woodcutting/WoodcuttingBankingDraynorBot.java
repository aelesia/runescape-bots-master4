package me.aelesia.runescape.script.woodcutting;

import me.aelesia.runescape.consts.E;
import me.aelesia.runescape.consts.Zones;

public class WoodcuttingBankingDraynorBot extends WoodcuttingBankingBase {

	@Override
	protected void initialize() {
		config.treesToChop = new String[] { E.Object.OAK };
		config.zone = Zones.DRAYNOR_OAKS_2;
		config.bank = Zones.BANK_DRAYNOR;
		config.itemsToDeposit = new String[] { E.Item.OAK_LOGS };
	}
}
