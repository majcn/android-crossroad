package si.majcn.krizisce;

import android.os.Bundle;
import android.app.Activity;

public class CrossroadChooserActivity extends Activity {
	
	public static final String EXTRA_MESSAGE = "";
	public static final int FOUR_WAY = 4;
	public static final int THREE_WAY = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_crossroad_chooser);
	}

}
