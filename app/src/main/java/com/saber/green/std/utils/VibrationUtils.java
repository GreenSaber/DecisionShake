package com.saber.green.std.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.appcompat.app.AppCompatActivity;

public class VibrationUtils {

    private AppCompatActivity activity;

    public VibrationUtils(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void vibrate() {
        long[] pattern = new long[]{0, 300, 150, 300};
        Vibrator vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                vibrator.vibrate(pattern, -1);
            }
        }

    }
}
