package de.newschool.homescreen;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Timetable {
    private static final String LOG_TAG = Timetable.class.getName();
    private final Context mcontext;

    public Timetable(Context context) {
        mcontext = context;
    }

    public TimetableWeekDetail getTimetable() {
        return getTimetableFromFile();
    }

    private TimetableWeekDetail getTimetableFromFile() {
        //the File of our txt file
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + ".NewSchool" +
                File.separator + "Stundenplan" + File.separator + "st.txt");


        TimetableWeekDetail week = null;

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            week = new TimetableWeekDetail();
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            String line = br.readLine();
            Log.d("line", line);

            TextUtils.SimpleStringSplitter day_splitter = new TextUtils.SimpleStringSplitter('*');
            TextUtils.SimpleStringSplitter hour_splitter = new TextUtils.SimpleStringSplitter(';');
            TextUtils.SimpleStringSplitter subject_splitter = new TextUtils.SimpleStringSplitter(',');
            TextUtils.SimpleStringSplitter time_endStart_splitter = new TextUtils.SimpleStringSplitter('-');
            TextUtils.SimpleStringSplitter hour_minute_splitter = new TextUtils.SimpleStringSplitter(':');

            String other;
            String daynum;
            List<TimetableHourDetail> hours_list;
            List<TimetableDayDetail> days_list = new ArrayList<>();

            for (int i = 0; line != null; i++) {
                hours_list = new ArrayList<>();

                day_splitter.setString(line);
                daynum = day_splitter.next();
                other = day_splitter.next();

                hour_splitter.setString(other);
                for (int j = 0; hour_splitter.hasNext(); j++) {

                    String hour = hour_splitter.next();
                    subject_splitter.setString(hour);

                    TimetableHourDetail hour_class = new TimetableHourDetail();
                    hour_class.subject = subject_splitter.next();
                    hour_class.room = subject_splitter.next();

                    String time = subject_splitter.next();
                    time_endStart_splitter.setString(time);

                    String time_start = time_endStart_splitter.next();
                    String time_end = time_endStart_splitter.next();

                    hour_minute_splitter.setString(time_start);
                    String time_hour_start = hour_minute_splitter.next();
                    String time_minute_start = hour_minute_splitter.next();

                    hour_minute_splitter.setString(time_end);
                    String time_hour_end = hour_minute_splitter.next();
                    String time_minute_end = hour_minute_splitter.next();


                    HourTime time_class = new HourTime();
                    time_class.hour_start = Integer.parseInt(time_hour_start);
                    time_class.minute_start = Integer.parseInt(time_minute_start);

                    time_class.hour_end = Integer.parseInt(time_hour_end);
                    time_class.minute_end = Integer.parseInt(time_minute_end);


                    //setting the time Object;
                    hour_class.time = time_class;

                    hours_list.add(j, hour_class);
                }

                TimetableDayDetail day = new TimetableDayDetail();
                day.hours = hours_list;
                day.day_name = daynum;
                days_list.add(i, day);

                line = br.readLine();
            }

            week.days = days_list;

            fis.close();
            isr.close();
            br.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
//                Toast.makeText(mcontext,"st.txt wurde nicht gefunden!",Toast.LENGTH_SHORT).show();
            week = null;
        } catch (IOException e) {
            e.printStackTrace();
            week = null;
            //  Toast.makeText(mcontext,"Linie in st.txt konnte nicht gelesen werden",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            week = null;
            //  Toast.makeText(mcontext,"Fehler beim Lesen der st.txt",Toast.LENGTH_SHORT).show();
        }

        if (week != null) {
            if (week.days.size() != 0) {
                return week;
            }
        } else {
            Log.d(LOG_TAG, "week is null");
        }

        return null;
    }
}
