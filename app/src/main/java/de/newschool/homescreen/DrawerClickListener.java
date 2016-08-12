package de.newschool.homescreen;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.AdapterView;

import de.newschool.homescreen.AppDetail;

class DrawerClickListener implements AdapterView.OnItemClickListener {
    private final Context mcontext;
    private final AppDetail[] list;
    private PackageManager Pmanager;

    public DrawerClickListener(Context context, AppDetail[] apps) {
        mcontext = context;
        list = apps;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent launchintent = Pmanager.getLaunchIntentForPackage(list[position].name);
        mcontext.startActivity(launchintent);
    }
}
