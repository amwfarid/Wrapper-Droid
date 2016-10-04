package com.fariddev.wrapperdroid.samples;

import android.util.Log;
import android.widget.TextView;

import com.fariddev.wrapperdroid.FileIO.FileIO;
import com.fariddev.wrapperdroid.R;
import com.fariddev.wrapperdroid.activity.WrapperActivity;
import com.fariddev.wrapperdroid.sensors.Accelerometer;
import com.fariddev.wrapperdroid.sensors.GPS;
import com.fariddev.wrapperdroid.sensors.Gyroscope;
import com.fariddev.wrapperdroid.sensors.Magnetometer;
import com.fariddev.wrapperdroid.sensors.Orientation;

public class SampleActivity extends WrapperActivity {

    // Declare objects

    Accelerometer a;
    Gyroscope g;
    Magnetometer m;
    GPS gps;
    Orientation o;
    FileIO f;

    // Declare text views

    TextView ax;
    TextView ay;
    TextView az;
    TextView gx;
    TextView gy;
    TextView gz;

    // UI initialization function

    @Override
    public void initUI() {

        setContentView(R.layout.activity_wrapper);

        ax = (TextView) findViewById(R.id.ax);
        ay = (TextView) findViewById(R.id.ay);
        az = (TextView) findViewById(R.id.az);
        gx = (TextView) findViewById(R.id.gx);
        gy = (TextView) findViewById(R.id.gy);
        gz = (TextView) findViewById(R.id.gz);

        // Use Log.d or Log.e to display output on logcat panel
        Log.d("Main", "UI init done");
    }

    // Object initialization function

    @Override
    public void setup() throws Exception {

        //setWorkInBackground(true);    // Uncomment if you'd like your app to work in the background

        a = new Accelerometer(this);

        g = new Gyroscope(this);

        m = new Magnetometer(this);

        gps = new GPS(this,1000);

        o = new Orientation(this);

        f = new FileIO("//sdcard","raw.csv",false);

        Log.d("Main", "Object init done");

    }

    // Loop function

    @Override
    public void loop() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ax.setText(Double.toString(a.raw[0]));
                ay.setText(Double.toString(a.raw[1]));
                az.setText(Double.toString(a.raw[2]));
                gx.setText(Double.toString(g.raw[0]));
                gy.setText(Double.toString(g.raw[1]));
                gz.setText(Double.toString(g.raw[2]));
            }
        });

        f.write(a.raw[0]+","+a.raw[1]+","+a.raw[2]+","+g.raw[0]+","+g.raw[1]+","+g.raw[2]+"\n");
        delay(500);

        Log.d("Orientation",o.raw[0]+"-"+o.raw[1]+"-"+o.raw[2]);

    }

    // Resource clean up

    @Override
    public void close(){
        a.stop();
        g.stop();
    }

}
