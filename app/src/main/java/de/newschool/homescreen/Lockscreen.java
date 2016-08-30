package de.newschool.homescreen;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;

import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import de.newschool.homescreen.R;

public class Lockscreen extends AppCompatActivity {
    private static final String LOG_TAG = Lockscreen.class.getName();

    CustomViewGroup systemUIHider;
    WindowManager windowManager;

    private TextView date_tv;
    private Calendar calendar;

    private TextView name_tv;
    private TextView class_tv;

    private ShimmerTextView slideToUnlock_Tv;
    private Shimmer shimmer;
    private GestureDetector gestureDetector;

    private final Activity activity = this;

    private GridView timetable_grid;
    private ImageView lock;

    private LinearLayout lockscreen_timetable;
    private ProgressBar loadingBar;

    Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockscreen);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        lockscreen_timetable = (LinearLayout) findViewById(R.id.timetable_lockscreen);
        lock = (ImageView) findViewById(R.id.lock);

        name_tv = (TextView)findViewById(R.id.name_tv);
        class_tv = (TextView) findViewById(R.id.class_tv);

    /*  try{
        StateListener phoneStateListener = new StateListener();
        TelephonyManager telephonymanager = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        telephonymanager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
    }catch(Exception e){ }*/

        date_tv = (TextView) findViewById(R.id.date_tv);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("E dd.MM.yyyy");
        date_tv.setText(format.format(calendar.getTime()));

        slideToUnlock_Tv = (ShimmerTextView) findViewById(R.id.slideToUnlock);

        shimmer = new Shimmer();
        shimmer.setDuration(2000);
        shimmer.start(slideToUnlock_Tv);

        timetable_grid = (GridView) findViewById(R.id.timetable_lockscreen_gridView);
        loadingBar = (ProgressBar) findViewById(R.id.progressBar);

        hideSystemUI();
        new ShowUserInfo().execute();


    }



    //hide homebutton,backbutton,taskmanagerbutton
    private void hideSystemUI(){
        windowManager = ((WindowManager)getApplicationContext()
               .getSystemService(Context.WINDOW_SERVICE));
        WindowManager.LayoutParams localLayoutParams =  new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.BOTTOM;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|

                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (50* getResources().getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.TRANSPARENT;
        systemUIHider = new CustomViewGroup(this);
        windowManager.addView(systemUIHider,localLayoutParams);

    }

    private class ShowUserInfo extends AsyncTask<String,Void,String>{
        String username;
        String schoolclass;
        @Override
        protected String doInBackground(String... params) {
            username = UserInfo.getUsername();
            schoolclass  = UserInfo.getSchoolClass();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            name_tv.setText(username);
            class_tv.setText(schoolclass);
        }



    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            final View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(flags);
        }
    }

    public class LockCheck extends AsyncTask<String, Void, String> {
        boolean locked = false;
        String line;

        @Override
        protected String doInBackground(String... params) {
            /*try {
               URL url = new URL("http://s1.demo.newschool-tablet.de:3000/status");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setConnectTimeout(5000);
                //handler.postDelayed(new Timetable_Runnable(connection),2000);

                try {
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    line = br.readLine();
                    if (line != null) {
                        if (line.equals("false")) {
                            locked = false;
                        } else {
                            if (line.equals("true"))
                                locked = true;
                        }
                    }
                } finally {
                    connection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Toast.makeText(this,"Übeprüfe deine Internetverbindung",Toast.LENGTH_SHORT).show();
            }*/

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (locked) {
                gestureDetector = null;
                lockscreen_timetable.setVisibility(View.GONE);
                lock.setVisibility(View.VISIBLE);
                loadingBar.setVisibility(View.GONE);
            } else {
                if (!locked) {
                    gestureDetector = new GestureDetector(Lockscreen.this, new MyGestureListener(activity, Lockscreen.this));
                    lockscreen_timetable.setVisibility(View.VISIBLE);
                    lock.setVisibility(View.GONE);
                    loadingBar.setVisibility(View.GONE);
                }
            }
        }
    }

    public class TimetableDeclaration extends AsyncTask<String, Void, String> {
        List<SubjectDetail> subjects;
        TimetableDayDetail day_timetable;

        @Override
        protected String doInBackground(String... params) {
            calendar = Calendar.getInstance();
            int which_day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
            if (which_day == -1) {
                which_day = 7;
            }

            Timetable timetable_class = new Timetable(Lockscreen.this);
            TimetableWeekDetail timetable = timetable_class.getTimetable();
            if (timetable != null) {
                if (which_day < timetable.days.size()) {
                    day_timetable = timetable_class.getTimetable().days.get(which_day);

                    SubjectsList list = new SubjectsList();

                    //for getting the all subjects list
                    //we want to get the icon of one subject

                    subjects = list.getAllSubjects();
                } else {
                    day_timetable = null;
                }
            } else {
                day_timetable = null;
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            LinearLayout parent = (LinearLayout) findViewById(R.id.timetable_lockscreen);
            parent.removeAllViews();

            if (day_timetable != null) {
                ImageView[] hours_views = new ImageView[day_timetable.hours.size()];

                for (int i = 0; i < day_timetable.hours.size(); i++) {
                    for (int j = 0; j < subjects.size(); j++) {
                        hours_views[i] = new ImageView(Lockscreen.this);
                        hours_views[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));

                        if (Objects.equals(subjects.get(j).name, day_timetable.hours.get(i).subject)) {
                            hours_views[i].setBackgroundResource(subjects.get(j).pic);
                            break;
                        }
                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
                    params.setMargins(5, 0, 5, 0);

                    //parent.setLayoutParams(parent_params);
                    parent.addView(hours_views[i], params);
                }

                int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
                int current_minute = calendar.get(Calendar.MINUTE);

                for (int i = 0; i < day_timetable.hours.size(); i++) {
                    HourTime time = day_timetable.hours.get(i).time;

                    if (current_hour >= time.hour_start && current_hour <= time.hour_end) {

                        Log.d(LOG_TAG, "actual hour" + Integer.toString(time.hour_start));
                        Log.d(LOG_TAG, "actual hour end" + Integer.toString(time.hour_end));


                        if(current_hour==time.hour_start && current_hour == time.hour_end){
                            if(current_minute >= time.minute_start && current_minute < time.minute_end){
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 250);
                                params.setMargins(5, 0, 5, 0);
                                //params.gravity = Gravity.CENTER;
                                hours_views[i].setLayoutParams(params);


                                return;
                            }
                        }else{
                        if (current_hour == time.hour_start) {

                            if (current_minute >= time.minute_start) {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 250);
                                params.setMargins(5, 0, 5, 0);
                                //params.gravity = Gravity.CENTER;
                                hours_views[i].setLayoutParams(params);

                                //Toast.makeText(Lockscreen.this,Integer.toString(time.hour_start)+":"+Integer.toString(time.minute_start)+"-"+Integer.toString(time.hour_end)+":"+Integer.toString(time.minute_end),Toast.LENGTH_LONG).show();
                                return;
                            }
                        }else{
                            if (current_hour == time.hour_end) {

                                if (current_minute < time.minute_end) {
                                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 250);
                                    params.setMargins(5, 0, 5, 0);
                                    // params.gravity = Gravity.CENTER;
                                    hours_views[i].setLayoutParams(params);


                                    return;
                                }
                            } else {
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(250, 250);
                                params.setMargins(5, 0, 5, 0);
                                // params.gravity = Gravity.CENTER;
                                hours_views[i].setLayoutParams(params);
                                return;
                            }
                        }

                        }
                    }
                }
            }
        }
    }

    private void timetableDeclaration() {
        LinearLayout parent = (LinearLayout) findViewById(R.id.timetable_lockscreen);
        Calendar calendar = Calendar.getInstance();
        int which_day = calendar.get(Calendar.DAY_OF_WEEK) - 2;

        Timetable timetable_class = new Timetable(this);
        TimetableWeekDetail timetable = timetable_class.getTimetable();

        if (timetable != null) {
            if (which_day < timetable.days.size()) {
                TimetableDayDetail day_timetable = timetable_class.getTimetable().days.get(which_day);

                SubjectsList list = new SubjectsList();

                //for getting the all subjects list
                //we want to get the icon of one subject

                List<SubjectDetail> subjects = list.getAllSubjects();

                ImageView[] hours_views = new ImageView[day_timetable.hours.size()];

                Log.d(LOG_TAG, Integer.toString(day_timetable.hours.size()));
                for (int i = 0; i < day_timetable.hours.size(); i++) {
                    for (int j = 0; j < subjects.size(); j++) {
                        hours_views[i] = new ImageView(this);
                        hours_views[i].setBackgroundColor(getResources().getColor(R.color.colorAccent));

                        if (Objects.equals(subjects.get(j).name, day_timetable.hours.get(i).subject)) {
                            hours_views[i].setImageResource(subjects.get(j).pic);
                            break;
                        }
                    }

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
                    params.setMargins(5, 0, 5, 0);
                    parent.addView(hours_views[i], params);
                }

                int current_hour = calendar.get(Calendar.HOUR_OF_DAY);
                int current_minute = calendar.get(Calendar.MINUTE);

                for (int i = 0; i < day_timetable.hours.size(); i++) {
                    HourTime time = day_timetable.hours.get(i).time;
                    if (current_hour >= time.hour_start && current_hour <= time.hour_end) {
                        if (current_minute >= time.minute_start && current_minute <= time.minute_end) {
                            Log.d("actual hour", Integer.toString(time.hour_start));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400, 400);
                            params.setMargins(5, 0, 5, 0);
                            hours_views[i].setLayoutParams(params);
                        }
                    }
                }
            }
        }
    }

    private class StateListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING:
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    System.out.println("call Activity off hook");

                    // Finish lock screen activity
                    finish();
                    break;
                case TelephonyManager.CALL_STATE_IDLE:
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Don't allow back to dismiss.
    }

    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_HOME)) {
            return true;
        }

        if ((keyCode == KeyEvent.KEYCODE_TAB)) {
            Toast.makeText(this, "3D_MODE", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }


    @Override
    protected void onResume() {
        //hideSystemUI();
        new TimetableDeclaration().execute();
        new LockCheck().execute();

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(windowManager != null)
        windowManager.removeView(systemUIHider);
    }
}
