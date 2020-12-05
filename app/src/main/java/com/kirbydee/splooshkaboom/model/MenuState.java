package com.kirbydee.splooshkaboom.model;

import com.kirbydee.splooshkaboom.R;

public enum MenuState {

    INIT(-1),
    INTRO(R.string.menu_text_intro),
    MENU(-1),
    TEXT_START_1(R.string.menu_text_start_1),
    TEXT_START_2(R.string.menu_text_start_2),
    TEXT_START_3(R.string.menu_text_start_3),
    TEXT_START_4(R.string.menu_text_start_4),
    TEXT_START_5(R.string.menu_text_start_5),
    TEXT_START_6(R.string.menu_text_start_6),
    START(-1);

    public int textId;

    MenuState(int textId) {
        this.textId = textId;
    }
}
