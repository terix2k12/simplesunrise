package de.phifo.simplesunrise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mainIntent = new Intent(context, SunriseWakeupActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainIntent.putExtra(SunriseActivity.EXTRA_STARTMODE, SunriseActivity.STARTMODE_ALERT);

        context.startActivity(mainIntent);
    }

}