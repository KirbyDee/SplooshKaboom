package com.kirbydee.splooshkaboom.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.counter.Rupees;

import java.util.function.Function;

import static com.kirbydee.splooshkaboom.utils.Consts.*;

public class Storage {

    private final Context context;

    public Storage(Context context) {
        this.context = context;
    }

    public void storeHasTreasureMap(boolean hasTreasureMap) {
        storeBoolean(KEY_HAS_TREASURE_MAP, hasTreasureMap);
    }

    public void storeHasHeartPiece(boolean hasHeartPiece) {
        storeBoolean(KEY_HAS_HEART_PIECE, hasHeartPiece);
    }

    public void storeRupees(Rupees rupees) {
        storeInt(KEY_RUPEES, rupees.get());
    }

    public void storeRecord(Counter record) {
        storeInt(KEY_RECORD, record.get());
    }

    private void storeInt(String key, int value) {
        store(e -> e.putInt(key, value));
    }

    private void storeBoolean(String key, boolean value) {
        store(e -> e.putBoolean(key, value));
    }

    private void store(Function<SharedPreferences.Editor, SharedPreferences.Editor> function) {
        SharedPreferences pref = this.context.getSharedPreferences(PREFERENCE, 0);
        SharedPreferences.Editor editor = pref.edit();
        function.apply(editor)
                .apply();
    }

    public boolean hasHeartPiece() {
        return get(s -> s.getBoolean(KEY_HAS_HEART_PIECE, false));
    }

    public boolean hasTreasureMap() {
        return get(s -> s.getBoolean(KEY_HAS_TREASURE_MAP, false));
    }

    public Rupees getRupees() {
        return get(s -> Rupees.of(s.getInt(KEY_RUPEES, 0)));
    }

    public Counter getRecord() {
        return Counter.of(get(s -> s.getInt(KEY_RECORD, -1)));
    }

    private <T> T get(Function<SharedPreferences, T> function) {
        SharedPreferences pref = this.context.getSharedPreferences(PREFERENCE, 0);
        return function.apply(pref);
    }
}
