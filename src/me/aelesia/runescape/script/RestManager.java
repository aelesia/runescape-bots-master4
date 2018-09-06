package me.aelesia.runescape.script;

import java.util.HashMap;
import java.util.Map;

public class RestManager {
	public static Map<String, Rest> restClients = new HashMap<String, Rest>();
	
	public static void createNew(String name, int duration) {
		restClients.put("name", new Rest(duration));
	}
	
	public static Rest get(String name) {
		return restClients.get("name");
	}
}
