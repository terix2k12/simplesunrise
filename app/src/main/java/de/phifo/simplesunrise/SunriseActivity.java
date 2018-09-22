package de.phifo.simplesunrise;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SunriseActivity extends AppCompatActivity {

    public static final String EXTRA_STARTMODE = "de.phifo.simplesunrise.EXTRA_STARTMODE";
    public static final String STARTMODE_ALERT = "ring";

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private Handler myHandler;

    private TextView textView;

    private Button buttonLight;
    private boolean toggle = false;

    public void setText(String text) {
        textView.setText(text);
    }

    public void lockScreen(boolean lock) {
        if (lock) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void startAlert() {
        int i = 15;

//        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (i * 1000), alarmIntent);

        long ww2 = System.currentTimeMillis() + (2 * 60 * 1000);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 6);
        calendar.set(Calendar.SECOND, 0);
//        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                          AlarmManager.INTERVAL_DAY, alarmIntent);

        long millis = calendar.getTimeInMillis();

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        long diff = millis - System.currentTimeMillis();

        setText(ww2 + " --" + millis + " Alarm in Minutes from now: " + (diff / 1000 / 60) + "  ");

        alarmMgr.set(AlarmManager.RTC_WAKEUP, millis, alarmIntent);

        Toast.makeText(this, "Alarm set in " + i + "  " +
                format1.format(calendar.getTime()), Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout linearLayout = new LinearLayout(this);

        textView = new TextView(this);

        if (getIntent().getExtras() != null) {
            Object startmode = getIntent().getExtras().get(EXTRA_STARTMODE);
            if (startmode == null) {
                setText("Sleeping...");
            } else {
                setText("Ringing...");
                lockScreen(true);
            }
        } else {
            setText("second?!...");
        }

        myHandler = new Handler();

        Button buttonView = new Button(this);
        buttonView.setText("Set Alert");
        buttonView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textView.setText("Hello World.");

                        startAlert();

                        // lockScreen(false);
                    }
                }
        );

        buttonLight = new Button(this);
        buttonLight.setText("Turn off Light");
        buttonLight.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeBright(linearLayout);
                    }
                }
        );


        linearLayout.addView(textView);
        linearLayout.addView(buttonView);
        linearLayout.addView(buttonLight);

        setContentView(linearLayout);


        alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyBroadcastReceiver.class);

        alarmIntent = PendingIntent.getBroadcast(this,
                0, intent, 0);


// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        //       alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //               1000 * 60 * 20, alarmIntent);

        //
    }

    private void makeBright(final LinearLayout li) {

        WindowManager.LayoutParams params = getWindow().getAttributes();
        final int f;

        if (toggle) {
            params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_FULL;
            toggle = false;
            f = 0;
            buttonLight.setText("Set Light OFF");
        } else {
            params.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_OFF;
            toggle = true;
            f = 255;
            buttonLight.setText("Set Light ON");
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= 255; i++) {
                    final int c = i;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    myHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int d = Math.abs(f - c);
                            int col = Color.rgb(d, d, d);
                            li.setBackgroundColor(col);
                        }
                    });
                }
            }
        }).start();

        getWindow().setAttributes(params);
    }
}
