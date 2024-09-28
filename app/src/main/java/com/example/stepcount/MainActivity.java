package com.example.stepcount;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    public boolean Active = false;
    SensorManager sensorManager;
    int count = 0;
    TextView tvResult;
    Button btnPause;
    long LastTimeUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);
        btnPause = findViewById(R.id.btnPause);
        tvResult.setText(String.valueOf(count));
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorManager.registerListener((SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        LastTimeUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onResume(){
        super.onResume();

        sensorManager.registerListener((SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        LastTimeUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener((SensorEventListener) this);
    }

    public void onStop(View view){
        Active = !Active;
        if(Active) btnPause.setText("ПАУЗА");
        else btnPause.setText("ВОЗОБНОВИТЬ");
    }

    public void onSensorChanged(SensorEvent sensorEvent){
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float X = sensorEvent.values[0];
            float Y = sensorEvent.values[1];
            float Z = sensorEvent.values[2];

            float AccelarationSquareRoot =  (X*X+Y*Y+Z*Z)
                    / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
            long ActualTimeUpdate = System.currentTimeMillis();

            if(AccelarationSquareRoot >= 2){
                if(ActualTimeUpdate - LastTimeUpdate < 200) return;;
                if(!Active) return;
                LastTimeUpdate = ActualTimeUpdate;
                count++;
                tvResult.setText(String.valueOf(count));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}