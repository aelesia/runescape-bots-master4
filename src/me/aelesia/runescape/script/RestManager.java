package me.aelesia.runescape.script;

import java.util.HashMap;
import java.util.Map;

public class RestManager {
	public static Map<String, Rest> restClients = new HashMap<String, Rest>();
	
	public static void createNew(String name) {
		restClients.put("name", new Rest());
	}
	
	public static Rest get(String name) {
		return restClients.get("name");
	}
}
