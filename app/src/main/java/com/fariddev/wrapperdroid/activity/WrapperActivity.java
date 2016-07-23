package com.fariddev.wrapperdroid.activity;

import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Ahmed on 3/6/16.
 */
public class WrapperActivity extends AppCompatActivity {

    private boolean workInBackground = false;

    private SensorManager sensorManager;

    private LocationManager locationManager;

    public void initUI(){}

    public void setup() throws Exception {}

    public void loop() throws Exception {}

    private void asyncLoop() throws Exception {
        while(true)
            loop();
    }

    public void close() throws Exception {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();

        try {
            setup();
        } catch (Exception e) {
            e.printStackTrace();
        }

        new loopTask().execute(0);

    }

    public void delay(int milliseconds){
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class loopTask extends AsyncTask<Integer, Integer, Integer> {
        protected Integer doInBackground(Integer... n) {

            try {
                asyncLoop();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return 0;
        }

        protected void onProgressUpdate(Integer... n) {

        }

        protected void onPostExecute(String[] n) {

        }
    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            if(!workInBackground)
                setup();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onPause(){
        super.onPause();
        try {
            if(!workInBackground)
                close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean isWorkInBackground() {
        return workInBackground;
    }

    public void setWorkInBackground(boolean workInBackground) {
        this.workInBackground = workInBackground;
    }


    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public void setSensorManager(SensorManager sensorManager) {
        this.sensorManager = sensorManager;
    }

    public LocationManager getLocationManager() {
        return locationManager;
    }

    public void setLocationManager(LocationManager locationManager) {
        this.locationManager = locationManager;
    }
}
