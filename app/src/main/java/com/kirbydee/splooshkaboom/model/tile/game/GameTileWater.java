package com.kirbydee.splooshkaboom.model.tile.game;

public class GameTileWater extends GameTile {

    public GameTileWater(int row, int column) {
        super(row, column, GameTileState.WATER, GameTileState.SPLOOSH);
    }
}
