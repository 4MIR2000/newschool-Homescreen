package amiran.siriustablet;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import amiran.siriustablet.test.R;

public class WeekTimetableActivity extends ActionBarActivity {
    RelativeLayout timetable_layout;
    private GridView timetable_gridView;
    private GridView days_gridView;

    private final static String LOG_TAG = WeekTimetableActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_timetable);

        // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //timetable_layout = (RelativeLayout)findViewById(R.id.weekTimetable);
        //addTimetable_table();

        int gridView_leftmargin = (int) (getResources().
                getDimension(R.dimen.weekTimetable_horizontal_margin) / getResources().getDisplayMetrics().density);

        days_gridView = (GridView) findViewById(R.id.days_grid);
        timetable_gridView = (GridView) findViewById(R.id.subjects_grid);

        Timetable timetable_class = new Timetable(this);
        TimetableWeekDetail timetable = timetable_class.getTimetable();
        TimetableWeekDetail timetable_ForSort = timetable_class.getTimetable();


        LinearLayout.LayoutParams days_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        days_params.leftMargin = gridView_leftmargin;
        days_gridView.setLayoutParams(days_params);

        days_gridView.setNumColumns(timetable.days.size());
        days_gridView.setAdapter(new WeekTimetableDaysGridAdapter(this, timetable));

        int timetable_sorted = SortTimetable.getlongestDayHours(timetable_ForSort);
        timetable_gridView.setAdapter(new WeekTimetableGridAdapter(this, timetable, timetable_sorted));
        timetable_gridView.setNumColumns(timetable.days.size());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = gridView_leftmargin;
        timetable_gridView.setLayoutParams(params);
    }

    private void addTimetable_table() {
        //getting the timetable
        Timetable timetable_class = new Timetable(this);
        TimetableWeekDetail timetable = new TimetableWeekDetail();
        timetable = timetable_class.getTimetable();

        TableLayout.LayoutParams tableLayout_params = new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        // TableLayout table = (TableLayout)findViewById(R.id.timetable_tableLayout);

        TableLayout.LayoutParams tableRowParams = new TableLayout.LayoutParams();
        tableRowParams.topMargin = 20;

        View convertView;

        TableRow.LayoutParams convertView_lp = new TableRow.LayoutParams();
        convertView_lp.rightMargin = 20; // right-margin = 10dp

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //getting the hour numbers of the longest day
        //have a look at SortTimetable

        int hoursnum;
        hoursnum = SortTimetable.getlongestDayHours(timetable);

        //for getting the right order of timetable again
        timetable = timetable_class.getTimetable();

        Log.d(LOG_TAG, Integer.toString(timetable.days.get(0).hours.size()));

        //for days: monday, tuesday, ...
        int a = 0;

        //go throw all days
        for (int i = 0; i < hoursnum; i++) {
            TableRow row = new TableRow(this);

            for (int j = 0; j < timetable.days.size(); j++) {
                //TimetableHourDetail hour_detail = timetable.days.get(j).hours.get(i);
                Log.d(LOG_TAG, "day = " + Integer.toString(j) + "hour = " + Integer.toString(i));

                convertView = inflater.inflate(R.layout.timetable_week_item, null);

                TextView subject_tv = (TextView) convertView.findViewById(R.id.subject_timetable);
                TextView standardroom_tv = (TextView) convertView.findViewById(R.id.standardroom_timetable);

                if (a == 0) {
                    subject_tv.setTextSize(30);
                    subject_tv.setText(timetable.days.get(j).day_name);
                    standardroom_tv.setText("");
                    convertView.setBackgroundColor(Color.YELLOW);
                } else {
                    //if i is bigger than the size of our hours of the day
                    //make the following textviews empety
                    if (i > timetable.days.get(j).hours.size() - 1) {
                        subject_tv.setText("");
                        standardroom_tv.setText("");
                    } else {
                        subject_tv.setText(timetable.days.get(j).hours.get(i).subject);
                        standardroom_tv.setText(timetable.days.get(j).hours.get(i).room);
                    }
                }

                convertView.setLayoutParams(convertView_lp);
                row.addView(convertView);
                row.setLayoutParams(tableRowParams);
            }

            if (a == 0) {
                a = 1;
                i--;
            }

            // table.addView(row,tableRowParams);
        }
    }
}
