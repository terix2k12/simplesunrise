package de.phifo.simplesunrise;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import static de.phifo.simplesunrise.SunriseActivity.EXTRA_STARTMODE;

public class SunriseWakeupActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setBackgroundColor(Color.BLACK);

        if (getIntent().getExtras() != null) {
            Object startmode = getIntent().getExtras().get(EXTRA_STARTMODE);
            if (startmode != null) {
                setScreenLocked(true);
                makeBright(linearLayout);
            }
        }

        Button buttonEndAlert = new Button(this);
        buttonEndAlert.setText("Dismiss Alert");
        buttonEndAlert.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setScreenBrightness(false);
                        setScreenLocked(false);
                    }
                }
        );

        linearLayout.addView(buttonEndAlert);

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

    private void makeBright(final LinearLayout li) {
        setScreenBrightness(true);

        final Handler myHandler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 255; i++) {
                    final int c = i;
                    try {

                        // Delay from black to white for 10 Minutes

                        long delay = (5*60*1000) / 255;

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
            }
        }).start();
    }

}