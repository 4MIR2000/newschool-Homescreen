package de.newschool.homescreen;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class SerializationTools {
    public static void serializeData(AppSerializableData obj) {
        FileOutputStream fos;
        try {
            fos = MainActivity.activity.openFileOutput("data", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AppSerializableData loadSerializedData() {
        ObjectInputStream inputStream = null;

        try {
            inputStream = new ObjectInputStream(MainActivity.activity.openFileInput("data"));
            Object obj = inputStream.readObject();
            if (obj instanceof AppSerializableData) {
                return (AppSerializableData) obj;
            } else {
                return null;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
