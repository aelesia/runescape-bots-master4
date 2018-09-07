package me.aelesia.runescape.consts;

import java.util.ArrayList;
import java.util.List;

import com.runemate.game.api.hybrid.location.Area;
import com.runemate.game.api.hybrid.location.Coordinate;

import me.aelesia.runescape.utils.game.Zone;

public class Zones {
	
	public static Zone LUMBRIDGE_GOBLIN_HUT = new Zone("LUMBRIDGE_GOBLIN_HUT", new Area.Rectangular(new Coordinate(3239, 3258, 0), new Coordinate(3254, 3231, 0)));
	public static Zone LUMBRIDGE_COW_FARM = new Zone("LUMBRIDGE_COW_FARM", new Area.Rectangular(new Coordinate(3153, 3345, 0), new Coordinate(3201, 3314, 0)));
//	public static Zone LUMBRIDGE_CHICKEN_FARM = new Zone("LUMBRIDGE_CHICKEN_FARM", new Area.Rectangular(new Coordinate(3153, 3345, 0), new Coordinate(3201, 3314, 0)));
	public static Zone DRAYNOR_OAKS = new Zone("DRAYNOR_OAKS", new Area.Rectangular(new Coordinate(3071, 3305, 0), new Coordinate(3087, 3290, 0)));
	public static Zone DRAYNOR_OAKS_2 = new Zone("DRAYNOR_OAKS_2", new Area.Rectangular(new Coordinate(3096, 3289, 0), new Coordinate(3108, 3282, 0)));
	public static Zone DRAYNOR_OAKS_BANK = new Zone("DRAYNOR_OAKS_BANK", new Area.Rectangular(new Coordinate(3101, 3245, 0), new Coordinate(3103, 3241, 0)));
//	public static Zone DRAYNOR_OAKS_2_EMPTY = new Zone("DRAYNOR_OAKS_2_EMPTY", new Area.Rectangular(new Coordinate(3099, 3284, 0), new Coordinate(3104, 3282, 0)));

	public static Zone BANK_DRAYNOR = new Zone("BANK_DRAYNOR", new Area.Rectangular(new Coordinate(3092, 3245, 0), new Coordinate(3095, 3241, 0)));
	public static Zone BANK_VARROCK_EAST = new Zone("BANK_VARROCK_EAST", new Area.Rectangular(new Coordinate(3251, 3422, 0), new Coordinate(3254, 3420, 0)));
	public static Zone BANK_VARROCK_WEST = new Zone("BANK_VARROCK_WEST", new Area.Rectangular(new Coordinate(3185, 3435, 0), new Coordinate(3181, 3445, 0)));

	public static Zone MINE_VARROCK_EAST = new Zone("MINE_VARROCK_EAST", new Area.Rectangular(new Coordinate(3285, 3370, 0), new Coordinate(3288, 3368, 0)));
	
	public static List<Zone> ALL = new ArrayList<Zone>() {{
		add(LUMBRIDGE_GOBLIN_HUT);
		add(LUMBRIDGE_COW_FARM);
//		add(LUMBRIDGE_CHICKEN_FARM);
		add(DRAYNOR_OAKS);
		add(DRAYNOR_OAKS_2);
//		add(DRAYNOR_OAKS_2_EMPTY);
		
		add(BANK_DRAYNOR);
	}};
}
