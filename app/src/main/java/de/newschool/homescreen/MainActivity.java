package de.newschool.homescreen;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;

import org.askerov.dynamicgrid.DynamicGridView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import multiscreenfragments.PagerChangeListener;
import multiscreenfragments.ViewPagerAdapter;



public class MainActivity extends Activity implements View.OnClickListener {
    private static final String LOG_TAG = MainActivity.class.getName();
    static Activity activity; // @TODO Dangerous static instance
    private LayoutInflater inflater;
    //layout views declaration
    private GridView drawergrid;
    static SlidingDrawer slidingDrawer; // @TODO Dangerous static instance
    private LinearLayout app_widget_layout;
    private LinearLayout app_widget_child_layout;
    private TextView delete_bar;
    private ProgressBar loading_bar;

    private PackageManager manager;
    private DrawerAdapter drawerAdapterObject;

    public static ArrayList<AppDetail> apps; // @TODO Dangerous static instance
    static boolean isLaunchable = true; // @TODO Dangerous static instance

    private AppWidgetManager mAppWidgetManager;
    private LauncherAppWidgetHost mAppWidgetHost;

    private final int REQUEST_CREATE_APPWIDGET = 900;
    private final int REQUEST_PICK_APPWIDGET = 800;

    private DynamicGridView subjects_grid;
    private ListView oneDayTimetable;

    static ViewPager multiscreen_pager; // @TODO Dangerous static instance
    static ViewPagerAdapter mpagerAdapter; // @TODO Dangerous static instance
    private ImageView[] dots;
    static LinearLayout viewPagerIndicator_layout; // @TODO Dangerous static instance
    private List<RelativeLayout> multiScreen_layouts;

    private int day_of_timetable = 1000;

    private int timesClickedTheAppsButton;
    Handler optionsOpenRequestHandler;
    Runnable optionsOpenRequestrunnable;

    Handler updateCheckerHandler;
    Runnable updateCheckerRunnable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         hideStatusBar();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //LOCKSCREEN RECEIVER
        IntentFilter lockscreen_filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        lockscreen_filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        registerReceiver(new LockscreenReceiver(),lockscreen_filter);


        activity = this;
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        intialize_AllApps_Drawer();

        app_widget_layout = (LinearLayout) findViewById(R.id.app_widget_placement);
        app_widget_child_layout = (LinearLayout) findViewById(R.id.app_widget_child_layout);

        delete_bar = (TextView) findViewById(R.id.delete_bar);
        loading_bar = (ProgressBar) findViewById(R.id.loadingbar);

        oneDayTimetable = (ListView) findViewById(R.id.oneDayTimetable);

        //the view pager is for the appWidget_placement
        //to have multiscreens
        intializeMultiscreenPager();

        manager = getPackageManager();

        Substitution substitution_class = new Substitution();

        new SubjectsGrid_declaration().execute();
        //new OneDayTimeTable_declaration().execute();


        //For reloading timetable if date changes
        IntentFilter date_changed_filter = new IntentFilter();
        date_changed_filter.addAction(Intent.ACTION_DATE_CHANGED);

        new LoadApps().execute();
        new AddAppsToHome().execute();

        //so the app is lauchable if the slidingdrawer opens
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                isLaunchable = true;

            }
        });

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_ADDED);
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED);
        filter.addDataScheme("package");

        //for refreshing the GridView
        registerReceiver(new appsReceiver(), filter);

        //AppWidgetManager gives us data about the installed Widgets
        mAppWidgetManager = AppWidgetManager.getInstance(this);

        //AppWidgetHost will keep the widget instances in memory
        mAppWidgetHost = new LauncherAppWidgetHost(this, R.id.APPWIDGET_HOST_ID);

        app_widget_layout.setOnLongClickListener(new Home_Longclick());

        //fullscreen
        //  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        checkForUpdates();

        Files.makeFiles();

       // new OneDayTimeTable_declaration().execute();

    }

    private void checkForUpdates() {
        updateCheckerHandler = new Handler();
        updateCheckerRunnable = new UpdateCheckerRunnable();

        updateCheckerHandler.postDelayed(updateCheckerRunnable,1000);
    }

    private class UpdateCheckerRunnable implements Runnable{

        @Override
        public void run() {

            Updates updates_class = new Updates();
            updates_class.checkForUpdates();
            updateCheckerHandler.postDelayed(updateCheckerRunnable,1800000);

        }
    }


    private void hideStatusBar(){
        WindowManager manager = ((WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE));

        WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams();
        localLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ERROR;
        localLayoutParams.gravity = Gravity.TOP;
        localLayoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|

                // this is to enable the notification to recieve touch events
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL |

                // Draws over status bar
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;

        localLayoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        localLayoutParams.height = (int) (50 * getResources()
                .getDisplayMetrics().scaledDensity);
        localLayoutParams.format = PixelFormat.TRANSPARENT;

        CustomViewGroup view = new CustomViewGroup(this);

        manager.addView(view, localLayoutParams);

    }

    private void intialize_AllApps_Drawer() {
        slidingDrawer = (SlidingDrawer) inflater.inflate(R.layout.apps_sliding_drawer, null);
        drawergrid = (GridView) slidingDrawer.findViewById(R.id.content);
    }

    private void intializeMultiscreenPager() {
        multiscreen_pager = (ViewPager) findViewById(R.id.viewpager);
        multiScreen_layouts = new ArrayList<>();
        multiScreen_layouts.add(0, (RelativeLayout) inflater.inflate(R.layout.homescreen_fragment1_layout, null));
        multiScreen_layouts.add(1, (RelativeLayout) inflater.inflate(R.layout.homescreen_fragment2_layout, null));
        multiScreen_layouts.add(2, (RelativeLayout) inflater.inflate(R.layout.homescreen_fragment3_layout, null));

        mpagerAdapter = new ViewPagerAdapter(multiScreen_layouts);
        multiscreen_pager.setAdapter(mpagerAdapter);

        multiscreen_pager.setCurrentItem(1);
        intializedotsIndicator();
    }

    private void intializedotsIndicator() {
        viewPagerIndicator_layout = (LinearLayout) findViewById(R.id.viewPager_indicator_child);
        int dotsCount = mpagerAdapter.getCount();
        dots = new ImageView[dotsCount];

        //Log.d("dots",Integer.toString(dotsCount));
        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.indicator_nonselecteditem_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(16, 0, 16, 0);
            params.gravity = Gravity.CENTER;
            viewPagerIndicator_layout.addView(dots[i], params);
        }

        dots[1].setImageDrawable(getResources().getDrawable(R.drawable.indicator_selecteditem_dot));
        multiscreen_pager.setOnPageChangeListener(new PagerChangeListener(dots));
    }

    private class ReloadTimetable extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            new OneDayTimeTable_declaration().execute();
        }
    }

    public class OneDayTimeTable_declaration extends AsyncTask<String, Void, String> {
        List<TimetableHourDetail> timetable_hours = null;
        List<TimetableHourDetail> substitution_hours = null;

        @Override
        protected String doInBackground(String... params) {
            Calendar calendar = Calendar.getInstance();
            int which_day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
            if (which_day == -1) {
                which_day = 6;
            }

            day_of_timetable = which_day;
            Log.d("which day", Integer.toString(which_day));
            Timetable timetable_class = new Timetable(MainActivity.this);
            Substitution substitution_class = new Substitution();
            if (timetable_class.getTimetable() != null) {

                if (which_day < timetable_class.getTimetable().days.size()) {
                    timetable_hours = timetable_class.getTimetable().days.get(which_day).hours;
                } else {
                    timetable_hours = new ArrayList<>();
                    for (int i = 0; i < 9; i++) {
                        timetable_hours.add(i, new TimetableHourDetail());
                    }
                }

                if (substitution_class.getSubstitution() != null) {
                    List<TimetableDayDetail> days = substitution_class.getSubstitution().days;
                    for (int i = 0; i < days.size(); i++) {
                        Log.d(LOG_TAG, Integer.toString(days.get(i).day_num));

                        //which_day+1 because it start from 0
                        if (days.get(i).day_num == which_day + 1) {
                            substitution_hours = days.get(i).hours;
                            Log.d(LOG_TAG, "we have the sub");
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            loading_bar.setVisibility(View.GONE);
            oneDayTimetable.setVisibility(View.VISIBLE);

            if (timetable_hours != null) {
                oneDayTimetable.setAdapter(new OneDayTimetableListAdapter(MainActivity.this, timetable_hours, substitution_hours));
                oneDayTimetable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        startActivity(new Intent(MainActivity.this, WeekTimetableActivity.class));
                    }
                });
            }
        }
    }

    public class SubjectsGrid_declaration extends AsyncTask<String, Void, String> {

        List<SubjectDetail> allSubjectsShowInHomescreen = null;

        @Override
        protected String doInBackground(String... params) {

            List<SubjectDetail> allSubjects = null;
            SubjectsList list = new SubjectsList();
            subjects_grid = (DynamicGridView) findViewById(R.id.subjects_grid);

            allSubjects = list.getAllSubjects();
            allSubjectsShowInHomescreen = new ArrayList<>();

            if(allSubjects != null) {
                for (int i = 0; i < allSubjects.size(); i++) {

                    if (allSubjects.get(i).showInHomescreen) {
                        allSubjectsShowInHomescreen.add(allSubjects.get(i));
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (allSubjectsShowInHomescreen != null) {
                subjects_grid.setAdapter(new SubjectsGridAdapter(MainActivity.this, allSubjectsShowInHomescreen,
                       getResources().getInteger(R.integer.column_count)));

                subjects_grid.setOnItemLongClickListener(new SubjectListeners(MainActivity.this, subjects_grid));
                subjects_grid.setOnItemClickListener(new SubjectListeners(MainActivity.this, subjects_grid));

                Log.d("width", Integer.toString(subjects_grid.getWidth()));


            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.apps_icon_imageView) {
            open_allApps_drawer();
            timesClickedTheAppsButton++;

            if(optionsOpenRequestrunnable != null) {
                optionsOpenRequestHandler.removeCallbacks(optionsOpenRequestrunnable);
            }


            if(timesClickedTheAppsButton == 10) {

                showOptionsOpenRequestDialog();
            }

            optionsOpenRequestHandler = new Handler();
            optionsOpenRequestrunnable = new timesClickedAppsButtonRunnable();
            optionsOpenRequestHandler.postDelayed(optionsOpenRequestrunnable,500);
        }
    }

    private class timesClickedAppsButtonRunnable implements Runnable {
        @Override
        public void run() {
            //set variable to 0 after 500 ms
            timesClickedTheAppsButton = 0;

        }
    }

    private void showOptionsOpenRequestDialog(){
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View convertView = inflater.inflate(R.layout.settings_open_request,null);

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(getString(R.string.openSettingsMessage));
        alertDialog.setView(convertView);
        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(Objects.equals(((EditText) convertView.findViewById(R.id.editText_pin)).getText().toString(), "1378")){
                    timesClickedTheAppsButton = 0;
                    optionsOpenRequestHandler.removeCallbacks(optionsOpenRequestrunnable);

                    Intent settings = new Intent(Intent.ACTION_MAIN);
                    ComponentName cn = new ComponentName("com.android.settings","com.android.settings.HWSettings");
                    settings.setComponent(cn);
                    settings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //so it is a Launcher and not a background app
                    settings.addCategory(Intent.CATEGORY_LAUNCHER);
                    startActivity(settings);
                }else{
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this,getString(R.string.false_pin),Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });

        alertDialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });



        alertDialog.show();


    }


    private void open_allApps_drawer() {
        if (slidingDrawer.getParent() == null) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (app_widget_layout.getMeasuredWidth(), app_widget_child_layout.getMeasuredHeight());
            slidingDrawer.setLayoutParams(params);
            //slidingDrawer.startAnimation(all_apps_anim);
            app_widget_layout.addView(slidingDrawer);
        }

        if (slidingDrawer.isOpened()) {
            multiscreen_pager.setVisibility(View.VISIBLE);
            viewPagerIndicator_layout.setVisibility(View.VISIBLE);
            slidingDrawer.close();
        } else {
            multiscreen_pager.setVisibility(View.GONE);
            viewPagerIndicator_layout.setVisibility(View.INVISIBLE);
            slidingDrawer.setVisibility(View.VISIBLE);
            slidingDrawer.open();
            //slidingDrawer.setVisibility(View.VISIBLE);
            //slidingDrawer.animateOpen();
        }
    }

    //this class helps us to load the installed apps in the background
    private class LoadApps extends AsyncTask<String, Void, String> {
        int position = 0;
        @Override
        protected String doInBackground(String... params) {
            //manager gives data about installed packages
            manager = getPackageManager();

            Intent intent = new Intent(Intent.ACTION_MAIN, null);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> avaibleactivities = manager.queryIntentActivities(intent, 0);

            String[][] lockedApps = LockedApps.getLockedApps();

            apps = new ArrayList<>();

            for (int i = 0; i < avaibleactivities.size(); i++) {
                boolean locked = false;

                lockedLoop:
                for(int j = 0; j<lockedApps[0].length; j++){
                    if(Objects.equals(avaibleactivities.get(i).activityInfo.packageName, lockedApps[0][j])
                            && Objects.equals(avaibleactivities.get(i).activityInfo.name, lockedApps[1][j])){

                        position++;
                        locked = true;
                        break lockedLoop;
                    }
                }

                if(!locked) {
                    AppDetail detail = new AppDetail();
                    detail.label = avaibleactivities.get(i).loadLabel(manager).toString();
                    detail.name = avaibleactivities.get(i).activityInfo.name;
                    detail.packageName = avaibleactivities.get(i).activityInfo.packageName;
                    detail.icon = avaibleactivities.get(i).loadIcon(manager);
                    apps.add(detail);
                }




            }

            SortApps sort = new SortApps();
            sort.exchangeSort(apps);

            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (drawerAdapterObject == null) {
                drawerAdapterObject = new DrawerAdapter(activity);
                //Adding the content look at DrawerAdapter class
                drawergrid.setAdapter(drawerAdapterObject);
                drawergrid.setOnItemLongClickListener(new DrawerLongClick(activity, app_widget_layout, delete_bar));
                drawergrid.setOnItemClickListener(new DrawerClick(activity));
            } else {
                drawerAdapterObject.notifyDataSetInvalidated();
                drawerAdapterObject.notifyDataSetChanged();
            }
        }
    }

    private class AddAppsToHome extends AsyncTask<String, Void, String> {
        AppSerializableData data;

        @Override
        protected String doInBackground(String... params) {
            data = SerializationTools.loadSerializedData();
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (data != null) {
                for (AppDetail appToAddHome : data.apps) {
                    appToAddHome.addToHome(MainActivity.this);
                }
            }
        }
    }

    private class Home_Longclick implements View.OnLongClickListener {
        @Override
        public boolean onLongClick(View v) {
            // selectWidget();
            return false;
        }
    }

    private class appsReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //for refreshing the gridView
            new LoadApps().execute();
        }
    }

    void selectWidget() {
        //showing all Widgets with a list
        int appWidgetId = mAppWidgetHost.allocateAppWidgetId();
        Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
        pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        addEmptyData(pickIntent);
        startActivityForResult(pickIntent, REQUEST_PICK_APPWIDGET);
    }

    private void addEmptyData(Intent pickIntent) {
        //addEmptyData is for preveting a crash if list is null
        ArrayList customInfo = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_INFO, customInfo);
        ArrayList customExtras = new ArrayList();
        pickIntent.putParcelableArrayListExtra(AppWidgetManager.EXTRA_CUSTOM_EXTRAS, customExtras);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_PICK_APPWIDGET) {
                configureWidget(data);
            } else if (requestCode == REQUEST_CREATE_APPWIDGET) {
                createWidget(data);
            }
        } else if (resultCode == RESULT_CANCELED && data != null) {
            int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            if (appWidgetId != -1) {
                mAppWidgetHost.deleteAppWidgetId(appWidgetId);
            }
        }
    }

    private void configureWidget(Intent data) {
        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);
        if (appWidgetInfo.configure != null) {
            Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
            intent.setComponent(appWidgetInfo.configure);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            startActivityForResult(intent, REQUEST_CREATE_APPWIDGET);
        } else {
            createWidget(data);
        }
    }

    private void createWidget(Intent data) {

        Bundle extras = data.getExtras();
        int appWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        AppWidgetProviderInfo appWidgetInfo = mAppWidgetManager.getAppWidgetInfo(appWidgetId);

        //converting to a View
        //we made our own AppwidgetHostview class
        //because we are now allowed to longpress the Widget :D
        LauncherAppWidgetHostView hostView = (LauncherAppWidgetHostView) mAppWidgetHost.createView(this, appWidgetId, appWidgetInfo);

        hostView.setAppWidget(appWidgetId, appWidgetInfo);
        hostView.setTag(appWidgetId);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(hostView.getWidth(), hostView.getHeight());
        // lp.leftMargin = numWidgets * app_widget_layout.getWidth()/3;
        app_widget_layout.addView(hostView);
        hostView.setOnLongClickListener(new ShortcutListeners());

        //Bringing the Drawer into front
        //slidingDrawer.bringToFront();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //updating only if the app is showing on screen
        mAppWidgetHost.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //if app is not showing than stop updating
        mAppWidgetHost.stopListening();
    }

    public void removeWidget(AppWidgetHostView v) {

        mAppWidgetHost.deleteAppWidgetId(v.getAppWidgetId());
        app_widget_layout.removeView(v);
    }

    @Override
    public void onBackPressed() {
        // Don't allow back to dismiss.

        if (slidingDrawer.isOpened()) {
            multiscreen_pager.setVisibility(View.VISIBLE);
            viewPagerIndicator_layout.setVisibility(View.VISIBLE);
            slidingDrawer.animateClose();
            slidingDrawer.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.d("home button", " pressed");

            multiscreen_pager.setCurrentItem(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Calendar calendar = Calendar.getInstance();
        int which_day = calendar.get(Calendar.DAY_OF_WEEK) - 2;
        if (which_day == -1) {
            which_day = 6;
        }

        //if there is a new timetable
        if (day_of_timetable != which_day) {
            oneDayTimetable.setVisibility(View.GONE);
            loading_bar.setVisibility(View.VISIBLE);

            new OneDayTimeTable_declaration().execute();
        }
    }
}
