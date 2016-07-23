package com.fariddev.wrapperdroid.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.fariddev.wrapperdroid.activity.WrapperActivity;

public class Magnetometer implements SensorEventListener {

	private SensorManager sensorManager;
	public double x,y,z;

    public Magnetometer(WrapperActivity activity)
	{
		if(activity.getSensorManager() == null)
			activity.setSensorManager((SensorManager) activity.getSystemService(Context.SENSOR_SERVICE));
         
         Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
         
         sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST);
         
         
    }
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	@Override
	public void onSensorChanged(SensorEvent event) {
		x = event.values[0];
		y = event.values[1];
		z = event.values[2];
		
	}
	
	public void stop(){
		sensorManager.unregisterListener(this);
	}

}
