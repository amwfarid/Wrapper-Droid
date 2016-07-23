package com.fariddev.wrapperdroid.sensors;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.fariddev.wrapperdroid.activity.WrapperActivity;

/**
 * Created by Ahmed on 3/6/16.
 */
public class GPS implements LocationListener {

    private LocationManager locationManager;
    public double latitide;
    public double longitude;

    public GPS(WrapperActivity activity, int delay) {

        if (activity.getLocationManager() == null)
            activity.setLocationManager((LocationManager) activity.getSystemService(Context.LOCATION_SERVICE));

        locationManager = activity.getLocationManager();

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, delay, 10, this);
        } catch(SecurityException e) {
            Log.i("GPS", "access denied");
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        latitide = location.getLatitude();
        longitude = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("onProviderEnabled", "Provider enabled");
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("onProviderDisabled", "Provider disabled");
    }
}
