package com.kirbydee.splooshkaboom.model.media;

import com.kirbydee.splooshkaboom.R;

public enum Sound {

    // background music
    INTRO_BACKGROUND(R.raw.intro_background, true),
    MENU_BACKGROUND(R.raw.menu_background, true),
    GAME_BACKGROUND(R.raw.game_background, true),
    SHOP_BACKGROUND(R.raw.shop_background, true),
    INVENTORY_BACKGROUND(R.raw.inventory_background, true),
    GAME_OVER(R.raw.game_over),

    // sfx
    INTRO_START(R.raw.intro_start),
    GAME_START(R.raw.game_start),
    GAME_OVER_BUTTON_CLICK(R.raw.game_over_button_click),
    TEXT_BUTTON_SOUND(R.raw.text_button_sound),
    SPLOOSH(R.raw.sploosh),
    KABOOM(R.raw.kaboom),
    HURRAY(R.raw.hurray),
    RUPEE(R.raw.rupee),
    ITEM_OBTAINED(R.raw.item_obtained),
    HEART_PIECE_SELECT(R.raw.heart_piece_select),
    RIDDLE_SELECT(R.raw.riddle_select, true),
    CHEST_OPEN(R.raw.chest_open_sound),
    BEEDLE_OHH(R.raw.beedle_ohh),
    BEEDLE_THANKYOU(R.raw.beedle_thankyou),
    BEEDLE_BYE(R.raw.beedle_bye);

    private final int res;

    private final boolean loop;

    Sound(int res) {
        this(res, false);
    }

    Sound(int res, boolean loop) {
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
