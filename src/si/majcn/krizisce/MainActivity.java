package si.majcn.krizisce;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import si.majcn.krizisce.log.D;
import si.majcn.krizisce.utils.DblIntCounter;
import si.majcn.krizisce.utils.VehiclePass;

import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
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
	private ArrayList<VehiclePass> mPasses;

	private Vibrator vib;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		
		mContext = getApplicationContext();
		mPasses = new ArrayList<VehiclePass>();
		
		//INIT VEHICLE BUTTONS
		mVehicleButtons = new ImageButton[] {
			(ImageButton) findViewById(R.id.btnCar),
			(ImageButton) findViewById(R.id.btnBus),
			(ImageButton) findViewById(R.id.btnTruck),
			(ImageButton) findViewById(R.id.btnBigtruck)
		};
		mVehicleButtons[0].setTag(VehiclePass.VEHICLE_CAR);
		mVehicleButtons[1].setTag(VehiclePass.VEHICLE_BUS);
		mVehicleButtons[2].setTag(VehiclePass.VEHICLE_TRUCK);
		mVehicleButtons[3].setTag(VehiclePass.VEHICLE_BIGTRUCK);
		OnClickListener onVehicleButtonClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				vibrate();
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
		mArrowButtons[0].setTag(VehiclePass.TURN_LEFT);
		mArrowButtons[1].setTag(VehiclePass.TURN_STRAIGHT);
		mArrowButtons[2].setTag(VehiclePass.TURN_RIGHT);
		mArrowButtons[3].setTag(VehiclePass.TURN_RIGHT);
		mArrowButtons[4].setTag(VehiclePass.TURN_LEFT);
		mArrowButtons[5].setTag(VehiclePass.TURN_STRAIGHT);
		mArrowButtons[6].setTag(VehiclePass.TURN_STRAIGHT);
		mArrowButtons[7].setTag(VehiclePass.TURN_RIGHT);
		mArrowButtons[8].setTag(VehiclePass.TURN_LEFT);
		mArrowButtons[9].setTag(VehiclePass.TURN_LEFT);
		mArrowButtons[10].setTag(VehiclePass.TURN_STRAIGHT);
		mArrowButtons[11].setTag(VehiclePass.TURN_RIGHT);
		OnClickListener onArrowButtonClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				vibrate();
				mPasses.add(new VehiclePass((Integer) mVehicleButtons[mSelectedVehicle].getTag(), (Integer) v.getTag(), v.getContentDescription().toString()));
				//Toast.makeText(mContext, v.getContentDescription().toString(), Toast.LENGTH_SHORT).show();
			}
		};
		for(ImageButton btn : mArrowButtons) {
			btn.setOnClickListener(onArrowButtonClick);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void vibrate() {
		vib.vibrate(100);
	}
	
	private boolean saveXLSReport() {
		if(mPasses.size() == 0) {
			return false;
		}
		File storagePath = new File(Environment.getExternalStorageDirectory(), "krizisce");
		storagePath.mkdirs();
		File gpxfile = new File(storagePath, String.format("%s.xls", mPasses.get(0).getDateTimeFilename()));
		SortedSet<String> listIn = new TreeSet<String>();
		for(VehiclePass vp : mPasses) {
			listIn.add(vp.getIn());
		}
		DblIntCounter counter = new DblIntCounter();
		
        int[] sortedVehicles = {
        		VehiclePass.VEHICLE_CAR,
        		VehiclePass.VEHICLE_BUS,
        		VehiclePass.VEHICLE_TRUCK,
        		VehiclePass.VEHICLE_BIGTRUCK
        };
        
        int[] sortedTurns = {
        		VehiclePass.TURN_LEFT,
        		VehiclePass.TURN_STRAIGHT,
        		VehiclePass.TURN_RIGHT
        };
		
	    try {
			WritableWorkbook workbook = Workbook.createWorkbook(gpxfile);
			
			WritableFont cellHeaderFont = new WritableFont(WritableFont.TIMES, 24);
		    WritableCellFormat cellHeaderFormat = new WritableCellFormat(cellHeaderFont);
		    WritableCellFormat cellFormat = new WritableCellFormat();
			cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
			
			WritableSheet sheet = null;
			
			int sheetCounter = 0;
			int row = 0;
			for(String cIn : listIn) {
			    sheet = workbook.createSheet(String.format("KRAK %s", cIn), sheetCounter++);
				
		        sheet.mergeCells(0, 0, 12, 0);
		        sheet.addCell(new Label(0, 0, String.format("Štetje prometa: %s", mPasses.get(0).getDateTime()), cellHeaderFormat));
		        sheet.setRowView(0, 30*20);
		        
		        for(int i=0; i<3; i++) {
		        	sheet.addCell(new Label(i*4+1, 3, "osebni", cellFormat));
		        	sheet.addCell(new Label(i*4+2, 3, "bus", cellFormat));
		        	sheet.addCell(new Label(i*4+3, 3, "tov", cellFormat));
		        	sheet.addCell(new Label(i*4+4, 3, "vlac", cellFormat));
		        }
		        
		        sheet.mergeCells(1, 2, 4, 2);
		        sheet.addCell(new Label(1, 2, "levo", cellFormat));
		        sheet.mergeCells(5, 2, 8, 2);
		        sheet.addCell(new Label(5, 2, "naravnost", cellFormat));
		        sheet.mergeCells(9, 2, 12, 2);
		        sheet.addCell(new Label(9, 2, "desno", cellFormat));

	        	sheet.addCell(new Label(1, 3, "osebni", cellFormat));
	        	sheet.addCell(new Label(2, 3, "bus", cellFormat));
	        	sheet.addCell(new Label(3, 3, "tov", cellFormat));
	        	sheet.addCell(new Label(4, 3, "vlac", cellFormat));
	        	sheet.addCell(new Label(5, 3, "osebni", cellFormat));
	        	sheet.addCell(new Label(6, 3, "bus", cellFormat));
	        	sheet.addCell(new Label(7, 3, "tov", cellFormat));
	        	sheet.addCell(new Label(8, 3, "vlac", cellFormat));
	        	sheet.addCell(new Label(9, 3, "osebni", cellFormat));
	        	sheet.addCell(new Label(10, 3, "bus", cellFormat));
	        	sheet.addCell(new Label(11, 3, "tov", cellFormat));
	        	sheet.addCell(new Label(12, 3, "vlac", cellFormat));
	        	
	        	counter.resetCounter();
		        String prevTime = mPasses.get(0).getTimeRounded();
		        String time = null;
		        row = 4;
		        Iterator<VehiclePass> it = mPasses.iterator();
		        while(it.hasNext()) {
		        	VehiclePass vp = it.next();
		        	time = vp.getTimeRounded();
		        	if(time.equals(prevTime)) {
		        		if(vp.getIn().equals(cIn)) {
		        			counter.incCounter(vp.getType(), vp.getTurn());		        			
		        		}
		        	}
		        	if(!it.hasNext() || !time.equals(prevTime)) {
		        		prevTime = time;
		        		sheet.addCell(new Label(0, row, time, cellFormat));
		        		for(int i=0; i<sortedTurns.length; i++) {
		        			for(int j=0; j<sortedVehicles.length; j++) {
		        				sheet.addCell(new Label(i*sortedVehicles.length+j+1, row, Integer.toString(counter.getCounter(sortedVehicles[j], sortedTurns[i])), cellFormat));
		        			}
		        		}
		        		row++;
		        		counter.resetCounter();
		        	}
		        }
			}
			
			sheet = workbook.createSheet(String.format("LOG"), sheetCounter++);
        	sheet.addCell(new Label(0, 0, "Ura", cellFormat));
        	sheet.addCell(new Label(1, 0, "Tip vozila", cellFormat));
        	sheet.addCell(new Label(2, 0, "Uvoz", cellFormat));
        	sheet.addCell(new Label(3, 0, "Izvoz", cellFormat));
        	row = 1;
        	for(VehiclePass vp : mPasses) {
        		sheet.addCell(new Label(0, row, vp.getDateTime(), cellFormat));
        		sheet.addCell(new Label(1, row, vp.getTextType(), cellFormat));
        		sheet.addCell(new Label(2, row, vp.getIn(), cellFormat));
        		sheet.addCell(new Label(3, row, vp.getOut(), cellFormat));
        		row++;
        	}
			
	        workbook.write();
	        workbook.close();
		} catch (Exception e) {
			D.dbge(e.getMessage());
			return false;
		}
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case R.id.action_reset:
	    	mPasses = new ArrayList<VehiclePass>();
    		Toast.makeText(mContext, "Resetirano", Toast.LENGTH_SHORT).show();
	    	return true;
	    case R.id.action_save_report:
	    	if(saveXLSReport()) {
	    		Toast.makeText(mContext, "Shranjeno porocilo", Toast.LENGTH_SHORT).show();
	    	} else {
	    		Toast.makeText(mContext, "NI Shranjeno porocilo", Toast.LENGTH_SHORT).show();
	    	}
	    	return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}

}
