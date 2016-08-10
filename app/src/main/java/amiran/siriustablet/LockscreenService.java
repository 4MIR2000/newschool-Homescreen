package amiran.siriustablet;

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
        KeyguardManager.KeyguardLock k1;

        KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        k1 = km.newKeyguardLock("IN");
        k1.disableKeyguard();

        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new LockscreenReceiver();
        registerReceiver(mReceiver, filter);

        Log.d("message", "service started");
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
