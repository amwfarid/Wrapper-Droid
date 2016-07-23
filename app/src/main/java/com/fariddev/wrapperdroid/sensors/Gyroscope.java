package com.fariddev.wrapperdroid.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.fariddev.wrapperdroid.activity.WrapperActivity;

public class Gyroscope extends SensorWrapper {

	private SensorManager sensorManager;
	public double x,y,z;

    public Gyroscope(WrapperActivity activity) {
		super(activity,Sensor.TYPE_GYROSCOPE);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		x = event.values[0];
		y = event.values[1];
		z = event.values[2];
	}

}
