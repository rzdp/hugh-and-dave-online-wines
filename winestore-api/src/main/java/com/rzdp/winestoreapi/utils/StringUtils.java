package com.rzdp.winestoreapi.utils;

public final class StringUtils {

    public static String padRight(String s, int n, String pad) {
        String format = "%1$-" + n + "s";
        return String.format(format, s).replace(" ", pad);
    }

    public static String padLeft(String s, int n, String pad) {
        String format = "%1$" + n + "s";
        return String.format(format, s).replace(" ", pad);
    }

}
