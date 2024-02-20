package me.beatzoid.coordinatelist;

import eu.midnightdust.lib.config.MidnightConfig;

public class CListConfig extends MidnightConfig {
    @Entry(min=1,max=200) public static int multiplier = 10;
    @Entry(min=0) public static int render_distance = 0;
    @Entry public static boolean waypoints_toggled = true;
    @Entry public static boolean can_place_deathpoints = true;
}
