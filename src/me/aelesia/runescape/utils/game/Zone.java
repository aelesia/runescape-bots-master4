package me.aelesia.runescape.utils.game;

import com.runemate.game.api.hybrid.location.Area;

public class Zone {
	public String name;
	public Area area;
	public String[] monsters;
	
	public Zone(String name, Area area) {
		this.name = name;
		this.area = area;
	}
	
//	@Override
//	public boolean equals(Object o) {
//		if (o instanceof Zone && this.name!=null) {
//			Zone z = (Zone)o;
//			if (this.name.equals(z.name)) {
//				return true;
//			}
//		}
//		return false;
//	}
}
