package com.kirbydee.splooshkaboom.utils;

public class Consts {

    // Activities
    public static final long GAME_ACTIVITY_BACKGROUND_SOUND_DELAY = 1300;
    public static final long GAME_OVER_ACTIVITY_BACKGROUND_SOUND_DELAY = 500;
    public static final long GAME_OVER_ACTIVITY_CHANGE_ACTIVITY_DELAY = 1000;
    public static final long SHOP_ACTIVITY_MIN_BEEDLE_SOUND_DELAY = 5000;
    public static final long SHOP_ACTIVITY_MAX_BEEDLE_SOUND_DELAY = 15000;
    public static final long SHOP_ACTIVITY_BUY_DIALOG_SHOW_DELAY = 2000;

    // Views
    public static final long GAME_OVER_FADE_IN_BUTTONS_DURATION = 1500;
    public static final long TEXT_BOX_NEXT_FADE_IN_DURATION = 100;
    public static final long UI_DELAY = 35;
    public static final long RUPEE_COUNT_MAX_TIME = 2000;
    public static final long SHOP_DIALOG_VIEW_FADE_DURATION = 500;
    public static final long INVENTORY_DISPLAY_FADE_IN_DURATION = 200;

    // Game Controller
    public static final int MAX_TURNS = 24;
    public static final int GAME_ROW_COUNT = 8;
    public static final int GAME_COLUMN_COUNT = 8;
    public static final int BOMB_COUNT = 24;
    public static final int SQUID_COUNT = 3;
    public static final int SQUID_SIZE_MAX = 4;

    // Shared Preferences
    public static final String PREFERENCE = "SplooshKaboom";
    public static final String KEY_RECORD = "KEY_RECORD";
    public static final String KEY_ITEMS_BOUGHT = "KEY_ITEMS_BOUGHT";
    public static final String KEY_RUPEES = "KEY_RUPEES";

    /*
     * The gForce that is necessary to register as shake.
     * Must be greater than 1G (one earth gravity unit).
     * You can install "G-Force", by Blake La Pierre
     * from the Google Play Store and run it to see how
     *  many G's it takes to register a shake
     */
    public static final float SHAKE_THRESHOLD_GRAVITY = 2.7F;
    public static final int SHAKE_SLOP_TIME_MS = 500;
    public static final int SHAKE_COUNT_RESET_TIME_MS = 3000;
    public static final int VIBRATE_MILLIS = 100;
}
