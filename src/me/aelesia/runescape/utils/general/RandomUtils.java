package me.aelesia.runescape.utils.general;

import java.util.Random;

public class RandomUtils {

	static Random rand = new Random(); 
	
	public static int randomInt(int num1, int num2) {
		return rand.nextInt(num2-num1+1) + num1;
	}
	
	public static int randomInt(double num1, double num2) {
		num1 = num1*100;
		num2 = num2*100;
		return (rand.nextInt((int)num2-(int)num1+1) + (int)num1) / 100;
	}
}
