package com.fariddev.wrapperdroid.FileIO;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Ahmed on 3/11/16.
 */
public class FileIO {

    private File file;
    private boolean append;
    private FileOutputStream oStream;
    private FileInputStream iStream;

    public FileIO(String path, String fileName, boolean append){

        this.append = append;
        file = new File(path, fileName);

    }

    public void write(String payload){
        try {
            oStream = new FileOutputStream(file,append);
            oStream.write(payload.getBytes());
            oStream.close();
        } catch (IOException e) {
            Log.e("FileIO error", e.getMessage());
        }
    }

    public String read(){


        try {
            int length = (int) file.length();
            byte[] payload = new byte[length];
            iStream = new FileInputStream(file);
            iStream.read(payload);
            iStream.close();
            return new String(payload);
        } catch (IOException e) {
            Log.e("FileIO error", e.getMessage());
        }

        return null;

    }

}
