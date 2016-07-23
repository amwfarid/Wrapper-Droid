package com.fariddev.wrapperdroid.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fariddev.wrapperdroid.FileIO.FileIO;
import com.fariddev.wrapperdroid.R;
import com.fariddev.wrapperdroid.activity.WrapperActivity;
import com.fariddev.wrapperdroid.sensors.Accelerometer;
import com.fariddev.wrapperdroid.sensors.Gyroscope;

public class SampleActivity extends WrapperActivity {

    // Declare objects

    Accelerometer a;
    Gyroscope g;
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
    }

    // Object initialization function

    @Override
    public void setup() throws Exception {

        //setWorkInBackground(true);    // Uncomment if you'd like your app wo work in the background

        a = new Accelerometer(this);

        g = new Gyroscope(this);

        f = new FileIO("//sdcard","readings.csv",true);

    }

    // Loop function

    @Override
    public void loop() {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ax.setText(Double.toString(a.x));
                ay.setText(Double.toString(a.y));
                az.setText(Double.toString(a.z));
                gx.setText(Double.toString(g.x));
                gy.setText(Double.toString(g.y));
                gz.setText(Double.toString(g.z));
            }
        });

        f.write(a.x+","+a.y+","+a.z+","+g.x+","+g.y+","+g.z+"\n");
        delay(500);
    }

    // Resource clean up

    @Override
    public void close(){
        a.stop();
        g.stop();
    }

}
