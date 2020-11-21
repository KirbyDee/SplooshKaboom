package com.kirbydee.splooshkaboom.model.tile.state;

import com.kirbydee.splooshkaboom.view.layoutviews.tile.state.SquidView;

public class Squid {

    private int squidSize;

    private boolean isDetonated = false;

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
        return "Squid{" +
                "squidSize=" + squidSize +
                ", isDetonated=" + isDetonated +
                '}';
    }

    public Squid detonate() {
        this.isDetonated = true;
        return this;
    }

    public boolean isNotDetonated() {
        return !isDetonated;
    }

    public int getSquidSize() {
        return squidSize;
    }

    public void setSquidSize(int squidSize) {
        this.squidSize = squidSize;
    }
}
