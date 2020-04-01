package com.example.hellosensor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AccActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    TextView txt_x, txt_y, txt_z;
    boolean haveSensor = false;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);



        setContentView(R.layout.activity_acc);
        txt_x = (TextView) findViewById(R.id.acc_x);
        txt_y = (TextView) findViewById(R.id.acc_y);
        txt_z = (TextView) findViewById(R.id.acc_z);

        start();
    }

    private void openBallAcc() {
        Intent intent = new Intent(this, BallActivity.class);
        startActivity(intent);
    }

    private void start() {
            if ((mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null)) {
                noSensorsAlert();
            } else {
                mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                haveSensor = mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
            }
        btn = findViewById(R.id.ballbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBallAcc();
            }
        });
    }

    private void noSensorsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Accelerometer.")
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            double x = (double) Math.round(event.values[0]*100)/100;
            double y = (double) Math.round(event.values[1]*100)/100;
            double z = (double) Math.round(event.values[2]*100)/100;
            txt_x.setText("X: " + String.valueOf(x));
            txt_y.setText("Y: " + String.valueOf(y));
            txt_z.setText("Z: " + String.valueOf(z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
