package si.majcn.krizisce.utils;

import android.text.format.Time;

import java.util.HashMap;

public class CrossroadInfo {
    private Time time;
    private String name;
    private String locationName;
    private HashMap<String, String> roads;

    public CrossroadInfo(String name, String locationName, HashMap<String, String> roads) {
        time = new Time();
        time.setToNow();

        this.name = name;
        this.locationName = locationName;
        this.roads = roads;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return time.format("%d.%m.%Y");
    }

    public String getLocationName() {
        return locationName;
    }

    public String getRoadName(String name) {
        if (roads.containsKey(name)) {
            return roads.get(name);
        } else {
            return "Unnamed road";
        }
    }
}
