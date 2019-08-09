package LavaCrafter.utils;

import org.powerbot.script.Area;
import org.powerbot.script.Tile;

public class Areas {

    public Areas(){}

    public static final Area GE_AREA = new Area(new Tile(3168, 3488), new Tile(3171, 3490));
    public static final Area TRADE_AREA = new Area(new Tile(3208, 3218, 2), new Tile(3209, 3216, 2));
    public static final Area CASTLE_WARS_AREA = new Area(new Tile(2442,3085,0), new Tile(2441, 3091, 0));
    public static final Area DUEL_ARENA_AREA = new Area(new Tile(3314, 3235, 0), new Tile(3320,3328,0));
    public static final Area BANK_AREA = new Area(new Tile(3382,3268,0), new Tile(3387,3266,0));
    public static final Area OUTSIDE_RUINS = new Area(new Tile(3311,3253,0), new Tile(3306,3246,0));
    public static final Area INSIDE_ALTAR = new Area(new Tile(2576,4848,0), new Tile(2579,4844,0));
    public static final Area AT_FIRE_ALTAR = new Area(new Tile(2584,4840,0), new Tile(2576,4832,0));
}
