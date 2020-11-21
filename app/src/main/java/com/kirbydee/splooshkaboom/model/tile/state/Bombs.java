package com.kirbydee.splooshkaboom.model.tile.state;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.kirbydee.splooshkaboom.utils.Consts.BOMB_COUNT;

public class Bombs {

    private static final String TAG = Bombs.class.getName();

    private List<Bomb> bombs;

    public Bombs() {
        init();
    }

    private void init() {
        Log.i(TAG, "init");
        this.bombs = new ArrayList<>(BOMB_COUNT);
        IntStream.rangeClosed(0, BOMB_COUNT)
                .mapToObj(Bomb::new)
                .forEach(this.bombs::add);
    }

    public Optional<Bomb> findBomb(int bombIndex) {
        Log.i(TAG, "getBomb (" + bombIndex + ")");
        return this.bombs.stream()
                .filter(b -> b.getBombIndex() == bombIndex)
                .findAny();
    }

    @Override
    public String toString() {
        return "Bombs{" +
                "bombs=" + bombs +
                '}';
    }
}
