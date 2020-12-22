package com.kirbydee.splooshkaboom.utils;

import java.util.function.Predicate;

public class PredicateExt {

    public static <T> Predicate<T> not(Predicate<T> t) {
        return t.negate();
    }
}
