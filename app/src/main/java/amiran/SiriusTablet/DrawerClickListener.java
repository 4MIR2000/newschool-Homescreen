package amiran.SiriusTablet;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

class DrawerClickListener implements AdapterView.OnItemClickListener {
    private final Context mcontext;
    private final AppDetail[] list;
    private PackageManager Pmanager;

    public DrawerClickListener(Context context, AppDetail[] apps, PackageManager manager) {
        mcontext = context;
        list = apps;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent launchintent = Pmanager.getLaunchIntentForPackage(list[position].name);
        mcontext.startActivity(launchintent);
    }
}
