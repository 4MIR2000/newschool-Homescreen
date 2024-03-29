package de.newschool.homescreen;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

class TimetableLockscreenAdapter extends BaseAdapter {
    private final TimetableDayDetail thisDay_timetable;
    private final Context mcontext;

    public TimetableLockscreenAdapter(Context context, TimetableDayDetail timetable) {
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
        params = new RelativeLayout.LayoutParams(150, 150);


        iv.setLayoutParams(params);
        return iv;
    }
}
