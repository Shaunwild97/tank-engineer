/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer;

import com.vobis.tankengineer.entity.Entity;

/**
 *
 * @author Shaun
 */
public class RayTrace {

    private Vector2 origin;
    private Vector2 collision;
    private float direction;
    private float distance;
    private Entity entity;

    public RayTrace(Vector2 origin, Vector2 collision, float direction, float distance, Entity entity) {
        this.origin = origin;
        this.collision = collision;
        this.direction = direction;
        this.distance = distance;
        this.entity = entity;
    }

    public RayTrace() {
    }

    public Vector2 getOrigin() {
        return origin;
    }

    public Vector2 getCollision() {
        return collision;
    }

    public float getDirection() {
        return direction;
    }

    public float getDistance() {
        return distance;
    }

    public void setOrigin(Vector2 origin) {
        this.origin = origin;
    }

    public void setCollision(Vector2 collision) {
        this.collision = collision;
    }

    public void setDirection(float direction) {
        this.direction = direction;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}
