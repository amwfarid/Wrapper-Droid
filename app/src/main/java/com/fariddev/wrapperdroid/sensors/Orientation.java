package com.fariddev.wrapperdroid.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.fariddev.wrapperdroid.activity.WrapperActivity;

/**
 * Created by ahmedfarid on 8/13/16.
 */
public class Orientation extends AbstractSensor{

    // Raw readings in Yaw Pitch Roll respectively
    public float [] raw = new float[3];

    // Filtered Yaw Pitch Roll respectively
    public float [] filtered = new float[3];

    // Low pass filtering constant
    static final float ALPHA = 0.25f;

    public Orientation(WrapperActivity activity) {
        super(activity, Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float [] tmpMatrix = new float[9];

        SensorManager.getRotationMatrixFromVector(tmpMatrix, event.values);

        SensorManager.getOrientation(tmpMatrix, raw);

        raw = event.values;

        if(filtered != null){
            filtered[0] = filtered[0] + ALPHA * (raw[0] - filtered[0]);
            filtered[1] = filtered[1] + ALPHA * (raw[1] - filtered[1]);
            filtered[2] = filtered[2] + ALPHA * (raw[2] - filtered[2]);
        }
        else {
            filtered = raw;
        }
    }

    private void toDegrees(float[] vector){
        for (int i = 0; i < vector.length; i++){
            vector[i] = Math.round(Math.toDegrees(vector[i]));
        }
    }


}
