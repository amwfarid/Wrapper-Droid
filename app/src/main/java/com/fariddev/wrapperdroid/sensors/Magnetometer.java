package com.fariddev.wrapperdroid.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;

import com.fariddev.wrapperdroid.activity.WrapperActivity;

public class Magnetometer extends AbstractSensor {

	// Raw readings in X Y Z respectively
	public float [] raw = new float[3];

	// Filtered readings in X Y Z respectively
	public float [] filtered = new float[3];

	// Low pass filtering constant
	protected float ALPHA = 0.25f;

    public Magnetometer(WrapperActivity activity) { super(activity,Sensor.TYPE_MAGNETIC_FIELD); }

	public Magnetometer(WrapperActivity activity, float filteringConstant) {
		super(activity,Sensor.TYPE_MAGNETIC_FIELD);
		ALPHA = filteringConstant;
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
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

}
