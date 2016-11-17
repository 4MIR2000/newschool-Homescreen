package de.newschool.homescreen;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.IntegerRes;
import android.support.design.widget.TabLayout;
import android.widget.Toast;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by ASUS on 08.10.2016.
 */
public class NotAllowedApps {

    public void closeNotAllowedApps(){

        PackageManager manager = MainActivity.getContext().getPackageManager();
        List<ApplicationInfo> apps = manager.getInstalledApplications(0);


        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:de.newschool.homescreen"));
        MainActivity.getContext().startActivity(intent);



    }
}
