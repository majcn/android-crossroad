package si.majcn.krizisce.entity;

import android.text.format.Time;

public class VehiclePass {
	private Time time;
	private String type;
	private String in;
	private String out;
	
	public VehiclePass(String type, String inout) {
		String[] s = inout.split("->");
		init(type, s[0], s[1]);
	}
	
	public VehiclePass(String type, String in, String out) {
		init(type, in, out);
	}
	
	private void init(String type, String in, String out) {
		time = new Time();
		time.setToNow();
		this.type = type;
		this.in = in;
		this.out = out;		
	}
	
	public String getIn() {
		return in;
	}
	
	public String getOut() {
		return out;
	}
	
	public String getInOut() {
		return in + "->" + out;
	}
	
	public String getTime() {
		return time.format("%d.%m.%Y %H:%M:%S");
	}
	
	public String getTimeRounded() {
		if(time.minute < 15) {
			return time.format("%d.%m.%Y %H:00");
		} else if (time.minute < 30) {
			return time.format("%d.%m.%Y %H:15");
		} else if (time.minute < 45) {
			return time.format("%d.%m.%Y %H:30");
		} else if (time.minute < 60) {
			return time.format("%d.%m.%Y %H:45");
		} else {
			return "";
		}
	}
	
	public String getType() {
		return type;
	}
}
