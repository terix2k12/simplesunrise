package de.phifo.simplesunrise;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

public class SunriseActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout linearLayout = new LinearLayout(this);

        final TextView textView = new TextView(this);
        textView.setText("Sleeping...");

        Button buttonView = new Button(this);
        buttonView.setOnClickListener(
                new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                textView.setText("Hello World.");
                                          }
                                      }
        );

        linearLayout.addView(textView);
        linearLayout.addView(buttonView);

        setContentView(linearLayout);


        // setContentView(R.layout.activity_sunrise);

        Context context = this;

        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, MyBroadcastReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        // Set the alarm to start at 8:30 a.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 42);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
 //       alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
 //               1000 * 60 * 20, alarmIntent);

        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);
    }
}
