package de.newschool.homescreen;

import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ASUS on 02.09.2016.
 */
public class Files {


    public static void makeFiles(){
        File root = new File(Environment.getExternalStorageDirectory(),".NewSchool");
        if(!root.exists()){
            root.mkdirs();
        }


        makeUserFiles();
        makeSubjectFiles();
        makeTeachersFiles();
        makeTimetableFiles();


    }

    private static void makeUserFiles(){
        try {
        File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Benutzer");
        if(!root.exists()) {
            root.mkdirs();

            File username = new File(root, "bn.txt");
            File schoolClass = new File(root, "k.txt");

            FileWriter usernameWriter = new FileWriter(username);
            usernameWriter.append("Amir Taghizadegan");
            usernameWriter.flush();
            usernameWriter.close();

            FileWriter schoolClassWriter = new FileWriter(schoolClass);
            schoolClassWriter.append("10b");
            schoolClassWriter.flush();
            schoolClassWriter.close();
        }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void makeSubjectFiles(){
        final CharSequence SUBJECTS = "Deutsch;Informatik;Mathe;Physik;Religion;Musik;Wirtschaft;Kunst;Chemie;Biologie;Geografie;Englisch;Französisch;Geschichte;Sport";
        try{
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Faecher");
            if(!root.exists()) {
                root.mkdirs();

                File subjectsFile = new File(root, "f.txt");


                FileWriter subjectsWriter = new FileWriter(subjectsFile);
                subjectsWriter.append(SUBJECTS);
                subjectsWriter.flush();
                subjectsWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeTeachersFiles(){
        final CharSequence TEACHERS = "Standhaft;Leitgeb;Suske;Bratu;Neuerer;Egger A.;Schleemilch;Opitz;Dukorn;Spanner;Herz;Göldner;Egger R.";
        try{
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Lehrer");
            if(!root.exists()){
                root.mkdirs();

                File TeachersFile = new File(root, "l.txt");


                FileWriter subjectsWriter = new FileWriter(TeachersFile);
                subjectsWriter.append(TEACHERS);
                subjectsWriter.flush();
                subjectsWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void makeTimetableFiles(){
        final CharSequence TIMETABLE = "Montag*Englisch,K27,7:50-8:35;Französisch,K27,8:35-9:20;Mathe,K27,9:40-10:25;Deutsch,K06,10:25-11:10;Sport,,11:30-12:15;Sport,,12:15-13:00;\n" +
                "Dienstag*Physik,K14,7:50-8:35;Geschichte,K27,8:35-9:20;Reli,K30,9:40-10:25;Musik,P02,10:25-11:10;Englisch,K27,11:30-12:15;PAUSE,,12:15-13:20;Informatik,K02,13:15-14:00;Wirtschaft,K27,14:00-14:45;Deutsch,K27,14:45-15:30;\n" +
                "Mittwoch*Chemie,K15,7:50-8:35;Englisch,K27,8:35-9:20;Biologie,K16,9:40-10:25;Französisch,K27,10:25-11:10;Physik,K14,11:30-12:15;Deutsch,K27,12:15-13:00;PAUSE,,13:00-14:00;Geschichte,K27,14:00-14:45;Mathe,K27,14:45-15:30;\n" +
                "Donnerstag*Musik,P02,7:50-8:35;Informatik,K02,8:35-9:20;Deutsch,K27,9:40-10:25;Französisch,K27,10:25-11:10;Mathe,K27,11:30-12:15;Chemie,K15,12:15-13:00;\n" +
                "Freitag*Religion,K30,7:50-8:35;Mathe,K27,8:35-9:20;Biologie,K27,9:40-10:25;Wirtschaft,K06,10:25-11:10;Chemie,K15/K14,11:30-12:15;Chemie,K15/K14,12:15-13:00;\n";

        try{
            File root = new File(Environment.getExternalStorageDirectory()+File.separator+".NewSchool","Stundenplan");
            if(!root.exists()) {
                root.mkdirs();

                File timetableFile = new File(root, "st.txt");
                FileWriter timetableWriter = new FileWriter(timetableFile);
                timetableWriter.append(TIMETABLE);
                timetableWriter.flush();
                timetableWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
