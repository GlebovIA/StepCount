package com.example.stepcount;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public boolean Active = false;
    SensorManager sensorManager;
    int count = 0;
    TextView tvResult;
    Button btnPause;
    long lastTimeUpdate;

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
        lastTimeUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onResume(){
        super.onResume();

        sensorManager.registerListener((SensorEventListener) this,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST);
        lastTimeUpdate = System.currentTimeMillis();
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
}