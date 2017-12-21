package com.davey.davescogsworth;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;

/**
 * Updated by Davey on 11/29/2017.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We're in the reciever.", "Cool.");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);


        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);

        //start ring service
        context.startService(service_intent);
    }
}
