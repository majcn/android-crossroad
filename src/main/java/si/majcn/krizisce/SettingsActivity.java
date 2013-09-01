package si.majcn.krizisce;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class SettingsActivity extends Activity {

    public static final String SETTINGS = "SETTINGS";
    public static final String SET_VIBRATE = "SET_VIBRATE";

    private ImageButton mBtnVibEnabler;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // full screen
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActionBar().hide();

        mBtnVibEnabler = (ImageButton) findViewById(R.id.btnVibEnabler);
        mSharedPreferences = getSharedPreferences(SETTINGS, MODE_PRIVATE);

        mBtnVibEnabler.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(!v.isSelected());
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean(SET_VIBRATE, v.isSelected());
                editor.commit();
            }
        });

        mBtnVibEnabler.setSelected(mSharedPreferences.getBoolean(SET_VIBRATE,
                true));
    }

}
