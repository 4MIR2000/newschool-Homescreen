package amiran.SiriusTablet;

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

import amiran.siriustablet_test.R;

/**
 * Created by ASUS on 09.04.2016.
 */
public class OneDayTimetable_ListAdapter extends BaseAdapter {
    static final String LOG_TAG = OneDayTimetable_ListAdapter.class.getName();
    Context mcontext;
    List<Timetable_HourDetail> mhours;
    List<Timetable_HourDetail> msubstitution;

    public OneDayTimetable_ListAdapter(Context context, List<Timetable_HourDetail> hours, List<Timetable_HourDetail> substitution){
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
        if(convertView == null){
            LayoutInflater inflater =(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.timetable_one_day_list_item,null);


            holder = new Holder(convertView);
            convertView.setTag(holder);

        }else{
            holder = (Holder) convertView.getTag();
        }




        holder.build(position+1,mhours.get(position).subject,mhours.get(position).room);



        if(msubstitution != null) {
            for (Timetable_HourDetail subhour : msubstitution) {

                if (subhour.hour-1 == position) {
                    holder.substitution(subhour.hour, subhour.subject,subhour.teacher, subhour.room);
                }
            }

        }








        return convertView;
    }

    private class Holder{
        TextView subject_tv;
        TextView standardroom_tv;
        Button hour_button;
        RelativeLayout layout;


        View convertView;
        public Holder(View parent){
            convertView = parent;
            subject_tv = (TextView) convertView.findViewById(R.id.subject_timetable);
            standardroom_tv = (TextView) convertView.findViewById(R.id.standardroom_timetable);
            hour_button = (Button)convertView.findViewById(R.id.hour_timetable);
            layout = (RelativeLayout)convertView.findViewById(R.id.timetable_item_layout_color);

        }



        public void build(int hour,String subject,String room){

            subject_tv.setText(subject);
            standardroom_tv.setText(room);
            hour_button.setText(Integer.toString(hour));

        }

        public void substitution(int hour,String subject,String teacher, String room){
            if(room == null && teacher == null){
                layout.setBackgroundColor(mcontext.getResources().getColor(R.color.ausfall));
            }else{
                if(teacher == null){
                    standardroom_tv.setTextColor(mcontext.getResources().getColor(R.color.raumverlegung));
                    standardroom_tv.setTypeface(Typeface.DEFAULT_BOLD);
                    standardroom_tv.setText(room);

                }else{
                    if(teacher != null){
                        layout.setBackgroundColor(mcontext.getResources().getColor(R.color.vertretung));
                        subject_tv.setText(subject);
                    }
                }
            }
        }
    }
}
