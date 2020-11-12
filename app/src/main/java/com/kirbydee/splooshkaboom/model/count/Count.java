package com.kirbydee.splooshkaboom.model.count;

import android.content.Context;

import java.util.Arrays;

public enum Count {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9);

    private final int value;

    public static Count of(int number) {
        return Arrays.stream(Count.values())
                .filter(i -> i.value == number)
                .findAny()
                .orElse(ZERO);
    }

    Count(int value) {
        this.value = value;
    }
    
    public int getResId(Context context) {
        return context.getResources().getIdentifier("count_" + value, "drawable", context.getPackageName());
    }
}
