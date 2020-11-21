package com.kirbydee.splooshkaboom.model.tile.game;

public class GameTileSquid extends GameTile {

    private GameTileSquidType tpe;

    public GameTileSquid(int row, int column, GameTileSquidType tpe) {
        super(row, column, GameTileState.SQUID, GameTileState.KABOOM);
        this.tpe = tpe;
    }

    @Override
    public String toString() {
        return "Squid{" +
                "tpe=" + tpe +
                '}';
    }

    public GameTileSquidType getTpe() {
        return tpe;
    }

    public void setTpe(GameTileSquidType tpe) {
        this.tpe = tpe;
    }
}
