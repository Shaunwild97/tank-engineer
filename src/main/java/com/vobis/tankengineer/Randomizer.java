/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
