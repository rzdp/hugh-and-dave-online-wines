package com.rzdp.winestoreapi.utils;

import java.util.Random;

public final class StringUtils {

    public static String padRight(String s, int n, String pad) {
        String format = "%1$-" + n + "s";
        return String.format(format, s).replace(" ", pad);
    }

    public static String padLeft(String s, int n, String pad) {
        String format = "%1$" + n + "s";
        return String.format(format, s).replace(" ", pad);
    }

    public static String generateOtp(int length) {
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[length];
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return String.valueOf(otp);
    }

}
