package com.kirbydee.splooshkaboom.model.counter;

import android.util.Log;

import static com.kirbydee.splooshkaboom.utils.Consts.MAX_TURNS;

public class Counter {

    private static final String TAG = Counter.class.getName();

    private int count;

    public Counter(int startCount) {
        this.count = startCount;
    }

    public void increase() {
        if (this.count < MAX_TURNS) {
            this.count++;
            Log.i(TAG, "count increased: " + this.count);
        }
        else {
            Log.w(TAG, "cannot increase count, already at max: " + this.count);
        }
    }

    public void decrease() {
        if (this.count > 0) {
            this.count--;
            Log.i(TAG, "count decreased: " + this.count);
        }
        else {
            Log.w(TAG, "cannot decrease count, already at min: " + this.count);
        }
    }

    public int get() {
        return count;
    }

    public void set(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Counter{" +
                "count=" + count +
                '}';
    }
}
