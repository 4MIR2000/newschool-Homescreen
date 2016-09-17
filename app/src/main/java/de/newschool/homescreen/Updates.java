package de.newschool.homescreen;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.webkit.URLUtil;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 01.09.2016.
 */
public class Updates {
    private final String VERSIONSURL = "http://sirius.ddnss.de:2000/student/getVersions";
    private String[][] package_Versions;
    private String[] apkUrls = {};

    private int actualAppPosition;
    ProgressDialog progressDialog;

    List<AlertDialog> alertDialogs;

    public void checkForUpdates() {

        new GetVersionsFromServer().execute();

    }

    public void removeOldAlertDialogs(){
        if(alertDialogs != null) {
            for (AlertDialog alertDialog : alertDialogs) {
                alertDialog.cancel();
            }
        }
    }

    private class GetVersionsFromServer extends AsyncTask<String, Void, String> {
        String title;

        @Override
        protected String doInBackground(String... params) {
            try {
                URL url = new URL(VERSIONSURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);

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
                    package_Versions = new String[3][];
                    package_Versions[0] = new String[parentArray.length()];
                    package_Versions[1] = new String[parentArray.length()];
                    package_Versions[2] = new String[parentArray.length()];

                    alertDialogs = new ArrayList<>();
                    for (int i = 0; i < parentArray.length(); i++) {
                        JSONObject finalObject = parentArray.getJSONObject(i);

                        package_Versions[0][i] = finalObject.getString("packagename");
                        package_Versions[1][i] = finalObject.getString("versioncode");
                        package_Versions[2][i] = finalObject.getString("apk");
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


//            Toast.makeText(MainActivity.activity,package_Versions[0].length,Toast.LENGTH_SHORT).show();
            if (package_Versions != null) {

                    checkForUpdateOrNewApp(actualAppPosition);
            }
        }
    }


    private void checkForUpdateOrNewApp(int position){

        try {
            PackageInfo pinfo = MainActivity.getContext().getPackageManager().getPackageInfo(package_Versions[0][position], 0);
            int actualversionCode = pinfo.versionCode;
            int serverversionCode = Integer.parseInt(package_Versions[1][position]);
            String apkUrl = package_Versions[2][position];

            if (serverversionCode > actualversionCode) {
                //Toast.makeText(MainActivity.activity, "new version of " + package_Versions[0][i] + " avaible", Toast.LENGTH_SHORT).show();

                // showUpdateDialog(apkUrl);
                // MainActivity.activity.startActivity(intent);
                showUpdateDialog(apkUrl);
                return;

            }
        } catch (PackageManager.NameNotFoundException e) {
            //Toast.makeText(MainActivity.activity, "new App " + package_Versions[0][i] + " avaible", Toast.LENGTH_SHORT).show();

            String apkUrl = package_Versions[2][position];
            showNewAppDialog(apkUrl);
            //showNewAppDialog(apkUrl);
            return;
        }

        //if there is no new update or app to download on this position than go to the next app position

        if(actualAppPosition<package_Versions[0].length-1) {
            actualAppPosition++;
            checkForUpdateOrNewApp(actualAppPosition);
        }

    }


    private void showUpdateDialog( final String apkUrl){

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.getContext())
            .setTitle(MainActivity.getContext().getString(R.string.update_title))
            .setMessage(MainActivity.getContext().getString(R.string.update_message))
            .setIcon(R.drawable.newschool)
            .setCancelable(false)
            .setPositiveButton(MainActivity.getContext().getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                progressDialog = ProgressDialog.show(MainActivity.getContext(),"",MainActivity.getContext().getString(R.string.downloading_progressBar), true, false);
                downloadInBackground(apkUrl,true);



            }
        })
            .setNegativeButton(MainActivity.getContext().getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        })

            .create();
        alertDialog.show();

        alertDialogs.add(alertDialog);


    }

    private void showNewAppDialog(final String apkUrl){

        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.getContext())
                .setTitle(MainActivity.getContext().getString(R.string.newAppAvaible_title))
                .setMessage(MainActivity.getContext().getString(R.string.newAppAvaible_message))
                .setIcon(R.drawable.newschool)
                .setCancelable(false)
                .setPositiveButton(MainActivity.getContext().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        progressDialog = ProgressDialog.show(MainActivity.getContext(),"",MainActivity.getContext().getString(R.string.downloading_progressBar), true, false);
                        downloadInBackground(apkUrl,false);



                    }
                })

                .setNegativeButton(MainActivity.getContext().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create();



        alertDialog.show();
        alertDialogs.add(alertDialog);
    }


    private void downloadInBackground(final String apkUrl, final boolean update){

        String destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/";
        String filename = "";

        filename = URLUtil.guessFileName(apkUrl,null, MimeTypeMap.getFileExtensionFromUrl(apkUrl));
        destination+=filename;
        File file = new File(destination);
        if(file.exists()){
            file.delete();
        }

        final Uri uri = Uri.parse("file://" + destination);


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(apkUrl));
        request.setTitle(filename);
        request.setDestinationUri(uri);
        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        final DownloadManager manager = (DownloadManager)MainActivity.getContext().getSystemService(Context.DOWNLOAD_SERVICE);

        final long downloadId = manager.enqueue(request);


        BroadcastReceiver onDonwloadComplete= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(update){
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.setDataAndType(uri,manager.getMimeTypeForDownloadedFile(downloadId));
                    MainActivity.getContext().startActivity(install);


                }else{

                    Intent install = new Intent(Intent.ACTION_VIEW);
                    install.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    install.setDataAndType(uri,manager.getMimeTypeForDownloadedFile(downloadId));
                    MainActivity.getContext().startActivity(install);

                }

                progressDialog.dismiss();
                if(actualAppPosition<package_Versions[0].length-1){
                    actualAppPosition++;
                    checkForUpdateOrNewApp(actualAppPosition);
                }
                context.unregisterReceiver(this);



            }

        };

        MainActivity.getContext().registerReceiver(onDonwloadComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

    }


}
