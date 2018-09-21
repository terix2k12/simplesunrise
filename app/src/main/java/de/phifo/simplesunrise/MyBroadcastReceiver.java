package de.phifo.simplesunrise;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.widget.Toast;

public class MyBroadcastReceiver  extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            long mili = System.currentTimeMillis();

            Toast.makeText(context, "Don't panik but your time "+mili+" is up!!!!.",
                    Toast.LENGTH_LONG).show();

            // Vibrate the mobile phone
             Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
             vibrator.vibrate(2000);
        }

    }
