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
public class Vector2 {

    public float x, y;

    public Vector2() {
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 createFromDirection(float d) {
        Vector2 vector = new Vector2();

        double rad = Math.toRadians(d - 90);
        double x = Math.sin(rad);
        double y = Math.cos(rad);

        vector.set((float) y, (float) x);
        return vector;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    public Vector2 subtract(Vector2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public Vector2 normalize() {
        double len = getLength();

        x /= len;
        y /= len;

        return this;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector2 v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2 add(Vector2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public Vector2 multiply(float s) {
        this.x *= s;
        this.y *= s;
        return this;
    }

    public float distanceSq(Vector2 other) {
        float dx = other.x - x;
        float dy = other.y - y;

        return dx * dx + dy * dy;
    }

    public float distance(Vector2 other) {
        return (float) Math.sqrt(distanceSq(other));
    }

    public float getLength() {
        double len = x * x + y * y;
        len = Math.sqrt(len);

        return (float) len;
    }

    /**
     * @return The angle between two objects from -180 to 180
     */
    public float angleTo(Vector2 other) {
        float dx = other.x - x;
        float dy = y - other.y;

        double result = Math.toDegrees(Math.atan2(dx, dy));

        return (float) ((result < 0) ? (360d + result) : result);
    }

    public float toAngle() {
        double result = Math.toDegrees(Math.atan2(x, y));

        return (float) ((result < 0) ? (360d + result) : result);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
