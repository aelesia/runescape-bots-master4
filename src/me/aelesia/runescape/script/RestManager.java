package me.aelesia.runescape.script;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import me.aelesia.runescape.exceptions.OutOfTimeException;
import me.aelesia.runescape.utils.general.RandomUtils;
import me.aelesia.runescape.utils.general.ThreadUtils;

public class RestManager {
	
	public class State {
		public static final String NO_DELAY = "NO_DELAY"; 
		public static final String ACTIVE = "ACTIVE"; 
		public static final String DISTRACTED = "DISTRACTED";
		public static final String OCCUPIED = "OCCUPIED";
		public static final String AWAY = "AWAY";
	}
	
	public static final int MIN_DURATION_MINS = 45;
	public static final int MAX_DURATION_MINS = 70;
	
	public static final int MIN_TIME_BLOCK = 1;
	public static final int MAX_TIME_BLOCK = 3;
	
	public static final int MIN_DISTRACTED = 25;
	public static final int MAX_DISTRACTED = 35;
	public static final int MIN_OCCUPIED = 15;
	public static final int MAX_OCCUPIED = 25;
	public static final int MIN_AWAY = 8;
	public static final int MAX_AWAY = 12;
	
	static int bottingDuration;
	static int occupiedTime;
	static int distractedTime;
	static int awayTime;
	static int activeTime;
	
	static int remainderTime;
	static int remainderActive;
	static int remainderDistracted;
	static int remainderOccupied;
	static int remainderAway;
	
	static long startTime = System.currentTimeMillis();
	static List<String> stateList = new ArrayList<String>();
	static List<Integer> timeList = new ArrayList<Integer>();
	
	static boolean willAway = false;
	static int awayFor;
	
	public static void initialize() {
		bottingDuration = RandomUtils.randomInt(MIN_DURATION_MINS*60, MAX_DURATION_MINS*60);

		occupiedTime = (int) (RandomUtils.randomInt(MIN_OCCUPIED, MAX_OCCUPIED) / 100.0 * bottingDuration);
		distractedTime = (int) (RandomUtils.randomInt(MIN_DISTRACTED, MAX_DISTRACTED) / 100.0 * bottingDuration);
		awayTime = (int) (RandomUtils.randomInt(MIN_AWAY, MAX_AWAY) / 100.0 * bottingDuration);
		activeTime = bottingDuration - distractedTime - occupiedTime - awayTime;
		
		System.out.println(bottingDuration/60 + " minutes");
		System.out.println(bottingDuration + " seconds");
		System.out.println("distractedTime: " + distractedTime);
		System.out.println("occupiedTime: " + occupiedTime);
		System.out.println("activeTime: " + activeTime);
		System.out.println("awayTime: " + awayTime);
		
		remainderTime = bottingDuration;
		remainderActive = activeTime;
		remainderDistracted = distractedTime;
		remainderOccupied = occupiedTime;
		remainderAway = awayTime;
		
		
		int totalTime = 0;
		
		while (remainderTime > 0) {
			int timeBlock = RandomUtils.randomInt(MIN_TIME_BLOCK*60, MAX_TIME_BLOCK*60);
			String state = generateRandomState();
			remainderTime -= timeBlock;
			if (State.DISTRACTED.equals(state)) {
				remainderDistracted -= timeBlock;
			} else if (State.OCCUPIED.equals(state)) {
				remainderOccupied -= timeBlock;
			} else if (State.AWAY.equals(state)) {
				timeBlock = RandomUtils.randomInt(3*60, 5*60);
				remainderAway -= timeBlock;
			} else {
				remainderActive -= timeBlock;
			}
			stateList.add(state);
			totalTime += timeBlock;
			timeList.add(totalTime);
		}
		LocalDateTime startTime = LocalDateTime.now();
		for (int i=0; i<stateList.size(); i++) {
			System.out.print(stateList.get(i));
			System.out.println(" " + startTime.plusSeconds((long)timeList.get(i)));
		}
	}

	private static String generateRandomState() {
		int random = RandomUtils.randomInt(0, remainderTime);
		if (random <= remainderActive) {
			return State.ACTIVE;
		} else if (random > remainderActive && random <= remainderActive+remainderDistracted) {
			return State.DISTRACTED;
		} else if (random > remainderActive+remainderDistracted && random <= remainderActive+remainderDistracted+remainderOccupied){
			return State.OCCUPIED;
		} else {
			return State.AWAY;
		}
	}
	
	public static void rest() {
		rest(State.NO_DELAY);
	}
	
	public static void rest(String maxRestState) {
		if (timeList.isEmpty()) {
			throw new OutOfTimeException("Bot finished running for " + bottingDuration/60 + " minutes");
		}
		else if (currentTime() > timeList.get(0)) {
			timeList.remove(0);
			stateList.remove(0);
			if (!stateList.isEmpty()) {
				System.out.println("[ALERT STATE] " + stateList.get(0));
			}
		}
//		System.out.println("Current time: " + currentTime());
		String state = stateList.get(0);
		if (maxRestState == null) {
			return;
		} else if (State.AWAY.equals(state) && !willAway) {
			willAway = true;
			awayFor = (timeList.get(0) - currentTime()) * 1000;
		}
		if (State.AWAY.equals(state) && willAway) {
			int sleepTime = RandomUtils.randomInt(100, 500);
			System.out.println("[WILL AWAY] Pause: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
		} else if (State.ACTIVE.equals(state) || State.ACTIVE.equals(maxRestState)) {			
//			ThreadUtils.sleepFor(0, 500);
			int sleepTime = RandomUtils.randomInt(100, 500);
			System.out.println("[ACTIVE] Pause: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
			
		} else if (State.DISTRACTED.equals(state) || State.DISTRACTED.equals(maxRestState)) {
//			ThreadUtils.sleepFor(0, 5000);
			int sleepTime = RandomUtils.randomInt(200, 7000);
			System.out.println("[DISTRACTED] Pause: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
			
		} else if (State.OCCUPIED.equals(state) || State.OCCUPIED.equals(maxRestState)) {
//			ThreadUtils.sleepFor(0, 30000);
			int sleepTime = RandomUtils.randomInt(1000, 20000);
			System.out.println("[OCCUPIED] Pause: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
		}
	}
	
	public static void away() {
//		if (timeList.isEmpty()) {
//			throw new OutOfTimeException("Bot finished running for " + bottingDuration/60 + " minutes");
//		}
//		else if (currentTime() > timeList.get(0)) {
//			timeList.remove(0);
//			stateList.remove(0);
//			if (!stateList.isEmpty()) {
//				System.out.println("[ALERT STATE] " + stateList.get(0));
//			}
//		}
//		String state = stateList.get(0);
		if (willAway) {
			System.out.println("[AWAY] Pause: " + awayFor/1000.0);
			ThreadUtils.sleepFor(awayFor);
			willAway = false;
		}
//		}
	}
	
	public static int currentTime() {
		return (int) (System.currentTimeMillis() - startTime)/1000;
	}
}
