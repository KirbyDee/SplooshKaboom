package com.kirbydee.splooshkaboom.model.gridcell;

public class Squid extends GridCell {

    private SquidType tpe;

    public Squid(int row, int column, SquidType tpe) {
        super(row, column, GridCellState.SQUID, GridCellState.KABOOM);
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
