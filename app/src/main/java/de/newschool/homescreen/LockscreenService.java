package de.newschool.homescreen;

import android.app.KeyguardManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

public class LockscreenService extends Service {
    private BroadcastReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("message", "IBinder");
        return null;
    }

    @Override
    public void onCreate() {

            Intent intent = getPackageManager().getLaunchIntentForPackage("de.newschool.homescreen");
            startActivity(intent);


    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }


}
