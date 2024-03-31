package one.pouekdev.coordinatelist;

import net.minecraft.text.Text;
import org.apache.commons.lang3.StringUtils;

public class CListWaypoint {
    public int x, y, z;
    public String name;
    public String dimension;
    public int bug_fix;
    public boolean render;
    public boolean deathpoint;
    CListWaypoint(String coords, String waypoint_name, String waypoint_dimension, boolean is_rendered, boolean is_deathpoint){
        String s = coords;
        s = s.replace("X", "");
        s = s.replace("Y", "");
        s = s.replace("Z", "");
        s = s.replace(" ", "");
        String[] segments = s.split(":");
        this.x = Integer.parseInt(segments[1]);
        this.y = Integer.parseInt(segments[2]);
        this.z = Integer.parseInt(segments[3]);
        this.name = waypoint_name;
        this.dimension = waypoint_dimension;
        this.render = is_rendered;
        this.deathpoint = is_deathpoint;
        this.bug_fix = 0;
    }
    CListWaypoint(int x, int y, int z, String waypoint_name, String waypoint_dimension, boolean is_rendered, boolean is_deathpoint){
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = waypoint_name;
        this.dimension = waypoint_dimension;
        this.render = is_rendered;
        this.deathpoint = is_deathpoint;
        this.bug_fix = 0;
    }
    public String getName(){
        return this.name;
    }
    public String getCoordinates(){
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
    public String getDimensionValue(){
        return this.dimension;
    }
    public void setName(String value){
        this.name = value;
    }
    public void toggleVisibility(){
        this.bug_fix += 1;
        if(bug_fix == 2){
            this.bug_fix = 0;
            this.render = !this.render;
            CListClient.variables.saved_since_last_update = false;
        }
    }
    public Text getDimensionText(){
        String s = this.dimension;
        s = s.replace("minecraft:","");
        s = s.replace("_"," ");
        s = StringUtils.capitalize(s);
        return Text.literal(s);
    }
    public String getDimensionString(){
        String s = this.dimension;
        s = s.replace("minecraft:","");
        s = s.replace("_"," ");
        s = StringUtils.capitalize(s);
        return s;
    }
}
