package com.kirbydee.splooshkaboom.model.gridcell;

import com.kirbydee.splooshkaboom.model.cellview.GridCellView;

public abstract class GridCell {

    private final int row;
    private final int column;
    private GridCellState currentState;
    private final GridCellState endState;

    public GridCell(int row, int column, GridCellState initState, GridCellState endState) {
        this.row = row;
        this.column = column;
        this.currentState = initState;
        this.endState = endState;
    }

    public boolean isCorrectGrid(GridCellView view) {
        return isCorrectGrid(view.getRowIndex(), view.getColumnIndex());
    }

    public boolean isCorrectGrid(int row, int column) {
        return row == this.row && column == this.column;
    }

    public boolean canBeHit() {
        return this.currentState != this.endState;
    }

    public boolean cannotBeHit() {
        return !canBeHit();
    }

    public GridCellState hit() {
        this.currentState = endState;
        return this.currentState;
    }

    public GridCellState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GridCellState currentState) {
        this.currentState = currentState;
    }
}
