package de.newschool.homescreen;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class LockscreenReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Intent lockscreen = context.getPackageManager().getLaunchIntentForPackage("de.newschool.lockscreen");

            if(lockscreen != null) {
                lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(lockscreen);
            }

        } else if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent lockscreen = context.getPackageManager().getLaunchIntentForPackage("de.newschool.lockscreen");
            lockscreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(lockscreen);
        }

    }
}
