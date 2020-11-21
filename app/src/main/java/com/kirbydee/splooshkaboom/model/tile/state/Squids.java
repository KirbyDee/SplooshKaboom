package com.kirbydee.splooshkaboom.model.tile.state;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.kirbydee.splooshkaboom.utils.Consts.SQUID_COUNT;

public class Squids {

    private static final String TAG = Squids.class.getName();

    private List<Squid> squids;

    public Squids() {
        init();
    }

    private void init() {
        Log.i(TAG, "init");
        this.squids = new ArrayList<>(SQUID_COUNT);
        IntStream.rangeClosed(0, SQUID_COUNT)
                .mapToObj(Squid::new)
                .forEach(this.squids::add);
    }

    public Optional<Squid> findSquid(int squidSize) {
        Log.i(TAG, "findSquid (" + squidSize + ")");
        return this.squids.stream()
                .filter(b -> b.getSquidSize() == squidSize)
                .findAny();
    }

    @Override
    public String toString() {
        return "Squids{" +
                "squids=" + squids +
                '}';
    }
}
