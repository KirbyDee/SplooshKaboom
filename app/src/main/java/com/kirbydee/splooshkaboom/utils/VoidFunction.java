package com.kirbydee.splooshkaboom.utils;

@FunctionalInterface
public interface VoidFunction {

    void run();

    static VoidFunction nothing() {
        return () -> {
            // do nothing
        };
    }
}
