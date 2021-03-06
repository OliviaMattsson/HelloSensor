package com.example.hellosensor;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Display;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class BallActivity extends AppCompatActivity implements SensorEventListener{
    Vibrator vibe;
    VibrationEffect effect;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private long lastUpdate;
    Display display;
    ImageView mDrawable;
    public static int x = 0;
    public static int y = 0;
    public static int z = 0;
    private int maxX;
    private int maxY;
    private int maxZ = 400;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ball);
        mDrawable = findViewById(R.id.ballImg);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lastUpdate = System.currentTimeMillis();
        display = getWindowManager().getDefaultDisplay();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if(vibe==null || !vibe.hasVibrator()){
            noSensorsAlert();
        } else{
            effect = VibrationEffect.createOneShot(100, 40);
        }
        calculateScreenSize();
    }

    private void noSensorsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Your device doesn't support the Ball Activity.")
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer,
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            x -= (int) event.values[0];
            y += (int) event.values[1];
            z += (int) event.values[2];

            if(x > maxX){
                x = maxX;
                vibrate();
            } else if (x < 0){
                x = 0;
                vibrate();
            }
            if(y > maxY){
                y = maxY;
                vibrate();
            } else if(y < 0){
                y = 0;
                vibrate();
            }
            if(z > maxZ){
                z = maxZ;
            } else if (z < 10){
                z = 10;
            }
            mDrawable.setY(y);
            mDrawable.setX(x);
            mDrawable.getLayoutParams().height = z;
            mDrawable.getLayoutParams().width = z;
            mDrawable.requestLayout();
            calculateScreenSize();


        }
    }

    private void vibrate() {
            if(vibe.hasVibrator()) {
                try{
                    vibe.vibrate(effect);
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
    }

    private void calculateScreenSize() {
        Point size = new Point();
        display.getSize(size);
        maxX = size.x - z ;
        maxY = size.y - 240 - z;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}