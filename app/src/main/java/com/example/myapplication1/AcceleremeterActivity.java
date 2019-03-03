package com.example.myapplication1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AcceleremeterActivity extends Activity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor mSensor;
    private TextView tvX, tvY, tvZ;
    private Vibrator v;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accel);

        layout = (LinearLayout) findViewById(R.id.background);
        layout.setBackgroundColor(Color.BLUE);

        this.getResources().getColor(android.R.color.white);

        tvX = (TextView) findViewById(R.id.x);
        tvY = (TextView) findViewById(R.id.y);
        tvZ = (TextView) findViewById(R.id.z);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (mSensor != null)
            sensorManager.registerListener(AcceleremeterActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        else
            Toast.makeText(getApplicationContext(), "mSensor = Null", Toast.LENGTH_SHORT);

        tvX.setText("X-VALUES: ");
        tvY.setText("Y-VALUES: ");
        tvZ.setText("Z-VALUES: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this, mSensor);
        v.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (v!=null)
            v.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(AcceleremeterActivity.this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (v!=null)
         v.cancel();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        tvX.setText("X-VALUES: " + event.values[0]);
        tvY.setText("Y-VALUES: " + event.values[1]);
        tvZ.setText("Z-VALUES: " + event.values[2]);

        if (event.values[0] < 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        }
        if (event.values[1] < 0)
            changeBackgroundColor();
    }

    private void changeBackgroundColor() {
        layout.setBackgroundColor(Color.GRAY);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}
