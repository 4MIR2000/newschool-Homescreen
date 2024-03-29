package de.newschool.homescreen;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Tools {
    public static Bitmap getdrawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable)
            return ((BitmapDrawable) drawable).getBitmap();

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static String getReligion(){
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool"+File.separator+"Benutzer");
            File religion_file = new File(root+File.separator+"r.txt");

            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader br = null;

            if(religion_file.exists()) {
                try {

                    fis = new FileInputStream(religion_file);
                    isr = new InputStreamReader(fis);
                    br = new BufferedReader(isr);

                    return br.readLine();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }

    public static List<ActivityManager.RunningServiceInfo> getRunningServices(){

        ActivityManager mActivityManager = (ActivityManager)MainActivity.getContext().getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningServiceInfo> appProcessInfoList = mActivityManager.getRunningServices(Integer.MAX_VALUE);

        return appProcessInfoList;
    }

    class GetWhiteList extends AsyncTask{


        @Override
        protected Object doInBackground(Object[] params) {

            return null;
        }
    }

}
