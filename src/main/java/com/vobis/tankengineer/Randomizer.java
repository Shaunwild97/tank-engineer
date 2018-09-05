package com.vobis.tankengineer;

import java.util.Random;

/**
 *
 * @author Shaun
 */
public class Randomizer {

    private static final Random rand = new Random();

    public static String nextUID() {
        long time = System.currentTimeMillis() / 10000;
        String result = "";

        result += Long.toHexString(time);

        for (int i = 0; i < 8; i++) {
            result += (char) (rand.nextInt(25) + 97);
        }

        return result;
    }

}
