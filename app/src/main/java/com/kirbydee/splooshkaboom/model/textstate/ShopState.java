package com.kirbydee.splooshkaboom.model.textstate;

import com.kirbydee.splooshkaboom.R;

public enum ShopState implements TextState {

    INTRO_1(R.string.shop_text_intro_1),
    INTRO_2(R.string.shop_text_intro_2),
    IDLE(R.string.shop_text_idle),
    BROWSE_ITEM_1(R.string.shop_text_browse_item_1),
    BROWSE_ITEM_2(R.string.shop_text_browse_item_2),
    BROWSE_ITEM_3(R.string.shop_text_browse_item_3),
    BUY_ITEM_1(R.string.shop_text_buy_item_1),
    BUY_ITEM_2(R.string.shop_text_buy_item_2),
    BUY_ITEM_3(R.string.shop_text_buy_item_3),
    BYE(R.string.shop_text_bye);

    private final int textId;

    ShopState(int textId) {
        this.textId = textId;
    }

    @Override
    public int getTextId() {
        return this.textId;
    }
}
