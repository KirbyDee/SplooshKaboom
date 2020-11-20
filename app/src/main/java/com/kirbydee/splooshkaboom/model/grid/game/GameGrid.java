package com.kirbydee.splooshkaboom.model.grid.game;

import com.kirbydee.splooshkaboom.view.layoutviews.grid.game.GameGridView;

public abstract class GameGrid {

    private final int rowIndex;

    private final int columnIndex;

    private GameGridState currentState;

    private final GameGridState endState;

    public GameGrid(int rowIndex, int columnIndex, GameGridState initState, GameGridState endState) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.currentState = initState;
        this.endState = endState;
    }

    public boolean isCorrectGrid(GameGridView view) {
        return isCorrectGrid(view.getRowIndex(), view.getColumnIndex());
    }

    public boolean isCorrectGrid(int rowIndex, int columnIndex) {
        return rowIndex == this.rowIndex && columnIndex == this.columnIndex;
    }

    public boolean canBeShot() {
        return this.currentState != this.endState;
    }

    public boolean cannotBeShot() {
        return !canBeShot();
    }

    public GameGridState shoot() {
        this.currentState = this.endState;
        return this.currentState;
    }

    @Override
    public String toString() {
        return "GameGrid{" +
                "rowIndex=" + rowIndex +
                ", columnIndex=" + columnIndex +
                ", currentState=" + currentState +
                ", endState=" + endState +
                '}';
    }

    public GameGridState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameGridState currentState) {
        this.currentState = currentState;
    }
}
