package com.kirbydee.splooshkaboom.utils.sound;

public enum MonoVolume implements Volume {

    VERY_LOW(0.1f),
    LOW(0.25f),
    NORMAL(0.5f),
    LOUD(0.75f),
    VERY_LOUD(0.9f),
    MAX(1.0f);

    private final float value;

    MonoVolume(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    @Override
    public float getLeft() {
        return getValue();
    }

    @Override
    public float getRight() {
        return getValue();
    }
}
