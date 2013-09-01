package si.majcn.krizisce;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class CrossroadChooserActivity extends Activity {

    public static final String EXTRA_MESSAGE = "COUNT";
    public static final int FOUR_WAY = 4;
    public static final int THREE_WAY = 3;

    private Context mContext;
    private ImageButton mBtnStart;
    private ImageButton mBtnFourway;
    private ImageButton mBtnThreeway;
    private int mSelectedCrossroad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crossroad_chooser);

        // full screen
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        // WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActionBar().hide();

        mContext = getApplicationContext();
        mBtnStart = (ImageButton) findViewById(R.id.btnStart);
        mBtnFourway = (ImageButton) findViewById(R.id.btnFourway);
        mBtnThreeway = (ImageButton) findViewById(R.id.btnThreeway);

        mSelectedCrossroad = FOUR_WAY;
        mBtnFourway.setSelected(true);

        mBtnThreeway.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnThreeway.setSelected(true);
                mBtnFourway.setSelected(false);
                mSelectedCrossroad = THREE_WAY;
            }
        });

        mBtnFourway.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnThreeway.setSelected(false);
                mBtnFourway.setSelected(true);
                mSelectedCrossroad = FOUR_WAY;
            }
        });

        mBtnStart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CrossroadActivity.class);
                intent.putExtra(EXTRA_MESSAGE, mSelectedCrossroad);
                startActivity(intent);
            }
        });

    }

}
