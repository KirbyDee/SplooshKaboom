package com.kirbydee.splooshkaboom.model.grid.game;

public enum SquidType {

    SQUID_2(2),
    SQUID_3(3),
    SQUID_4(4);

    public int length;

    SquidType(int length) {
        this.length = length;
    }
}
