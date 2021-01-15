package com.kirbydee.splooshkaboom.model.counter;

import com.kirbydee.splooshkaboom.R;

public enum Rupee {

    GREEN(1, R.drawable.rupee_green_anim),
    BLUE(5, R.drawable.rupee_blue_anim),
    RED(20, R.drawable.rupee_red_anim),
    YELLOW(50, R.drawable.rupee_yellow_anim);

    public Rupees amount;

    public int resId;

    Rupee(int amount, int resId) {
        this.amount = Rupees.of(amount);
        this.resId = resId;
    }
}
