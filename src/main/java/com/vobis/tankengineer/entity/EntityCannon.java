/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.circuit.NodeDefinition;
import com.vobis.tankengineer.circuit.Nodes;
import com.vobis.tankengineer.modules.ModuleSize;
import com.vobis.tankengineer.modules.ModuleSlot;
import com.vobis.tankengineer.render.Animation;
import com.vobis.tankengineer.render.ModelLoader;
import com.vobis.tankengineer.render.Resources;

/**
 *
 * @author Shaun
 */
public class EntityCannon extends EntityModular {

    private int fireTime;
    protected int fireCooldown = 100;

    public int cannonLength = 180;

    public EntityCannon() {
        zOffset = 10;
        setModel(ModelLoader.loadModel("KV2_Cannon"));
    }

    @Override
    public void update() {
        super.update();
        fireTime--;
    }

    public boolean readyToFire() {
        return fireTime <= 0;
    }

    public void fire() {
        if (readyToFire()) {
            EntityProjectile shell = new EntityProjectile(this);
            shell.setModel(ModelLoader.loadModel("Tank_Shell"));

            double radians = Math.toRadians(-getDirection());

            float x = (float) (Math.sin(radians) * cannonLength);
            float y = (float) (Math.cos(radians) * cannonLength);

            if (getModel().getAnimation() != null) {
                getModel().getAnimation().play();
            }

            relativeDir += rand.nextGaussian() * 2;

            if (world.isOnScreen(this)) {
                TankEngineer.gameInstance.screen.cameraShake = 10f;
            }

            world.addEntity(shell, pos.x - x, pos.y - y);
            world.addEntity(new EntityParticle(new Animation(Resources.explosion6, 360)), pos.x - x, pos.y - y);

            fireTime = fireCooldown;
        }
    }

    @Override
    public void initModuleSlots() {
        getModuleSlots().add(new ModuleSlot(ModuleSize.SMALL, 35, 30));
        getModuleSlots().add(new ModuleSlot(ModuleSize.SMALL, -35, 30));
    }

    @Override
    public ModuleSize getModuleSize() {
        return ModuleSize.LARGE;
    }

    @Override
    public NodeDefinition getCircuitNode() {
        return Nodes.cannon;
    }
}
