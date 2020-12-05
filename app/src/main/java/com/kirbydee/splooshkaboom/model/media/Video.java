package com.kirbydee.splooshkaboom.model.media;

import com.kirbydee.splooshkaboom.R;

public enum Video {

    // loop videos
    MENU_IDLE(R.raw.menu_idle, true),
    MENU_TALK(R.raw.menu_talk, true),


    // non-loop videos
    MENU_INTRO(R.raw.menu_intro);

    private final int res;

    private final boolean loop;

    Video(int res) {
        this(res, false);
    }

    Video(int res, boolean loop) {
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
