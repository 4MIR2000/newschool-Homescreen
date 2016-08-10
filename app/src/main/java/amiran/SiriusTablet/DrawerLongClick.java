package amiran.SiriusTablet;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by amirt on 11.02.2016.
 */
public class DrawerLongClick implements AdapterView.OnItemLongClickListener {
    final static String LOG_TAG = DrawerLongClick.class.getName();
    static Context mcontext;
    SlidingDrawer SDrawer;
    LinearLayout home_layout;

    static View dragging_app;

    //is the bar which allows you to delete the icon of an added App on homescreen
    static TextView delete_bar_tv;


    View draggingIcon;

    public DrawerLongClick(Context context, LinearLayout layout, TextView delete_tv){

        mcontext = context;
        home_layout = layout;
        //list = apps;
        delete_bar_tv = delete_tv;
        delete_bar_tv.setOnDragListener(new Delete_bar(delete_bar_tv,home_layout));
        home_layout.setOnDragListener(new ShortcutListeners(mcontext,layout,delete_bar_tv));


    }

    public DrawerLongClick(){

    }





    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


        mainActivity.isLaunchable = false; //so the app wont lauch if you longclick it. Look at OnItemListener in mainActivity.class


        AppSerializableData objectdata = SerializationTools.loadSerializedData();
        if(objectdata == null)
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

        AppDetail appToAdd = mainActivity.apps[position];
        appToAdd.x = (int) view.getX();
        appToAdd.y = (int) view.getY();


        if(objectdata.apps == null)
            objectdata.apps = new ArrayList<AppDetail>();

        appToAdd.cacheIcon();
        //home_icon.setTag(appToAdd);

        //to get the viewpagerItem which is shown

        appToAdd.screen_num = mainActivity.multiscreen_pager.getCurrentItem();

        String date = new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(new Date());

        //setting the exact time as id
        appToAdd.id = date;
        objectdata.apps.add(appToAdd);
        SerializationTools.serializeData(objectdata);


        appToAdd.addToHome(mcontext);


        mainActivity.multiscreen_pager.setVisibility(View.VISIBLE);
        mainActivity.viewPagerIndicator_layout.setVisibility(View.VISIBLE);
        mainActivity.slidingDrawer.close();

        //for draging the home_app
        //home_icon.setOnLongClickListener(new HomeApp_Listeners());
        //home_icon.setOnClickListener(this);

        return false;
    }




}
