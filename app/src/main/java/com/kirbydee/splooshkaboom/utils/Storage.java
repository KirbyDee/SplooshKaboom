package com.kirbydee.splooshkaboom.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kirbydee.splooshkaboom.model.counter.Counter;

import java.util.function.Function;

import static com.kirbydee.splooshkaboom.utils.Consts.KEY_RECORD;
import static com.kirbydee.splooshkaboom.utils.Consts.PREFERENCE;

public class Storage {

    private final Context context;

    public Storage(Context context) {
        this.context = context;
    }

    public void storeRecord(Counter record) {
        storeInt(KEY_RECORD, record.get());
    }

    private void storeInt(String key, int value) {
        store(e -> e.putInt(key, value));
    }

    private void store(Function<SharedPreferences.Editor, SharedPreferences.Editor> function) {
        SharedPreferences pref = this.context.getSharedPreferences(PREFERENCE, 0);
        SharedPreferences.Editor editor = pref.edit();
        function.apply(editor)
                .apply();
    }

    public Counter getRecord() {
        return Counter.of(get(s -> s.getInt(KEY_RECORD, -1)));
    }

    private <T> T get(Function<SharedPreferences, T> function) {
        SharedPreferences pref = this.context.getSharedPreferences(PREFERENCE, 0);
        return function.apply(pref);
    }
}
