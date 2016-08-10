package amiran.SiriusTablet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import amiran.siriustablet_test.R;

public class AppDetail implements Serializable {
    final String LOG_TAG = AppDetail.class.getName();
    String id;
    String label;
    String name;
    String packageName;
    transient Drawable icon;
    int x, y;
    String iconLocation;

    //which screen is the shortcut added
    int screen_num;

    public void cacheIcon() {
        if (iconLocation == null) {
            //mainactivity.activity.getApplicationInfo().dataDir+"/cachedApps/"+packageName+name;

            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "NewSchool" + File.separator +
                    "cachedApps" + File.separator + packageName + name);
            try {
                file.createNewFile();
                Log.d(LOG_TAG, "file angelegt");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (icon != null) {
            iconLocation = Environment.getExternalStorageDirectory() + File.separator + "NewSchool" + File.separator +
                    "cachedApps" + File.separator + packageName + name;

            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(iconLocation);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            if (fos != null) {
                Tools.getdrawableToBitmap(icon).compress(Bitmap.CompressFormat.PNG, 100, fos);
                Log.d(LOG_TAG, "icon saved");
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                iconLocation = null;
            }
        }
    }

    public Bitmap getCachedIcon() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inDither = true;

        if (iconLocation != null) {
            File cachedIcon = new File(iconLocation);
            return BitmapFactory.decodeFile(cachedIcon.getAbsolutePath());
        }

        return null;
    }

    public void deleteIcon() {
        if (iconLocation != null) {
            new File(iconLocation).delete();
        }
    }

    public void addToHome(Context context) {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                , ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.rightMargin = -params.leftMargin;
        params.topMargin = y;
        params.bottomMargin = -params.topMargin;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View home_icon = inflater.inflate(R.layout.list_item, null);

        if (icon == null) {
            icon = new BitmapDrawable(context.getResources(), getCachedIcon());
        }

        ImageView icon_imageView = (ImageView) home_icon.findViewById(R.id.item_app_icon);
        icon_imageView.setImageDrawable(icon);

        ((TextView) home_icon.findViewById(R.id.item_app_label)).setText(label);


        RelativeLayout parent = mainActivity.mpagerAdapter.getLayout(screen_num);
        parent.addView(home_icon, 0, params);

        home_icon.setOnLongClickListener(new ShortcutListeners());
        home_icon.setOnClickListener(new ShortcutListeners());

        //icon_imageView.setOnTouchListener(new ShortcutListeners());

        icon_imageView.setTag(this);
        home_icon.setTag(this);
    }
}
