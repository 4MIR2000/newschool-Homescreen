package de.newschool.homescreen;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import amiran.siriustablet.test.R;

class WeekTimetableDaysGridAdapter extends BaseAdapter {
    private final Context mcontext;
    private final TimetableWeekDetail mtimetable;

    public WeekTimetableDaysGridAdapter(Context context, TimetableWeekDetail timetable) {
        mtimetable = timetable;
        mcontext = context;
    }

    @Override
    public int getCount() {
        return mtimetable.days.size();
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
        View convertLayout = convertView;
        Holder holder;
        if (convertLayout == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertLayout = inflater.inflate(R.layout.timetable_week_day, null);

            Display d = ((WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            int display_width = d.getWidth();

            double gridView_leftMargin = mcontext.getResources().getDimension(R.dimen.weekTimetable_horizontal_margin)
                    / mcontext.getResources().getDisplayMetrics().density;

            double gridView_width = display_width - gridView_leftMargin;

            int cellWidth = (int) ((gridView_width - gridView_leftMargin * mtimetable.days.size()) / mtimetable.days.size());
            int cellHeight = (int) (mcontext.getResources().getDimension(R.dimen.WeekTimetable_days_height)
                    / mcontext.getResources().getDisplayMetrics().density);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(cellWidth, cellHeight);
            convertLayout.setLayoutParams(params);

            holder = new Holder(convertLayout);
            convertLayout.setTag(holder);

        } else {
            holder = (Holder) convertLayout.getTag();
        }

        holder.build(mtimetable.days.get(position).day_name);
        return convertLayout;
    }

    private class Holder {
        final TextView day_tv;

        public Holder(View convertLayout) {
            day_tv = (TextView) convertLayout.findViewById(R.id.day_timetable);
        }

        public void build(String day) {
            day_tv.setText(day);
        }
    }
}
