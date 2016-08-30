package de.newschool.homescreen;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;
import de.newschool.homescreen.R;

class OneDayTimetableListAdapter extends BaseAdapter {
    private final Context mcontext;
    private final List<TimetableHourDetail> mhours;
    private final List<TimetableHourDetail> msubstitution;

    public OneDayTimetableListAdapter(Context context, List<TimetableHourDetail> hours, List<TimetableHourDetail> substitution) {
        mcontext = context;
        mhours = hours;
        msubstitution = substitution;
    }

    @Override
    public int getCount() {
        return mhours.size();
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
        Holder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.timetable_one_day_list_item, null);

            holder = new Holder(convertView);
            convertView.setTag(holder);

        } else {
            holder = (Holder) convertView.getTag();
        }

        holder.build(position + 1, mhours.get(position).subject, mhours.get(position).room);

        if (msubstitution != null) {
            for (TimetableHourDetail subhour : msubstitution) {
                if (subhour.hour - 1 == position) {
                    holder.substitution(subhour.subject, subhour.teacher, subhour.room);
                }
            }
        }

        return convertView;
    }

    private class Holder {
        final TextView subject_tv;
        final TextView standardroom_tv;
        final Button hour_button;
        final RelativeLayout layout;
        final View convertView;

        public Holder(View parent) {
            convertView = parent;
            subject_tv = (TextView) convertView.findViewById(R.id.subject_timetable);
            standardroom_tv = (TextView) convertView.findViewById(R.id.standardroom_timetable);
            hour_button = (Button) convertView.findViewById(R.id.hour_timetable);
            layout = (RelativeLayout) convertView.findViewById(R.id.timetable_item_layout_color);
        }

        public void build(int hour, String subject, String room) {
            subject_tv.setText(subject);
            standardroom_tv.setText(room);
            hour_button.setText(Integer.toString(hour));
        }

        public void substitution(String subject, String teacher, String room) {
            if (room == null && teacher == null) {
                layout.setBackgroundColor(mcontext.getResources().getColor(R.color.ausfall));
            } else {
                if (teacher == null) {
                    standardroom_tv.setTextColor(mcontext.getResources().getColor(R.color.raumverlegung));
                    standardroom_tv.setTypeface(Typeface.DEFAULT_BOLD);
                    standardroom_tv.setText(room);

                } else {
                    layout.setBackgroundColor(mcontext.getResources().getColor(R.color.vertretung));
                    subject_tv.setText(subject);
                }
            }
        }
    }
}
