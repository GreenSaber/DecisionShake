package com.saber.green.std.utils;

import android.hardware.SensorManager;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.seismic.ShakeDetector;

import static android.content.Context.SENSOR_SERVICE;

public class ShakeUtils {

    private SensorManager sensorManager;
    private ShakeDetector shakeDetector;

    public ShakeUtils(AppCompatActivity activity, ShakeDetector.Listener listener) {
        sensorManager = (SensorManager) activity.getSystemService(SENSOR_SERVICE);
        shakeDetector = new ShakeDetector(listener);
        shakeDetector.setSensitivity(ShakeDetector.SENSITIVITY_LIGHT);
    }

    public void stopHearShake() { shakeDetector.stop(); }

    public void startHearShake() {
        shakeDetector.start(sensorManager);
    }

}
