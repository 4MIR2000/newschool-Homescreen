package de.newschool.homescreen;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

class DrawerClick implements AdapterView.OnItemClickListener {
    private final Context mcontext;
    AppDetail[] mapps;

    public DrawerClick(Context context) {
        mcontext = context;
        //mapps = apps;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity ma = new MainActivity();
        if (MainActivity.isLaunchable) {
            Intent launchintent = new Intent(Intent.ACTION_MAIN);

            launchintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //so it is a Launcher and not a background app
            launchintent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(MainActivity.apps.get(position).packageName, MainActivity.apps.get(position).name);
            launchintent.setComponent(cp);


            mcontext.startActivity(launchintent);
        }
    }
}
