package si.majcn.krizisce;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {

	private Context mContext;
	private ImageButton mBtnChooser;
	private ImageButton mBtnSettings;
	private ImageButton mBtnAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// full screen
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getActionBar().hide();

		mContext = getApplicationContext();
		mBtnChooser = (ImageButton) findViewById(R.id.btnChooser);
		mBtnSettings = (ImageButton) findViewById(R.id.btnSettings);
		mBtnAbout = (ImageButton) findViewById(R.id.btnAbout);

		mBtnChooser.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext,
						CrossroadChooserActivity.class);
				startActivity(intent);
			}
		});

		mBtnSettings.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, SettingsActivity.class);
				startActivity(intent);
			}
		});

		mBtnAbout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(mContext, AboutActivity.class);
				startActivity(intent);
			}
		});

	}

}
