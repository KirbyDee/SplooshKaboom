package com.kirbydee.splooshkaboom.model.grid.state;

import com.kirbydee.splooshkaboom.view.layoutviews.grid.state.BombStateGridView;

public class BombStateGrid {

    private int bombIndex;

    public BombStateGrid(int bombIndex) {
        this.bombIndex = bombIndex;
    }

    public boolean isCorrectBomb(BombStateGridView view) {
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
