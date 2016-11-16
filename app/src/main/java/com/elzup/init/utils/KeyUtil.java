package com.elzup.init.utils;

import java.math.BigInteger;
import java.security.SecureRandom;

public class KeyUtil {
    private static SecureRandom random = new SecureRandom();

    public static String generateRandomCode() {
        return new BigInteger(130, random).toString(32);
    }
}
