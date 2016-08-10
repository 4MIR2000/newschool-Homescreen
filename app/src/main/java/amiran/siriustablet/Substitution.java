package amiran.siriustablet;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class Substitution {
    public TimetableWeekDetail getSubstitution() {
        TimetableWeekDetail week = null;
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "NewSchool" + File.separator +
                "Vertretungsplan" + File.separator + "vp.txt");
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            week = new TimetableWeekDetail();
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            TextUtils.SimpleStringSplitter day_splitter = new TextUtils.SimpleStringSplitter('*');
            TextUtils.SimpleStringSplitter hour_splitter = new TextUtils.SimpleStringSplitter(';');
            TextUtils.SimpleStringSplitter subject_splitter = new TextUtils.SimpleStringSplitter(',');

            String line = br.readLine();

            List<TimetableDayDetail> days_list = new ArrayList<>();

            for (int i = 0; line != null; i++) {
                day_splitter.setString(line);

                String day_num = day_splitter.next();
                String other = day_splitter.next();

                hour_splitter.setString(other);

                List<TimetableHourDetail> hours_list = new ArrayList<>();

                Log.d("substitution line", line);
                for (int j = 0; hour_splitter.hasNext(); j++) {
                    String hour = hour_splitter.next();
                    subject_splitter.setString(hour);

                    String hour_num = subject_splitter.next();
                    String kindOfSubstitution = subject_splitter.next();

                    String room = null;
                    String teacher = null;
                    String subject = null;

                    try {
                        if (!kindOfSubstitution.equals("A")) {
                            if (kindOfSubstitution.equals("R")) {
                                room = subject_splitter.next();
                            } else {
                                if (kindOfSubstitution.equals("V")) {
                                    teacher = subject_splitter.next();
                                    subject = subject_splitter.next();
                                    room = subject_splitter.next();
                                }
                            }
                        }
                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    TimetableHourDetail hour_class = new TimetableHourDetail();
                    hour_class.hour = Integer.parseInt(hour_num);
                    hour_class.room = room;
                    hour_class.teacher = teacher;
                    hour_class.subject = subject;

                    hours_list.add(j, hour_class);
                }

                TimetableDayDetail day_class = new TimetableDayDetail();
                day_class.day_num = Integer.parseInt(day_num);
                day_class.hours = hours_list;
                days_list.add(i, day_class);

                line = br.readLine();
            }

            week = new TimetableWeekDetail();
            week.days = days_list;

        } catch (IOException | StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return week;
    }
}
