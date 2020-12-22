package com.kirbydee.splooshkaboom.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.kirbydee.splooshkaboom.model.counter.Counter;
import com.kirbydee.splooshkaboom.model.counter.Rupees;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.kirbydee.splooshkaboom.utils.Consts.KEY_ITEMS_BOUGHT;
import static com.kirbydee.splooshkaboom.utils.Consts.KEY_RECORD;
import static com.kirbydee.splooshkaboom.utils.Consts.KEY_RUPEES;
import static com.kirbydee.splooshkaboom.utils.Consts.PREFERENCE;
import static com.kirbydee.splooshkaboom.utils.PredicateExt.not;

public class Storage {

    private final Context context;

    public Storage(Context context) {
        this.context = context;
    }

    public void storeBoughtItemIndexes(Set<Integer> itemSet) {
        String indices = itemSet
                .stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
        storeString(KEY_ITEMS_BOUGHT, indices);
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

    private void storeString(String key, String value) {
        store(e -> e.putString(key, value));
    }

    private void store(Function<SharedPreferences.Editor, SharedPreferences.Editor> function) {
        SharedPreferences pref = this.context.getSharedPreferences(PREFERENCE, 0);
        SharedPreferences.Editor editor = pref.edit();
        function.apply(editor)
                .apply();
    }

    public Set<Integer> getBoughtItemIndexes() {
        String indices = get(s -> s.getString(KEY_ITEMS_BOUGHT, ""));
        return Stream.of(indices.split(","))
                .filter(not(String::isEmpty))
                .map(Integer::parseInt)
                .collect(Collectors.toSet());
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
