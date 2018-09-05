/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.Vector2;

/**
 *
 * @author Shaun
 */
public class EntityPhysical extends Entity {

    private int health;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void moveForward() {
        vel.add(Vector2.createFromDirection(getDirection()).multiply(.5f));
    }

    public void brake(boolean hard) {
        vel.multiply(hard ? .85f : .999f);
    }

    public void turn(float t) {
        relativeDir += t;
    }
}
