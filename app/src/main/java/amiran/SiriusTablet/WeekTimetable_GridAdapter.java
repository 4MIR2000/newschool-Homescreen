package amiran.SiriusTablet;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import amiran.siriustablet_test.R;

class WeekTimetable_GridAdapter extends BaseAdapter {
    public static final String LOG_TAG = WeekTimetable_GridAdapter.class.getName();
    private final Context mcontext;
    private final Timetable_WeekDetail mtimetable;
    private final int mlongestDayHoursNum;
    int mposition;

    public WeekTimetable_GridAdapter(Context context, Timetable_WeekDetail timetable, int longestDayHoursNum) {
        mcontext = context;
        mtimetable = timetable;
        mlongestDayHoursNum = longestDayHoursNum;
    }

    @Override
    public int getCount() {
        return mtimetable.days.size() * mlongestDayHoursNum;
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
            convertLayout = inflater.inflate(R.layout.timetable_week_item, null);

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

        int day_position = position;
        for (int i = 0; day_position >= mtimetable.days.size(); i++) {
            day_position = day_position - mtimetable.days.size();
        }

        // Toast.makeText(mcontext,Integer.toString(day_position),Toast.LENGTH_SHORT).show();
        int hourposition = position / mtimetable.days.size();

        if (hourposition < mtimetable.days.get(day_position).hours.size()) {
            holder.build(mtimetable.days.get(day_position).hours.get(hourposition).subject,
                    mtimetable.days.get(day_position).hours.get(hourposition).room);

        } else {
            holder.build("", "");
        }

        //day_position++;

        return convertLayout;
    }

    public class Holder {
        final TextView subject_tv;
        final TextView room_tv;

        public Holder(View convertLayout) {
            subject_tv = (TextView) convertLayout.findViewById(R.id.subject_timetable);
            room_tv = (TextView) convertLayout.findViewById(R.id.standardroom_timetable);
        }

        public void build(String subject, String room) {
            subject_tv.setText(subject);
            room_tv.setText(room);
        }
    }
}


