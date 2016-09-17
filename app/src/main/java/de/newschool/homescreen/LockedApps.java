package de.newschool.homescreen;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ASUS on 30.08.2016.
 */
public class LockedApps {

    private static List<String> lockedApps = new ArrayList<>();

    public static List<String> getLockedApps(){

        lockedApps.add("de.newschool.homescreen");
        lockedApps.add("de.newschool.lockscreen");
        lockedApps.add("com.android.settings");
        lockedApps.add( "com.android.vending");
        lockedApps.add( "com.android.browser");
        lockedApps.add("com.google.android.apps.docs");
        lockedApps.add("com.google.android.gm");
        lockedApps.add("com.google.android.googlequicksearchbox");
        lockedApps.add("com.google.android.apps.plus");
        lockedApps.add("com.google.android.talk");
        lockedApps.add("com.google.android.play.games");
        lockedApps.add("com.google.android.videos");
        lockedApps.add( "com.huawei.systemmanager");
        lockedApps.add("com.google.android.gms");
        lockedApps.add("com.google.android.apps.photos");
        lockedApps.add("com.google.android.apps.books");
        lockedApps.add("com.google.android.music");
        lockedApps.add("com.google.android.apps.magazines");


        return lockedApps;
    }

    public static void addLockedApps(List<String> apps){
        for(int i=0; i<apps.size();i++){

            lockedApps.add(apps.get(i));
        }
    }

    public static void removeLockedApps(List<String> apps){

//        Toast.makeText(MainActivity.getContext(),lockedApps[0].length,Toast.LENGTH_SHORT).show();
        for(int i=0; i<apps.size();i++){
            for(int j=0; j<lockedApps.size(); j++){
                if(lockedApps.get(j).equals(apps.get(i))){

                     lockedApps.remove(j);

                }
            }
        }
    }

}
