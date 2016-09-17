package de.newschool.homescreen;

import android.os.Build;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ASUS on 02.09.2016.
 */
public class Files {

    private static final int VERSION_NR =2;

    public static void makeFiles(){
        File root = new File(Environment.getExternalStorageDirectory(),".NewSchool");


        if(!root.exists())
            root.mkdirs();

        if(VERSION_NR > getVersion()) {

            makeVersiontxt();
            makeUserFiles();
            makeSubjectFiles();
            makeTeachersFiles();
            makeTimetableFiles();

        }


    }

    private static void makeVersiontxt() {
            try {

                File root = new File(Environment.getExternalStorageDirectory() + File.separator + ".NewSchool");

                File version = new File(root, "v.txt");

                FileWriter versionWriter = new FileWriter(version);
                versionWriter.append(Integer.toString(VERSION_NR));
                versionWriter.flush();
                versionWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    private static int getVersion() {
        File root = new File(Environment.getExternalStorageDirectory() + File.separator + ".NewSchool" + File.separator + "v.txt");

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;


            try {
                fis = new FileInputStream(root);
                isr = new InputStreamReader(fis);
                br = new BufferedReader(isr);

                return Integer.parseInt(br.readLine());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return -1;

    }
    private static void makeUserFiles(){
        try {
        File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Benutzer");

            if(!root.exists())
            root.mkdirs();

            File username = new File(root, "bn.txt");


            File schoolClass = new File(root, "k.txt");
            File religion = new File(root,"r.txt");

            if(!username.exists()) {
                FileWriter usernameWriter = new FileWriter(username);
                usernameWriter.append("");
                usernameWriter.flush();
                usernameWriter.close();
            }


                FileWriter schoolClassWriter = new FileWriter(schoolClass);
                schoolClassWriter.append("9c");
                schoolClassWriter.flush();
                schoolClassWriter.close();


            if(!religion.exists()) {

                FileWriter religionWriter = new FileWriter(religion);
                religionWriter.append("");
                religionWriter.flush();
                religionWriter.close();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void makeSubjectFiles(){
        final CharSequence SUBJECTS = "Deutsch;Informatik;Mathe;Physik;Physik Üb.;Religion;Musik;Wirtschaft;Kunst;Chemie;Chemie Üb.;Biologie;Englisch;Französisch;Geschichte;Sport";
        try{
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Faecher");

                root.mkdirs();

                File subjectsFile = new File(root, "f.txt");


                FileWriter subjectsWriter = new FileWriter(subjectsFile);
                subjectsWriter.append(SUBJECTS);
                subjectsWriter.flush();
                subjectsWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeTeachersFiles(){
        final CharSequence TEACHERS = "Standhaft;Klein;Gutwald;Leitgeb;Suske;Bratu;Krommer;R. Egger;Schleemilch;Opitz;Dukorn;Spanner;Herz;Göldner;Vertretung";
        try{
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Lehrer");

            root.mkdirs();

            File teachersFile = new File(root, "l.txt");



                FileWriter subjectsWriter = new FileWriter(teachersFile);
                subjectsWriter.append(TEACHERS);
                subjectsWriter.flush();
                subjectsWriter.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeTimetableFiles(){
        final CharSequence TIMETABLE = "Montag*Biologie,P5,7:50-8:35;Französisch,P5,8:35-9:20;Deutsch,P5,9:40-10:25;Physik,F07,10:25-11:10;Mathe,P5,11:30-12:15;Geschichte,P5,12:15-13:00;MITTAGSPAUSE,,13:00-14:00;Kunst,F17,14:00-14:45;Englisch,P5,14:45-15:30;\n" +
                "Dienstag*Wirtschaft,P5,7:50-8:35;Englisch,P5,8:35-9:20;Chemie Üb.,F11,9:40-10:25;Chemie Üb.,F11,10:25-11:10;Musik,T06,11:30-12:15;Informatik,K24,12:15-13:00;\n" +
                "Mittwoch*Deutsch,P5,7:50-8:35;Chemie,F14,8:35-9:20;Sport,THS,9:40-10:25;Sport,THS,10:25-11:10;Kunst,K27,11:30-12:15;Mathe,P5,12:15-13:00;MITTAGSPAUSE,,13:00-14:00;Physik,F07,14:00-14:45;Geschichte,P5,14:45-15:30;\n" +
                "Donnerstag*Englisch,P5,7:50-8:35;Deutsch,P5,8:35-9:20;Deutsch,P5,9:40-10:25;Mathe,P5,10:25-11:10;Informatik,K24,11:30-12:15;Französisch,P5,12:15-13:00;\n" +
                "Freitag*Wirtschaft,P5,7:50-8:35;Französisch,P5,8:35-9:20;Kunst,K27,9:40-10:25;Mathe,P5,10:25-11:10;Chemie,F13,11:30-12:15;Biologie,P5,12:15-13:00;\n";

        try{
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Stundenplan");

            root.mkdirs();

            File timetableFile = new File(root, "st.txt");
            FileWriter timetableWriter = new FileWriter(timetableFile);
            timetableWriter.append(TIMETABLE);
            timetableWriter.flush();
            timetableWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
