package de.phifo.simplesunrise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver  extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long mili = System.currentTimeMillis();

//            SunriseActivity activity = (SunriseActivity) context;

//            activity.setText("I was called!");

            Toast.makeText(context, "Don't panik but your time "+mili+" is up!!!!.",
                    Toast.LENGTH_LONG).show();

            Intent mainIntent = new Intent(context, SunriseActivity.class);
            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(mainIntent);
        }

    }
