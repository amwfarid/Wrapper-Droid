package com.fariddev.wrapperdroid.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.fariddev.wrapperdroid.activity.WrapperActivity;

/**
 * Created by ahmedfarid on 7/23/16.
 */
public class SensorWrapper implements SensorEventListener {

    private SensorManager sensorManager;
    public float values[];

    public SensorWrapper(WrapperActivity activity, int TYPE) {

        if(activity.getSensorManager() == null)
            activity.setSensorManager((SensorManager) activity.getSystemService(Context.SENSOR_SERVICE));

        sensorManager = activity.getSensorManager();

        Sensor sensor = sensorManager.getDefaultSensor(TYPE);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}

    @Override
    public void onSensorChanged(SensorEvent event) {}

    public void stop(){
        sensorManager.unregisterListener(this);
    }
}
