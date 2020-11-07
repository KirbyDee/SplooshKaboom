package com.kirbydee.splooshkaboom.utils;

import com.kirbydee.splooshkaboom.R;

public enum Sounds {

    INTRO(R.raw.game_intro),
    SPLOOSH(R.raw.sploosh),
    KABOOM(R.raw.kaboom),
    HURRAY(R.raw.hurray),
    GAME_OVER(R.raw.game_over),
    TREASURE(R.raw.treasure);

    private int res;

    Sounds(int res) {
        this.res = res;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }
}
