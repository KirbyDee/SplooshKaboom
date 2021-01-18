package com.kirbydee.splooshkaboom.controller;

import android.os.Handler;
import android.util.Log;

public class HandlerController {

    private static final String TAG = HandlerController.class.getName();

    private final Handler handler;

    public HandlerController(Handler handler) {
        this.handler = handler;
    }

    public CallbackHandler postDelayed(final Runnable runnable, long delay) {
        Log.i(TAG, "postDelay (" + runnable + ", " + delay + "ms)");
        this.handler.postDelayed(runnable, delay);
        return new CallbackHandler(this, runnable);
    }

    public void remove(final Runnable runnable) {
        Log.i(TAG, "remove (" + runnable + ")");
        this.handler.removeCallbacks(runnable);
    }

    public void clear() {
        Log.i(TAG, "clear");
        this.handler.removeCallbacksAndMessages(null);
    }

    public static class CallbackHandler {

        private final HandlerController handler;

        private final Runnable runnable;

        public CallbackHandler(HandlerController handler, Runnable runnable) {
            this.handler = handler;
            this.runnable = runnable;
        }

        public void cancel() {
            this.handler.remove(this.runnable);
        }
    }
}
