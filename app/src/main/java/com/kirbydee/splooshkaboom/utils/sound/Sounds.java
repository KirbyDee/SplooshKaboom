package com.kirbydee.splooshkaboom.utils.sound;

import com.kirbydee.splooshkaboom.R;

public enum Sounds {

    // background music
    INTRO_BACKGROUND(R.raw.intro_background, true),
    GAME_BACKGROUND(R.raw.game_background, true),
    GAME_OVER(R.raw.game_over),

    // sfx
    GAME_START(R.raw.game_start),
    GAME_OVER_BUTTON_CLICK(R.raw.game_over_button_click),
    SPLOOSH(R.raw.sploosh),
    KABOOM(R.raw.kaboom),
    HURRAY(R.raw.hurray),
    TREASURE(R.raw.treasure);

    private final int res;

    private final boolean loop;

    Sounds(int res) {
        this(res, false);
    }

    Sounds(int res, boolean loop) {
        this.res = res;
        this.loop = loop;
    }

    public int getRes() {
        return res;
    }

    public boolean isLoop() {
        return loop;
    }
}
