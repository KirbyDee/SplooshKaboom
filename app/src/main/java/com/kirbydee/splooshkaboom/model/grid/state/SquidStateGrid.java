package com.kirbydee.splooshkaboom.model.grid.state;

import com.kirbydee.splooshkaboom.view.layoutviews.grid.state.SquidStateGridView;

public class SquidStateGrid {

    private int squidSize;

    public SquidStateGrid(int squidSize) {
        this.squidSize = squidSize;
    }

    public boolean isCorrectSquid(SquidStateGridView view) {
        return isCorrectSquid(view.getSquidSize());
    }

    public boolean isCorrectSquid(int squidSize) {
        return squidSize == this.squidSize;
    }

    @Override
    public String toString() {
        return "SquidStateGrid{" +
                "squidSize=" + squidSize +
                '}';
    }

    public int getSquidSize() {
        return squidSize;
    }

    public void setSquidSize(int squidSize) {
        this.squidSize = squidSize;
    }
}
