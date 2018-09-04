package me.aelesia.runescape.script;

import java.awt.Color;
import java.util.Arrays;

import me.aelesia.runescape.utils.game.Zone;

public class Config {
	public Zone zone;
	public Zone bank;
	public String[] treesToChop;
	public String[] logsToBurn;
	public Color[] oreToMine;
	
	public String[] itemsToLoot;
	public String[] itemsToDeposit;
	public String[] itemsToCook;
	public String[] itemsToDispose;
	public String[] bonesToBury;
	
	public String[] monstersToFight;
	public String[] fishingSpotAction;
	
	public int minLoot;
	public int maxLoot;

	public String toString() {
		String str = "______________________________\n";
		str+= "zone: " + zone.name + "\n";
		if (bank!=null) {
		str+= "bank: " + bank.name + "\n";
		}
		str+= "monstersToFight: " + Arrays.toString(monstersToFight) + "\n";
		str+= "treesToChop: " + Arrays.toString(treesToChop) + "\n";
		str+= "itemsToDeposit: " + Arrays.toString(itemsToDeposit) + "\n";
		str+= "itemsToDispose: " + Arrays.toString(itemsToDispose) + "\n";
		str+= "itemsToCook: " + Arrays.toString(itemsToCook) + "\n";
		str+= "itemsToLoot: " + Arrays.toString(itemsToLoot) + "\n";
		str+= "logsToBurn: " + Arrays.toString(logsToBurn) + "\n";
		str+= "oreToMine: " + Arrays.toString(oreToMine) + "\n";
		str+= "fishingSpotAction: " + Arrays.toString(fishingSpotAction) + "\n";
		str+= "______________________________";
		return str;
	}
}
