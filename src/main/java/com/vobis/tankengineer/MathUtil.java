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
