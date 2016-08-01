package amiran.SiriusTablet;

import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.List;


/**
 * Created by ASUS on 03.04.2016.
 */
public class Timetable_lockscreen_adapter extends BaseAdapter {
    Timetable_DayDetail thisDay_timetable;
    Context mcontext;

    public Timetable_lockscreen_adapter(Context context, Timetable_DayDetail timetable){
        thisDay_timetable = timetable;
        mcontext = context;
    }
    @Override
    public int getCount() {
        return thisDay_timetable.hours.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv = new ImageView(mcontext);

        iv.setImageResource(thisDay_timetable.hours.get(position).pic);

        RelativeLayout.LayoutParams params;
        params = new RelativeLayout.LayoutParams(150,150);
       

        iv.setLayoutParams(params);
        return iv;
    }
}
