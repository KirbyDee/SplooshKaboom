package com.kirbydee.splooshkaboom.model.grid.game;

public class Squid extends GameGrid {

    private SquidType tpe;

    public Squid(int row, int column, SquidType tpe) {
        super(row, column, GameGridState.SQUID, GameGridState.KABOOM);
        this.tpe = tpe;
    }

    @Override
    public String toString() {
        return "Squid{" +
                "tpe=" + tpe +
                '}';
    }

    public SquidType getTpe() {
        return tpe;
    }

    public void setTpe(SquidType tpe) {
        this.tpe = tpe;
    }
}
