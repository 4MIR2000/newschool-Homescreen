package amiran.SiriusTablet;

import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import amiran.siriustablet_test.R;

public class ShortcutListeners implements View.OnClickListener, View.OnLongClickListener, View.OnDragListener, View.OnTouchListener {
    static final String LOG_TAG = ShortcutListeners.class.getName();
    static View dragging_app;
    static TextView delete_bar_tv;
    static Context mcontext;
    static LinearLayout mplacement_layout;

    public ShortcutListeners(Context context, LinearLayout placement_layout, TextView delete_tv) {
        mcontext = context;
        delete_bar_tv = delete_tv;
        mplacement_layout = placement_layout;
    }

    public ShortcutListeners() {
    }

    @Override
    public void onClick(View v) {
        AppDetail dataofView = (AppDetail) v.getTag();
        try {
            Intent launchintent = new Intent(Intent.ACTION_MAIN);
            //it is a Launcher and not a background app
            launchintent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cp = new ComponentName(dataofView.packageName, dataofView.name);
            launchintent.setComponent(cp);
            mcontext.startActivity(launchintent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(mcontext,
                    mcontext.getResources().getString(R.string.activityNotFound), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onLongClick(View v) {
        ClipData.Item item = new ClipData.Item("drag_item");
        String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
        ClipData clipData = new ClipData("drag_clipdata", mimeTypes, item);
        View.DragShadowBuilder shadow = new View.DragShadowBuilder(v);
        v.startDrag(clipData, shadow, null, 0);
        v.setVisibility(View.INVISIBLE);
        dragging_app = v;

        //making the delete_bar visible if icon is longpressed
        delete_bar_tv.setVisibility(View.VISIBLE);

        return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
   /*     Log.d(LOG_TAG, String.valueOf(event.getX()));

        Display display = ((WindowManager)mcontext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int d_width = display.getWidth();
        Double placement_layout_width = d_width*0.7;

        if(event.getX()<placement_layout_width) {


            if (event.getX()+22 > mplacement_layout.getWidth()) {

                mainActivity.multiscreen_pager.setCurrentItem(mainActivity.multiscreen_pager.getCurrentItem()+1);

            }


        }*/
        switch (event.getAction()) {
            case DragEvent.ACTION_DROP:
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dragging_app.getWidth(), dragging_app.getHeight());
                params.leftMargin = (int) event.getX() - (dragging_app.getWidth() / 2);
                params.rightMargin = -params.leftMargin;
                params.topMargin = (int) event.getY() - (dragging_app.getHeight() / 2);
                params.bottomMargin = -params.topMargin;

                //dragging_view is the View which is longpressed. Have a look
                //at LongClickListener
                dragging_app.setLayoutParams(params);

                //making the icon visible
                dragging_app.setVisibility(View.VISIBLE);

                //making the delete bar invisible when the icon is droped
                delete_bar_tv.setVisibility(View.INVISIBLE);

                AppDetail appDetail = (AppDetail) dragging_app.getTag();
                AppSerializableData data = SerializationTools.loadSerializedData();
                if (appDetail != null) {
                    if (data != null) {
                        if (data.apps != null) {
                            for (int i = 0; i < data.apps.size(); i++) {
                                if (Objects.equals(data.apps.get(i).id, appDetail.id)) {
                                    data.apps.get(i).x = (int) event.getX() - dragging_app.getWidth() / 2;
                                    data.apps.get(i).y = (int) event.getY() - dragging_app.getHeight() / 2;

                                    data.apps.get(i).screen_num = mainActivity.multiscreen_pager.getCurrentItem();
                                    SerializationTools.serializeData(data);
                                }
                            }
                        }
                    }
                }
                //  SerializationTools.serializeData(serializableData);
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                boolean droped = event.getResult();
                if (droped == false) {
                    //invisible deletebar if the icon is droped anywhere else as our app_widget_placement
                    delete_bar_tv.setVisibility(View.INVISIBLE);
                    dragging_app.setVisibility(View.VISIBLE);

                    Toast.makeText(mcontext, "Hier kann die Verknüpfung nicht hinzugefügt werden", Toast.LENGTH_SHORT).show();
               /*     AppDetail appDetail2 = (AppDetail) ShortcutListeners.getDraggingApp().getTag();
                    AppSerializableData data2= SerializationTools.loadSerializedData();

                    if(appDetail2 != null) {
                        if (data2 != null) {
                            if (data2.apps != null) {
                                for(int i = 0; i<data2.apps.size(); i++){

                                    if(Objects.equals(data2.apps.get(i).packageName, appDetail2.packageName)){
                                        Log.d(LOG_TAG,"deleted");
                                        data2.apps.get(i).deleteIcon();
                                        data2.apps.remove(i);
                                        SerializationTools.serializeData(data2);



                                    }
                                }

                            }
                        }
                    }*/
                }
        }

        return true;
    }


    public static View getDraggingApp() {
        return dragging_app;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                v.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                v.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                v.getBackground().clearColorFilter();
                v.invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
                v.getBackground().clearColorFilter();
                v.invalidate();
                break;
        }
        return false;
    }
}
