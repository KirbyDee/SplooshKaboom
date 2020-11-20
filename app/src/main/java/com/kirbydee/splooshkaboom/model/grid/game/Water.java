package com.kirbydee.splooshkaboom.model.grid.game;

public class Water extends GameGrid {

    public Water(int row, int column) {
        super(row, column, GameGridState.WATER, GameGridState.SPLOOSH);
    }
}
