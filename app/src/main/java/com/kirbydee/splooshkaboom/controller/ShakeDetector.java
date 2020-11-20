package com.kirbydee.splooshkaboom.controller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import static com.kirbydee.splooshkaboom.utils.Consts.SHAKE_COUNT_RESET_TIME_MS;
import static com.kirbydee.splooshkaboom.utils.Consts.SHAKE_SLOP_TIME_MS;
import static com.kirbydee.splooshkaboom.utils.Consts.SHAKE_THRESHOLD_GRAVITY;

public class ShakeDetector implements SensorEventListener {

    private Listener listener;

    private long shakeTimestamp;

    private int shakeCount;

    public ShakeDetector(Context context) {
        if (context instanceof ShakeDetector.Listener) {
            this.listener = (ShakeDetector.Listener) context;
        }
    }

    public interface Listener {

        void onShake(int count);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (this.listener != null) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > SHAKE_THRESHOLD_GRAVITY) {
                final long now = System.currentTimeMillis();
                // ignore shake events too close to each other (500ms)
                if (this.shakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return;
                }

                // reset the shake count after 3 seconds of no shakes
                if (this.shakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    this.shakeCount = 0;
                }

                this.shakeTimestamp = now;
                this.shakeCount++;

                this.listener.onShake(this.shakeCount);
            }
        }
    }
}
