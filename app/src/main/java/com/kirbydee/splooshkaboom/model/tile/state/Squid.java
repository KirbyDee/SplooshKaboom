package com.kirbydee.splooshkaboom.model.tile.state;

import com.kirbydee.splooshkaboom.view.layoutviews.tile.state.SquidView;

public class Squid {

    private int squidSize;

    public Squid(int squidSize) {
        this.squidSize = squidSize;
    }

    public boolean isCorrectSquid(SquidView view) {
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
