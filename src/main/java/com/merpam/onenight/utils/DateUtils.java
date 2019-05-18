package com.merpam.onenight.utils;

public final class DateUtils {

    private DateUtils() {
        //private constructor, not meant to be initialized
    }

    public static long getCurrentTimestampInSeconds() {
        return System.currentTimeMillis() / 1000;
    }
}
