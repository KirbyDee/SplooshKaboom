package com.kirbydee.splooshkaboom.utils.sound;

public interface Volume {

    default float getLeft() {
        return 0.5f;
    }

    default float getRight() {
        return 0.5f;
    }
}
