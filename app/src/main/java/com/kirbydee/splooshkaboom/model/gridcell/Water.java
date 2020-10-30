package com.kirbydee.splooshkaboom.model.gridcell;

public class Water extends GridCell {

    public Water(int row, int column) {
        super(row, column, GridCellState.WATER, GridCellState.SPLOOSH);
    }
}
