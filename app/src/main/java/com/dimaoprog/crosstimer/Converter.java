package com.dimaoprog.crosstimer;

import androidx.databinding.InverseMethod;

import java.util.Locale;

public class Converter {

    @InverseMethod("stringToInt")
    public static String intToString(int value) {
        return value == 0 ? "00" : String.format(Locale.getDefault(),"%02d", value);
    }

    public static int stringToInt(String value) {
        return value.isEmpty() ? 0 : Integer.valueOf(value);
    }

    public static String longTimeToString(Long time) {
        return time == null ? "00:00" : String.format(Locale.getDefault(),"%02d:%02d", time/60, time%60);
    }

    public static String longTimeToStringDetail(Long time) {
        return time == null ? "0" : String.valueOf(time);
    }

}
