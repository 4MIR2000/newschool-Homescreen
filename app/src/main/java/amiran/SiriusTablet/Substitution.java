package amiran.SiriusTablet;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 30.06.2016.
 */
public class Substitution {

    public static final String LOG_TAG = Substitution.class.getName();
    public Context mcontext;

    public Substitution(Context context){
        mcontext = context;

    }

    public  Timetable_WeekDetail getSubstitution(){

        Timetable_WeekDetail week = null;
        File file = new File(Environment.getExternalStorageDirectory()+File.separator+"NewSchool"+File.separator+
                "Vertretungsplan"+File.separator+"vp.txt");
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;




        try{

            week = new Timetable_WeekDetail();
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);


            TextUtils.SimpleStringSplitter day_splitter = new TextUtils.SimpleStringSplitter('*');
            TextUtils.SimpleStringSplitter hour_splitter = new TextUtils.SimpleStringSplitter(';');
            TextUtils.SimpleStringSplitter subject_splitter = new TextUtils.SimpleStringSplitter(',');

            String line = br.readLine();


            List<Timetable_DayDetail> days_list = new ArrayList<>();


            for(int i = 0; line!= null; i++) {
                day_splitter.setString(line);

                String day_num = day_splitter.next();
                String other = day_splitter.next();

                hour_splitter.setString(other);

                List<Timetable_HourDetail> hours_list = new ArrayList<>();

                Log.d("substitution line",line);
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

                    Timetable_HourDetail hour_class = new Timetable_HourDetail();
                    hour_class.hour = Integer.parseInt(hour_num);
                    hour_class.room = room;
                    hour_class.teacher = teacher;
                    hour_class.subject = subject;

                    hours_list.add(j,hour_class);

                }

                    Timetable_DayDetail day_class = new Timetable_DayDetail();
                    day_class.day_num = Integer.parseInt(day_num);
                    day_class.hours = hours_list;

                    days_list.add(i,day_class);


                line = br.readLine();


            }

            week = new Timetable_WeekDetail();
            week.days = days_list;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }catch(StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }




        return week;
    }
}
