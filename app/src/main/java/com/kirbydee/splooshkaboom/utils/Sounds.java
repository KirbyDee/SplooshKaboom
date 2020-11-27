package com.kirbydee.splooshkaboom.utils;

import com.kirbydee.splooshkaboom.R;

public enum Sounds {

    // background music
    INTRO_BACKGROUND(R.raw.intro_background),
    GAME_BACKGROUND(R.raw.game_background),
    GAME_OVER(R.raw.game_over),

    // sfx
    GAME_START(R.raw.game_start),
    GAME_OVER_BUTTON_CLICK(R.raw.game_over_button_click),
    SPLOOSH(R.raw.sploosh),
    KABOOM(R.raw.kaboom),
    HURRAY(R.raw.hurray),
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
