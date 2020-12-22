package com.kirbydee.splooshkaboom.view.layoutviews.textbox;

import static com.kirbydee.splooshkaboom.utils.Consts.UI_DELAY;

public enum TextSpeed {

    DEFAULT(UI_DELAY),
    FAST(UI_DELAY / 3);

    public long time;

    private TextSpeed(long time) {
        this.time = time;
    }
}
