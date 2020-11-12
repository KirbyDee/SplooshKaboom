package com.kirbydee.splooshkaboom.controller;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.kirbydee.splooshkaboom.model.count.Count;

import static com.kirbydee.splooshkaboom.utils.Consts.MAX_TURNS;

public class Counter {

    private static final String TAG = Counter.class.getName();

    private final ImageView counterLow;

    private final ImageView counterHigh;

    private int count;

    public Counter(ImageView counterLow, ImageView counterHigh) {
        this(counterLow, counterHigh, 0);
    }

    public Counter(ImageView counterLow, ImageView counterHigh, int startCount) {
        this.counterLow = counterLow;
        this.counterHigh = counterHigh;
        this.count = startCount;
    }

    public void increase(Context context) {
        if (this.count < MAX_TURNS) {
            this.count++;
            Log.i(TAG, "count increased: " + this.count);
            updateCounter(context);
        }
        else {
            Log.w(TAG, "cannot increase count, already at max: " + this.count);
        }
    }

    public void decrease(Context context) {
        if (this.count > 0) {
            this.count--;
            Log.i(TAG, "count decreased: " + this.count);
            updateCounter(context);
        }
        else {
            Log.w(TAG, "cannot decrease count, already at min: " + this.count);
        }
    }

    private void updateCounter(Context context) {
        Log.i(TAG, "update counter views: " + this.count);
        int high = this.count / 10;
        int low = this.count - 10 * high;

        Count highCount = Count.of(high);
        Count lowCount = Count.of(low);

        if (high != 0) {
            this.counterHigh.setBackgroundResource(highCount.getResId(context));
        }
        this.counterLow.setBackgroundResource(lowCount.getResId(context));
    }
}
