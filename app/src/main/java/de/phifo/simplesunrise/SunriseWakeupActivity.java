package de.phifo.simplesunrise;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static de.phifo.simplesunrise.SunriseActivity.EXTRA_STARTMODE;

public class SunriseWakeupActivity extends Activity {

    private TextView textTime;
    private Handler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myHandler = new Handler();

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setBackgroundColor(Color.BLACK);
        linearLayout.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT)
        );

        if (getIntent().getExtras() != null) {
            Object startmode = getIntent().getExtras().get(EXTRA_STARTMODE);
            if (startmode != null) {
                setScreenLocked(true);
                makeBright(linearLayout);
            }
        }

        Button buttonDismiss = new Button(this);
        buttonDismiss.setText("Dismiss");
        buttonDismiss.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setScreenBrightness(false);
                        setScreenLocked(false);
                    }
                }
        );

        Button buttonSnooze = new Button(this);
        buttonSnooze.setText("Snooze");
        buttonSnooze.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //setScreenBrightness(false);
                        //setScreenLocked(false);
                    }
                }
        );
        buttonSnooze.setActivated(false);

        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        ll.weight = 0.6f;

        textTime = new TextView(this);
        textTime.setLayoutParams(ll);
//        textTime.setTextAppearance(this, android.R.style.TextAppearance_Material_Large);
        textTime.setTextColor(Color.RED);
        textTime.setTextSize(TypedValue.COMPLEX_UNIT_PT, 50);

        linearLayout.addView(textTime);

        final LinearLayout buttonLayout = new LinearLayout(this);
        buttonLayout.setOrientation(LinearLayout.HORIZONTAL);
        buttonLayout.addView(buttonDismiss);
        buttonLayout.addView(buttonSnooze);

        linearLayout.addView(buttonLayout);

        setContentView(linearLayout);
    }

    private void setScreenLocked(boolean lock) {
        if (lock) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
    }

    private void setScreenBrightness(boolean bright) {
        WindowManager.LayoutParams params = getWindow().getAttributes();
        if (bright) {
            params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
        } else {
            params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
        }
        getWindow().setAttributes(params);
    }

    private void updateTime() {
        final SimpleDateFormat format1 = new SimpleDateFormat("HH:mm");
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                textTime.setText(format1.format(calendar.getTime()));
            }
        });
    }

    private void makeBright(final LinearLayout li) {
        setScreenBrightness(true);

        new Thread(new Runnable() {
            @Override
            public void run() {


                for (int i = 0; i <= 255; i++) {
                    final int c = i;
                    try {

                        // Delay from black to white for 10 Minutes

                        long delay = (5 * 60 * 1000) / 255;

                        updateTime();

                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int col = Color.rgb(c, c, c);
                            li.setBackgroundColor(col);
                        }
                    });
                }

                    // keep on updating the clock afterwards
                while (true) {
                    try {
                        updateTime();

                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

}