package com.kirbydee.splooshkaboom.model.tile.state;

import com.kirbydee.splooshkaboom.view.layoutviews.tile.state.BombView;

public class Bomb {

    private int bombIndex;

    public Bomb(int bombIndex) {
        this.bombIndex = bombIndex;
    }

    public boolean isCorrectBomb(BombView view) {
        return isCorrectBomb(view.getBombIndex());
    }

    public boolean isCorrectBomb(int bombIndex) {
        return bombIndex == this.bombIndex;
    }

    @Override
    public String toString() {
        return "BombStateGrid{" +
                "bombIndex=" + bombIndex +
                '}';
    }

    public int getBombIndex() {
        return bombIndex;
    }

    public void setBombIndex(int bombIndex) {
        this.bombIndex = bombIndex;
    }
}
