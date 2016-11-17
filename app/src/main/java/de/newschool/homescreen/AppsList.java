package de.newschool.homescreen;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.newschool.homescreen.R;

public class AppsList {
    private static List<String> lockedApps = new ArrayList<>();
    private static List<String> allowedApps = new ArrayList<>();

    public static List<String> getLockedApps(){

        lockedApps.add("de.newschool.homescreen");
        lockedApps.add("de.newschool.lockscreen");
        lockedApps.add("com.android.settings");
        lockedApps.add("com.android.vending");
        lockedApps.add("com.android.browser");
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

    public static List<String> getAllowedApps(){
        allowedApps.add("com.Slack");
        allowedApps.add("com.discord");
        allowedApps.add("com.steadfastinnovation.android.projectpapyrus");
        allowedApps.add("com.xodo.pdf.reader");
        allowedApps.add("com.intsig.camscanner");
        allowedApps.add("mmi.android.timetable");
        allowedApps.add("com.cateater.stopmotionstudio");
        allowedApps.add("com.sonymobile.sketch");
        allowedApps.add("com.spotify.music");
        allowedApps.add("de.phase6.freeversion.beta");
        allowedApps.add("com.rubengees.vocables");
        allowedApps.add("org.kman.WifiManager");
        allowedApps.add("org.geogebra");
        allowedApps.add("com.meisterlabs.mindmeister");

        allowedApps.add("com.visionobjects.calculator_huawei");
        allowedApps.add("com.wacom.bamboopapertab");
        allowedApps.add("org.simalliance.openmobileapi.service");
        allowedApps.add("com.nuance.swype.emui");
        allowedApps.add("com.example.leliu.clarifidemosys");
        allowedApps.add("androidhwext");
        allowedApps.add("android");
        allowedApps.add("com.visionobjects.stylusmobile.v3_2_huawei");
        allowedApps.add("com.dropbox.android");
        allowedApps.add("com.example.android.notepad");
        allowedApps.add("org.khanacademy.android");
        allowedApps.add("de.phase6.freeversion.beta");
        allowedApps.add("jp.co.omronsoft.openwnn");
        allowedApps.add("com.futurewei.ecens.mocalite");
        allowedApps.add("com.fingerprints.service");
        allowedApps.add("com.steadfastinnovation.android.projectpapyrus");
        allowedApps.add("com.soundcloud.android");
        allowedApps.add("com.gmd.immersive");
        allowedApps.add("com.mgyun.shua.protector");
        allowedApps.add("cn.wps.moffice_eng");

        allowedApps.add("org.geogebra.android");

        allowedApps.add("org.leo.android.dict");
        allowedApps.add("com.gamestar.pianoperfect");
        allowedApps.add("com.neuratron.notatemenow");





        return allowedApps;

    }
}
