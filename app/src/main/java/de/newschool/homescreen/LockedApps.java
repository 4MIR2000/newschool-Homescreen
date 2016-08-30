package de.newschool.homescreen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 30.08.2016.
 */
public class LockedApps {

    public static String[][] getLockedApps(){

        String[][] apps = new String[][]{{
                "de.newschool.homescreen",
                "com.android.settings",
                "com.android.vending",
                "com.android.browser",
                "com.google.android.apps.docs",
                "com.google.android.gm",
                "com.google.android.googlequicksearchbox",
                "com.google.android.apps.plus",
                "com.google.android.talk",
                "com.google.android.play.games",
                "com.google.android.videos",
                "com.huawei.systemmanager",
                "com.google.android.googlequicksearchbox",
               },

                {
                        "de.newschool.homescreen.MainActivity",
                        "com.android.settings.HWSettings",
                        "com.android.vending.AssetBrowserActivity",
                        "com.android.browser.BrowserActivity",
                        "com.google.android.apps.docs.app.NewMainProxyActivity",
                        "com.google.android.gm.ConversationListActivityGmail",
                        "com.google.android.googlequicksearchbox.SearchActivity",
                        "com.google.android.apps.plus.phone.HomeActivity",
                        "com.google.android.talk.SigningInActivity",
                        "com.google.android.gms.games.ui.destination.main.MainActivity",
                        "com.google.android.youtube.videos.EntryPoint",
                        "com.huawei.systemmanager.mainscreen.MainScreenActivity",
                        "com.google.android.googlequicksearchbox.VoiceSearchActivity",
                        }

        };


        return apps;
    }

}
