package com.kirbydee.splooshkaboom.model.tile.game;

public enum GameTileSquidType {

    SQUID_2(2),
    SQUID_3(3),
    SQUID_4(4);

    public int length;

    GameTileSquidType(int length) {
        this.length = length;
    }
}
