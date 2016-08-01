package amiran.SiriusTablet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ASUS on 13.03.2016.
 */
public class LockscreenReceiver extends BroadcastReceiver {
    public static boolean wasScreenOn = true;




    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {

            wasScreenOn = false;
            Intent intent1 = new Intent(context, Lockscreen.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent1);

        } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {

            wasScreenOn = true;
            Intent intent11 = new Intent(context, Lockscreen.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }else if(intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
        {

            Intent intent11 = new Intent(context, Lockscreen.class);
            intent11.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        }
    }

}
