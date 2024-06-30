package com.itaybs.carcrash.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.itaybs.carcrash.Interfaces.MoveCallback;

public class MoveDetector {
    private final SensorManager sensorManager;
    private final Sensor sensor;
    private SensorEventListener sensorEventListener;
    private MoveCallback moveCallback;
    private long timestamp = 0L;

    private void calculateMove(float x, float y, float z) {
        if (System.currentTimeMillis() - timestamp > 400) {
            timestamp = System.currentTimeMillis();
            if (Math.abs(x) > 3.0) {
                if (moveCallback != null) {
                    moveCallback.moveX(x);
                }
            }
            if (z < -3 || z > 8) {
                if (moveCallback != null) {
                    moveCallback.moveZ(z);
                }
            }
        }
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                calculateMove(x, y, z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }
    public MoveDetector(Context context, MoveCallback moveCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.moveCallback = moveCallback;
        initEventListener();
    }

    public void start() {
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void stop() {
        sensorManager.unregisterListener(sensorEventListener);
    }
}
