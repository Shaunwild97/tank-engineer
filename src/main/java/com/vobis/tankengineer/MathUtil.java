/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer;

/**
 *
 * @author Shaun
 */
public class MathUtil {

    public static float clamp(float a, float min, float max) {
        if (a < min) {
            return min;
        } else if (a > max) {
            return max;
        }

        return a;
    }

}
