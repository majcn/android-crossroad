package si.majcn.krizisce.utils;

import android.text.format.Time;

public class VehiclePass {
	public static final int TURN_STRAIGHT = 10;
	public static final int TURN_LEFT = 11;
	public static final int TURN_RIGHT = 12;
	
	public static final int VEHICLE_CAR = 20;
	public static final int VEHICLE_BUS = 21;
	public static final int VEHICLE_TRUCK = 22;
	public static final int VEHICLE_BIGTRUCK = 23;
	
	private Time time;
	private String in;
	private String out;
	private int type;
	private int turn;
	
	public VehiclePass(int type, int turn, String inout) {
		String[] s = inout.split("->");
		init(type, turn, s[0], s[1]);
	}
	
	public VehiclePass(int type, int turn, String in, String out) {
		init(type, turn, in, out);
	}
	
	private void init(int type, int turn, String in, String out) {
		time = new Time();
		time.setToNow();
		this.type = type;
		this.turn = turn;
		this.in = in;
		this.out = out;
	}
	
	public String getIn() {
		return in;
	}
	
	public String getOut() {
		return out;
	}
	
	public String getDateTime() {
		return time.format("%d.%m.%Y %H:%M:%S");
	}
	
	private String getFormatedTimeRounded(String format) {
		if(time.minute < 15) {
			return time.format(format.replaceAll("%S", "00"));
		} else if (time.minute < 30) {
			return time.format(format.replaceAll("%S", "15"));
		} else if (time.minute < 45) {
			return time.format(format.replaceAll("%S", "30"));
		} else if (time.minute < 60) {
			return time.format(format.replaceAll("%S", "45"));
		} else {
			return "";
		}		
	}
	
	public String getDateTimeRounded() {
		return getFormatedTimeRounded("%d.%m.%Y %H:%S");
	}
	
	public String getTimeRounded() {
		return getFormatedTimeRounded("%H:%S");
	}
	
	public int getType() {
		return type;
	}
	
	public int getTurn() {
		return turn;
	}
}
