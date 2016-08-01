package amiran.SiriusTablet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by amirt on 11.02.2016.
 */
public class DrawerClickListener implements AdapterView.OnItemClickListener {

    Context mcontext;
    AppDetail [] list;
    PackageManager Pmanager;

    public DrawerClickListener(Context context, AppDetail[] apps, PackageManager manager){
        mcontext = context;
        list = apps;
        manager = Pmanager;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent launchintent = Pmanager.getLaunchIntentForPackage(list[position].name.toString());
        mcontext.startActivity(launchintent);
    }
}