package com.kirbydee.splooshkaboom.model.textstate;

import com.kirbydee.splooshkaboom.R;

public enum WinState implements TextState {

    INTRO(-1),
    RUPEE_SHOW(-1),
    RUPEE_TEXT(R.string.win_text),
    RUPEE_COUNT(-1);

    private final int textId;

    WinState(int textId) {
        this.textId = textId;
    }

    @Override
    public int getTextId() {
        return this.textId;
    }
}
