package de.newschool.homescreen;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ASUS on 14.11.2016.
 */
public class HomeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent homescreen = new Intent(context,MainActivity.class);
        homescreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(homescreen);
    }
}
