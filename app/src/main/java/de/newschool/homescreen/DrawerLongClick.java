package de.newschool.homescreen;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

class DrawerLongClick implements AdapterView.OnItemLongClickListener {
    private final Context mcontext;
    private final TextView delete_bar_tv; // bar allowing deletion of the icon of an added App on homescreen

    private LinearLayout home_layout;


    public DrawerLongClick(Context context, LinearLayout layout, TextView delete_tv) {
        mcontext = context;
        home_layout = layout;
        //list = apps;
        delete_bar_tv = delete_tv;
        delete_bar_tv.setOnDragListener(new DeleteBar(delete_bar_tv, home_layout));
        home_layout.setOnDragListener(new ShortcutListeners(mcontext, delete_bar_tv));
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        MainActivity.isLaunchable = false; //so the app wont lauch if you longclick it. Look at OnItemListener in MainActivity.class

        AppSerializableData objectdata = SerializationTools.loadSerializedData();
        if (objectdata == null)
            objectdata = new AppSerializableData();

      /*  if(objectdata.apps != null){
            for(int i = 0; i<objectdata.apps.size();i++){
                if(Objects.equals(objectdata.apps.get(i).packageName,list[position].packageName) &&
                   Objects.equals(objectdata.apps.get(i).name,list[position].name)){

                    Toast.makeText(mcontext,"eine Verknüpfung dieser App ist schon vorhanden",Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }*/

        AppDetail appToAdd = MainActivity.apps.get(position);
        appToAdd.x = (int) view.getX();
        appToAdd.y = (int) view.getY();

        if (objectdata.apps == null)
            objectdata.apps = new ArrayList<>();

        appToAdd.cacheIcon();
        //home_icon.setTag(appToAdd);

        //to get the viewpagerItem which is shown

        appToAdd.screen_num = MainActivity.multiscreen_pager.getCurrentItem();

        String date = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());

        //setting the exact time as id
        appToAdd.id = date;
        objectdata.apps.add(appToAdd);
        SerializationTools.serializeData(objectdata);

        appToAdd.addToHome(mcontext);

        MainActivity.multiscreen_pager.setVisibility(View.VISIBLE);
        MainActivity.viewPagerIndicator_layout.setVisibility(View.VISIBLE);
        MainActivity.slidingDrawer.close();

        //for draging the home_app
        //home_icon.setOnLongClickListener(new HomeApp_Listeners());
        //home_icon.setOnClickListener(this);

        return false;
    }
}
