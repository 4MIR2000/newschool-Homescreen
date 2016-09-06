package de.newschool.homescreen;

import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


class DeleteBar implements View.OnDragListener {
    private final String LOG_TAG = DeleteBar.class.getName();
    private final TextView delete_bar_tv;
    private final LinearLayout layout_home;
    private final MainActivity ma;

    public DeleteBar(TextView delete_bar, LinearLayout home_layout) {
        delete_bar_tv = delete_bar;
        layout_home = home_layout;
        ma = new MainActivity();
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_ENTERED:
                delete_bar_tv.setTextColor(Color.RED);
                break;

            case DragEvent.ACTION_DROP:
                delete_bar_tv.setTextColor(Color.BLACK);
                layout_home.removeView(v);
                delete_bar_tv.setVisibility(View.INVISIBLE);

                AppDetail appDetail = (AppDetail) ShortcutListeners.getDraggingApp().getTag();
                AppSerializableData data = SerializationTools.loadSerializedData();


                if (appDetail != null) {
                    if (data != null) {
                        if (data.apps != null) {
                            for (int i = 0; i < data.apps.size(); i++) {



                                if (Objects.equals(data.apps.get(i).id, appDetail.id)) {


                                    //check if there is an other app with same icon
                                    //else delete icon

                                    boolean otherShortcutWithSameIcon_avaiable = false;
                                    for (int j = 0; j < data.apps.size(); j++) {
                                        if (Objects.equals(data.apps.get(j).packageName, appDetail.packageName) &&
                                                Objects.equals(data.apps.get(j).name, appDetail.name)) {


                                            //we does ignore the actual dragged app
                                            if(!Objects.equals(data.apps.get(j).id,appDetail.id)) {
                                                otherShortcutWithSameIcon_avaiable = true;
                                                Log.d(LOG_TAG, "avaible");
                                            }
                                        }
                                    }


                                    //if there is no other shortcut with the same icon
                                    if (!otherShortcutWithSameIcon_avaiable) {
                                        data.apps.get(i).deleteIcon();


                                    }

                                    data.apps.remove(i);
                                    SerializationTools.serializeData(data);
                                    break;
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
