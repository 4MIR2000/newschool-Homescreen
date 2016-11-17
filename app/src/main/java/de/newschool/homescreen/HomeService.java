package de.newschool.homescreen;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by ASUS on 14.11.2016.
 */
public class HomeService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {



        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification note = new Notification( 0, null, System.currentTimeMillis() );
        note.flags |= Notification.FLAG_NO_CLEAR;

      //  startForeground(42,note);


         return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent("de.newschool.homescreen.homereceiver");
        sendBroadcast(intent);

    }
}
