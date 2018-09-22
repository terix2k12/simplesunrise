package de.phifo.simplesunrise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mainIntent = new Intent(context, SunriseActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mainIntent.putExtra(SunriseActivity.EXTRA_STARTMODE, SunriseActivity.STARTMODE_ALERT);

        Toast.makeText(context, "Alarm triggered", Toast.LENGTH_LONG).show();

        context.startActivity(mainIntent);
    }

}