package com.kirbydee.splooshkaboom.model.tile.game;

import com.kirbydee.splooshkaboom.view.layoutviews.tile.game.GameTileView;

public abstract class GameTile {

    private final int rowIndex;

    private final int columnIndex;

    private GameTileState currentState;

    private final GameTileState endState;

    public GameTile(int rowIndex, int columnIndex, GameTileState initState, GameTileState endState) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.currentState = initState;
        this.endState = endState;
    }

    public boolean isCorrectTile(GameTileView view) {
        return isCorrectTile(view.getRowIndex(), view.getColumnIndex());
    }

    public boolean isCorrectTile(int rowIndex, int columnIndex) {
        return rowIndex == this.rowIndex && columnIndex == this.columnIndex;
    }

    public boolean canBeShot() {
        return this.currentState != this.endState;
    }

    public boolean cannotBeShot() {
        return !canBeShot();
    }

    public GameTileState shoot() {
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

    public GameTileState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(GameTileState currentState) {
        this.currentState = currentState;
    }
}
