package me.aelesia.runescape.utils.general;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.runemate.game.api.hybrid.local.hud.interfaces.SpriteItem;

public class CommonUtils {

	public static boolean isBlank(String string) {
		if (string==null || string.isEmpty() || string.replace(" ", "").length() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(List<?> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isEmpty(String[] array) {
		if (array == null || array.length == 0) {
			return true;
		}
		return false;
	}
	
	public static List<SpriteItem> sortInventoryByIndex(List<SpriteItem> list) {
		List<SpriteItem> sortedList = new ArrayList<SpriteItem>(list);
		
		sortedList.sort(new Comparator<SpriteItem>() {
			@Override
			public int compare(SpriteItem o1, SpriteItem o2) {
				if (o1.getIndex() < o2.getIndex()) {
					return -1;
				} else {
					return 1;
				}
			}
		});
		return sortedList;
	}
	
	public static String[] mergeArrays(String[] ...arrays) {
		List<String> stringList = new ArrayList<String>();
		for (int i=0; i<arrays.length; i++) {
			stringList.addAll(Arrays.asList(arrays[i]));
		}
		return stringList.toArray(new String[stringList.size()]);
	}
	
	public static String[] mergeStringArray(String string, String[] ...arrays) {
		List<String> stringList = new ArrayList<String>();
		stringList.add(string);
		for (int i=0; i<arrays.length; i++) {
			stringList.addAll(Arrays.asList(arrays[i]));
		}
		return stringList.toArray(new String[stringList.size()]);
	}
}
