package amiran.SiriusTablet;

import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Objects;


/**
 * Created by amirt on 17.02.2016.
 */
public class Delete_bar implements View.OnDragListener {
    final String LOG_TAG = Delete_bar.class.getName();
    TextView delete_bar_tv;
    LinearLayout layout_home;
    mainActivity ma;

    public Delete_bar(TextView delete_bar, LinearLayout home_layout){
        delete_bar_tv = delete_bar;
        layout_home = home_layout;
        ma = new mainActivity();
    }
    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_ENTERED:
                delete_bar_tv.setTextColor(Color.RED);
                break;

            case DragEvent.ACTION_DROP:
                delete_bar_tv.setTextColor(Color.BLACK);
                layout_home.removeView(v);
                delete_bar_tv.setVisibility(View.INVISIBLE);

                AppDetail appDetail = (AppDetail) ShortcutListeners.getDraggingApp().getTag();
                AppSerializableData data= SerializationTools.loadSerializedData();

                if(appDetail != null) {
                    if (data != null) {
                        if (data.apps != null) {
                            for(int i = 0; i<data.apps.size(); i++){
                                Log.d(LOG_TAG,data.apps.get(i).packageName);
                                Log.d(LOG_TAG,appDetail.packageName);
                                if(Objects.equals(data.apps.get(i).id, appDetail.id)){
                                    Log.d(LOG_TAG,"deleted");

                                    boolean otherShortcutWithSameIcon_avaiable = false;
                                    for(int j = 0; j<data.apps.size(); j++){
                                        if(Objects.equals(data.apps.get(j).packageName,appDetail.packageName)&&
                                                Objects.equals(data.apps.get(j).name,appDetail.name)){

                                            otherShortcutWithSameIcon_avaiable = true;
                                            Log.d(LOG_TAG,"avaible");
                                        }
                                    }

                                    //if there is no other shortcut with the same icon
                                    if(otherShortcutWithSameIcon_avaiable == false) {
                                        data.apps.get(i).deleteIcon();
                                    }

                                    data.apps.remove(i);
                                    SerializationTools.serializeData(data);



                                }
                            }

                        }
                    }
                }

                break;

            case DragEvent.ACTION_DRAG_EXITED:

                delete_bar_tv.setTextColor(Color.BLACK);

        }

        return true;
    }
}
