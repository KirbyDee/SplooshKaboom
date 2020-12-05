package com.kirbydee.splooshkaboom.model.media;

public interface Volume {

    default float getLeft() {
        return 0.5f;
    }

    default float getRight() {
        return 0.5f;
    }
}
