package com.kirbydee.splooshkaboom.model.gridcell;

public class Squid extends GridCell {

    public Squid(int row, int column) {
        super(row, column, GridCellState.SQUID, GridCellState.KABOOM);
    }
}
