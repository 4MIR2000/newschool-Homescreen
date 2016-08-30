package de.newschool.homescreen;

import android.os.Environment;
import android.renderscript.ScriptGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ASUS on 28.08.2016.
 */
public class UserInfo {

    public static String getUsername(){
        String path = Environment.getExternalStorageDirectory()+ File.separator+"NewSchool/Benutzer/bn.txt";
        File file = new File(path);

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try{
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            return br.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String getSchoolClass(){

        String path =  Environment.getExternalStorageDirectory()+ File.separator+"NewSchool/Benutzer/k.txt";
        File file = new File(path);

        FileInputStream fis= null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            return br.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
