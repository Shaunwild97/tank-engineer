/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.Vector2;
import com.vobis.tankengineer.render.ModelLoader;

/**
 *
 * @author Shaun
 */
public class EntityProjectile extends Entity {

    public Entity owner;
    private float speed = 30f;

    public EntityProjectile(Entity owner) {
        this.owner = owner;
        this.dir = owner.getDirection();
        zOffset = 5;
        vel = Vector2.createFromDirection(getDirection()).multiply(speed);
        setModel(ModelLoader.loadModel("Tank_Shell"));
        slowdownFactor = 1f;
        mass = 0;
        size = 0;
    }

    @Override
    public void onCollision(Entity other) {
        setDead(true);
    }

    @Override
    public void setDead(boolean dead) {
        super.setDead(dead);
//        world.addEntity(new EntityParticle(new Animation(Resources.explosion1, 360)), pos.x, pos.y);
    }

    @Override
    public boolean shouldCollide(Entity other) {
        return !other.connectedTo(owner);
    }

    @Override
    public void update() {
        super.update();

        if (age > 100) {
            setDead(true);
        }

        pos.add(vel);
    }
}
