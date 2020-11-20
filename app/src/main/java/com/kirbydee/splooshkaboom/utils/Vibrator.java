package com.kirbydee.splooshkaboom.utils;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;

import static com.kirbydee.splooshkaboom.utils.Consts.VIBRATE_MILLIS;

public class Vibrator {

    private final Context context;

    public Vibrator(Context context) {
        this.context = context;
    }

    public void vibrate() {
        android.os.Vibrator v = (android.os.Vibrator) this.context.getSystemService(Context.VIBRATOR_SERVICE);

        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(VIBRATE_MILLIS, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            v.vibrate(VIBRATE_MILLIS);
        }
    }
}
