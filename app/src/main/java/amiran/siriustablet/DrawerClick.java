package amiran.siriustablet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

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
        if (ma.isLaunchable) {
            Intent launchintent = new Intent(Intent.ACTION_MAIN);

            //so it is a Launcher and not a background app
            launchintent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(MainActivity.apps[position].packageName, MainActivity.apps[position].name);
            launchintent.setComponent(cp);
            mcontext.startActivity(launchintent);
        }
    }
}
