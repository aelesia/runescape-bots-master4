package me.aelesia.runescape.utils.game;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.runemate.game.api.hybrid.region.Players;

public class Logger {
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public static void state(String msg) {
		println("[STATE] " + msg);
	}
	
	public static void stateChange(String msg) {
		println("***** " + msg + " *****");
	}
	
	public static void success(String msg) {
		println("[SUCCESS] " + msg);
	}
	
	public static void action(String msg) {
		println("[ACTION] " + msg);
	}
	
	public static void config(String msg) {
		println("[CONFIG] " + msg);
	}
	
	public static void init(String msg) {
		println("[INIT] " + msg);
	}
	
	public static void info(String msg) {
		println("[INFO] " + msg);
	}
	
	public static void pause(String msg) {
		println("[PAUSE] " + msg);
	}
	
	public static void println(String msg) {
		System.out.println("["+dtf.format(LocalDateTime.now())+"]["+Players.getLocal().getName()+"]"+msg);
	}
}
