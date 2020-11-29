package com.kirbydee.splooshkaboom.controller;

import android.os.Handler;
import android.util.Log;

public class HandlerController {

    private static final String TAG = HandlerController.class.getName();

    private final Handler handler;

    public HandlerController(Handler handler) {
        this.handler = handler;
    }

    public void postDelayed(final Runnable runnable, long delay) {
        Log.i(TAG, "postDelay (" + runnable + ", " + delay + "ms)");
        this.handler.postDelayed(runnable, delay);
    }

    public void clear() {
        Log.i(TAG, "clear");
        this.handler.removeCallbacksAndMessages(null);
    }
}