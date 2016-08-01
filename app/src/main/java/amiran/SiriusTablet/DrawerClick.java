package amiran.SiriusTablet;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by ASUS on 07.04.2016.
 */
public class DrawerClick implements AdapterView.OnItemClickListener {

    Context mcontext;
    AppDetail[] mapps;

    public DrawerClick(Context context){
        mcontext = context;
        //mapps = apps;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        mainActivity ma = new mainActivity();
        if (ma.isLaunchable) {
            Intent launchintent = new Intent(Intent.ACTION_MAIN);

            //so it is a Launcher and not a background app
            launchintent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(mainActivity.apps[position].packageName.toString(), mainActivity.apps[position].name.toString());
            launchintent.setComponent(cp);
            mcontext.startActivity(launchintent);

        }


    }

}
