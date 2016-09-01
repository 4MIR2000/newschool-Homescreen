package de.newschool.homescreen;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ASUS on 01.09.2016.
 */
public class Updates {
    private String getVersionUrl = "http://sirius.ddnss.de:1500/getVersions";
    private String[][] package_Versions;
    private String[] apkUrls = {};

    public void checkForUpdates() {

        new GetVersionsFromServer().execute();

    }

    private class GetVersionsFromServer extends AsyncTask<String, Void, String> {
        String title;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(getVersionUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);

                try {
                    InputStream is = connection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);

                    //a stringbuffer is mutable a string not
                    StringBuffer buffer = new StringBuffer();

                    String line = "";

                    while ((line = br.readLine()) != null) {
                        buffer.append(line);
                    }

                    //versionsOnServer[i] = Integer.parseInt(br.readLine());

                    JSONArray parentArray = new JSONArray(buffer.toString());
                    package_Versions = new String[2][];
                    package_Versions[0] = new String[parentArray.length()];
                    package_Versions[1] = new String[parentArray.length()];

                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);

                        package_Versions[0][i] = finalObject.getString("packagename");
                        package_Versions[1][i] = finalObject.getString("versioncode");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (package_Versions != null) {

                for (int i = 0; i < package_Versions.length; i++) {
                    try {
                        PackageInfo pinfo = MainActivity.activity.getPackageManager().getPackageInfo(package_Versions[0][i], 0);
                        int actualversionCode = pinfo.versionCode;
                        int serverversionCode = Integer.parseInt(package_Versions[1][i]);

                        if (serverversionCode > actualversionCode) {
                            Toast.makeText(MainActivity.activity, "new version of " + package_Versions[0][i] + " avaible", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.apkmonk.com/download/3_mybusinesscard.amiran.de.mybusinesscard_2016-06-27.apk/"));
                           // MainActivity.activity.startActivity(intent);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
