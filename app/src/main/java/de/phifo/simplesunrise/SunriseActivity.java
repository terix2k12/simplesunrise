package de.phifo.simplesunrise;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SunriseActivity extends AppCompatActivity implements ITimeConsumer {

    public static final String EXTRA_STARTMODE = "de.phifo.simplesunrise.EXTRA_STARTMODE";
    public static final String STARTMODE_ALERT = "ring";

    private TextView textView;

    public void setText(String text) {
        textView.setText(text);
    }

    private void startAlert(long millis) {
        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(this,
                0, intent, 0);

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        //       alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
        //               1000 * 60 * 20, alarmIntent);
//        alarmMgr.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
//                + (i * 1000), alarmIntent);
//        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//                          AlarmManager.INTERVAL_DAY, alarmIntent);


//        long diff = millis - System.currentTimeMillis();
//        setText(" Alarm in Minutes from now: " + (diff / 1000 / 60) + "  ");

        alarmMgr.set(AlarmManager.RTC_WAKEUP, millis, alarmIntent);

        // from api lvl 21
//        AlarmManager.AlarmClockInfo info = new AlarmManager.AlarmClockInfo(millis, null);
//        alarmMgr.setAlarmClock(info, alarmIntent);


        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        Toast.makeText(this, "Alarm set to: " +
                format1.format(calendar.getTime()), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final LinearLayout linearLayout = new LinearLayout(this);

        textView = new TextView(this);

        Button buttonView = new Button(this);
        buttonView.setText("Set Alert");
        buttonView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DialogFragment newFragment = new TimePickerFragment(SunriseActivity.this);
                        newFragment.show(SunriseActivity.this.getFragmentManager(), "timePicker");
                    }
                }
        );

        linearLayout.addView(textView);
        linearLayout.addView(buttonView);

        setContentView(linearLayout);
    }

    public void onTimeSet(int hourOfDay, int minutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        if(calendar.get(Calendar.HOUR_OF_DAY) < hourOfDay)
        {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        startAlert(calendar.getTimeInMillis());
    }

}