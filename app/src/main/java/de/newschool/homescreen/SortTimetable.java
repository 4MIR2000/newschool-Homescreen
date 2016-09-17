package de.newschool.homescreen;

import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;

class SortTimetable {
    public static int getlongestDayHours(TimetableWeekDetail timetable) {
        TimetableWeekDetail mtimetable = timetable;
        TimetableDayDetail temp;

        int i = 0;
        for (int j = 0; j < mtimetable.days.size(); j++) {

            if (mtimetable.days.get(j).hours.size() > mtimetable.days.get(i).hours.size()) {


                //swapping
                temp = mtimetable.days.get(i);
              //  mtimetable.days.remove(i);
                mtimetable.days.add(i, mtimetable.days.get(j));


              //  mtimetable.days.remove(j);
                mtimetable.days.add(j, temp);


            }
        }

        return mtimetable.days.get(0).hours.size();

    }
}
