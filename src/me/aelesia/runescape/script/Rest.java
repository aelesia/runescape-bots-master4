package me.aelesia.runescape.script;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import me.aelesia.runescape.exceptions.OutOfTimeException;
import me.aelesia.runescape.utils.game.Logger;
import me.aelesia.runescape.utils.general.RandomUtils;
import me.aelesia.runescape.utils.general.ThreadUtils;

public class Rest {
	
	public class State {
		public static final String NO_DELAY = "NO_DELAY"; 
		public static final String ACTIVE = "ACTIVE"; 
		public static final String DISTRACTED = "DISTRACTED";
		public static final String OCCUPIED = "OCCUPIED";
		public static final String AWAY = "AWAY";
	}
	
	public final int MIN_DURATION_MINS = 45;
	public final int MAX_DURATION_MINS = 60;
	
	public final int MIN_TIME_BLOCK = 1;
	public final int MAX_TIME_BLOCK = 3;
	
	public final int MIN_DISTRACTED = 20;
	public final int MAX_DISTRACTED = 30;
	public final int MIN_OCCUPIED = 10;
	public final int MAX_OCCUPIED = 15;
	public final int MIN_AWAY = 10;
	public final int MAX_AWAY = 15;
	
	int bottingDuration;
	int occupiedTime;
	int distractedTime;
	int awayTime;
	int activeTime;
	
	int remainderTime;
	int remainderActive;
	int remainderDistracted;
	int remainderOccupied;
	int remainderAway;
	
	long startTime = System.currentTimeMillis();
	List<String> stateList = new ArrayList<String>();
	List<Integer> timeList = new ArrayList<Integer>();
	
	boolean willAway = false;
	int awayFor;
	
	public Rest() {
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
				timeBlock = RandomUtils.randomInt(1*60, 4*60);
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

	private String generateRandomState() {
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
	
	public void rest() {
		rest(State.NO_DELAY);
	}
	
	public void rest(String maxRestState) {
		if (timeList.isEmpty()) {
			throw new OutOfTimeException("Bot finished running for " + bottingDuration/60 + " minutes");
		}
		while (timeList.size()>0 && currentTime() > timeList.get(0)) {

			timeList.remove(0);
			stateList.remove(0);
		}
		if (timeList.isEmpty()) {
			throw new OutOfTimeException("Bot finished running for " + bottingDuration/60 + " minutes");
		}
//		System.out.println("Current time: " + currentTime());
		String state = stateList.get(0);
		if (maxRestState == null) {
			return;
		} else if (State.AWAY.equals(state) && !willAway) {
			willAway = true;
			awayFor = (timeList.get(0) - currentTime()) * 1000;
			if (awayFor < 0) {
				awayFor = RandomUtils.randomInt(1*60, 4*60) * 1000;
			}
		}
		if (State.AWAY.equals(state) && willAway) {
			int sleepTime = RandomUtils.randomInt(100, 500);
			Logger.pause("Will away: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
		} else if (State.ACTIVE.equals(state) || State.ACTIVE.equals(maxRestState)) {			
//			ThreadUtils.sleepFor(0, 500);
			int sleepTime = RandomUtils.randomInt(100, 500);
			Logger.pause("Active: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
			
		} else if (State.DISTRACTED.equals(state) || State.DISTRACTED.equals(maxRestState)) {
//			ThreadUtils.sleepFor(0, 5000);
			int sleepTime = RandomUtils.randomInt(200, 7000);
			Logger.pause("Distracted for: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
			
		} else if (State.OCCUPIED.equals(state) || State.OCCUPIED.equals(maxRestState)) {
//			ThreadUtils.sleepFor(0, 30000);
			int sleepTime = RandomUtils.randomInt(1000, 20000);
			Logger.pause("Occupied for: " + sleepTime/1000.0);
			ThreadUtils.sleepFor(sleepTime);
		}
	}
	
	public void away() {
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
			Logger.pause("Away for: " + awayFor/1000.0);
			ThreadUtils.sleepFor(awayFor);
			willAway = false;
		}
//		}
	}
	
	private int currentTime() {
		return (int) (System.currentTimeMillis() - startTime)/1000;
	}
}
