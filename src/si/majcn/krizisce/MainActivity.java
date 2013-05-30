package si.majcn.krizisce;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import si.majcn.krizisce.entity.VehiclePass;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int DEFAULT_VEHICLE = 0; //CAR
	
	private Context mContext;
	private ImageButton[] mArrowButtons;
	private ImageButton[] mVehicleButtons;
	private Integer mSelectedVehicle;
	private LinkedList<VehiclePass> mPasses;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext = getApplicationContext();
		mPasses = new LinkedList<VehiclePass>();
		
		//INIT VEHICLE BUTTONS
		mVehicleButtons = new ImageButton[] {
			(ImageButton) findViewById(R.id.btnCar),
			(ImageButton) findViewById(R.id.btnBus),
			(ImageButton) findViewById(R.id.btnTruck),
			(ImageButton) findViewById(R.id.btnBigtruck)
		};
		OnClickListener onVehicleButtonClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				for(int i = 0; i < mVehicleButtons.length; i++) {
					if(v.equals(mVehicleButtons[i])) {
						mVehicleButtons[i].setSelected(true);
						mSelectedVehicle = i;
					} else {
						mVehicleButtons[i].setSelected(false);
					}
				}
			}
		};
		for(ImageButton btn : mVehicleButtons) {
			btn.setOnClickListener(onVehicleButtonClick);
		}
		mVehicleButtons[DEFAULT_VEHICLE].setSelected(true);
		mSelectedVehicle = DEFAULT_VEHICLE;
		
		//INIT ARROW BUTTONS
		mArrowButtons = new ImageButton[] {
			(ImageButton) findViewById(R.id.btnAB),
			(ImageButton) findViewById(R.id.btnAC),
			(ImageButton) findViewById(R.id.btnAD),
			(ImageButton) findViewById(R.id.btnBA),
			(ImageButton) findViewById(R.id.btnBC),
			(ImageButton) findViewById(R.id.btnBD),
			(ImageButton) findViewById(R.id.btnCA),
			(ImageButton) findViewById(R.id.btnCB),
			(ImageButton) findViewById(R.id.btnCD),
			(ImageButton) findViewById(R.id.btnDA),
			(ImageButton) findViewById(R.id.btnDB),
			(ImageButton) findViewById(R.id.btnDC)
		};
		OnClickListener onArrowButtonClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				mPasses.add(new VehiclePass(mVehicleButtons[mSelectedVehicle].getContentDescription().toString(), v.getContentDescription().toString()));
				Toast.makeText(mContext, v.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
			}
		};
		for(ImageButton btn : mArrowButtons) {
			btn.setOnClickListener(onArrowButtonClick);
		}
	}
	
	private void saveToCSV(String filename, String s) {
		File root = Environment.getExternalStorageDirectory();
		File gpxfile = new File(root, filename);
		FileWriter writer = null;
		try {
			writer = new FileWriter(gpxfile);
			writer.write(s);
			writer.flush();
			writer.close();
		} catch (IOException e) {
            e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String csvText = "";
	    switch (item.getItemId()) {
	    case R.id.action_save_log:
	    	csvText = "Ura,Tip vozila,Uvoz,Izvoz\n";
			for(VehiclePass vp : mPasses) {
				csvText += String.format("%s,%s,%s,%s\n", vp.getTimeRounded(), vp.getType(), vp.getIn(), vp.getOut());
			}
	    	saveToCSV("log.csv", csvText);
	    	Toast.makeText(this, "Shranjen Log", Toast.LENGTH_SHORT).show();
	    	return true;
	    case R.id.action_save_report:
	    	String filename = "A.csv";
	    	String prevTime = "";
	    	csvText = "";
	    	
	    	for(VehiclePass vp : mPasses) {
	    		
	    	}
	    	HashMap<String, Integer> tabela = null; 
	    	for(VehiclePass vp : mPasses) {
	    		String time = vp.getTimeRounded();
	    		if(!prevTime.equals(time)) {
	    			tabela = new HashMap<String, Integer>();
	    		}
	    		String key = vp.getInOut();
	    		if(!tabela.containsKey(key)) {
	    			tabela.put(key, 1);
	    		} else {
	    			tabela.put(key, tabela.get(key) + 1);
	    		}
	    	}
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

}
