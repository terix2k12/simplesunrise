package de.phifo.simplesunrise;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SunriseActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private TextView textView;

    public void setText(String text)
    {
        textView.setText(text);
    }

    public void lockScreen(boolean lock)
    {
        if(lock)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }else{
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    private void startAler(){
        int i = 15;

        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                + (i * 1000), alarmIntent);

        Toast.makeText(this, "Alarm set in " + i + " seconds",
                Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);

        textView = new TextView(this);

        setText("Sleeping...");

        Button buttonView = new Button(this);
        buttonView.setOnClickListener(
                new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                            textView.setText("Hello World.");

                                startAler();

                            // lockScreen(false);
                                          }
                                      }
        );



        linearLayout.addView(textView);
        linearLayout.addView(buttonView);

        setContentView(linearLayout);


        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(this, MyBroadcastReceiver.class);

        alarmIntent = PendingIntent.getBroadcast(this,
                0, intent, 0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 15);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
 //       alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
 //               1000 * 60 * 20, alarmIntent);

      //  alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
      //          AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}
