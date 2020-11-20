package com.kirbydee.splooshkaboom.utils;

public class Consts {

    public static final int MAX_TURNS = 24;

    public static final int VIBRATE_MILLIS = 100;

    public static final long GAME_ACTIVITY_BACKGROUND_SOUND_DELAY = 1300;

    public static final int GAME_ROW_COUNT = 8;

    public static final int GAME_COLUMN_COUNT = 8;

    public static final int BOMB_COUNT = 24;

    public static final int SQUID_COUNT = 3;

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
}
