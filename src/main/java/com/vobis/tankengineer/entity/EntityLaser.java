/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.RayTrace;
import com.vobis.tankengineer.circuit.NodeDefinition;
import com.vobis.tankengineer.circuit.Nodes;
import com.vobis.tankengineer.modules.ModuleSize;
import com.vobis.tankengineer.render.ModelLoader;
import com.vobis.tankengineer.render.Resources;
import com.vobis.tankengineer.render.Screen;

/**
 *
 * @author Shaun
 */
public class EntityLaser extends EntityModular {

    private boolean on;
    private RayTrace trace;

    public EntityLaser() {
        zOffset = 11;
        setModel(ModelLoader.loadModel("Laser_Base"));
    }

    @Override
    public void initModuleSlots() {
    }

    @Override
    public void update() {
        super.update();

        if (on) {
            trace = world.rayTrace(this, pos, getDirection(), 5000, 100);
        } else {
            trace = null;
        }
    }

    @Override
    public ModuleSize getModuleSize() {
        return ModuleSize.SMALL;
    }

    @Override
    public NodeDefinition getCircuitNode() {
        return Nodes.laser;
    }

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public RayTrace getTrace() {
        return trace;
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
        screen.drawImage(Resources.laser.getSprite(0, 0), pos.x, pos.y, dir);

        if (trace != null) {
            screen.drawLine(pos.x, pos.y, trace.getCollision().x, trace.getCollision().y, 0xFF0000);
        }

        screen.drawImage(Resources.laser.getSprite(1, 0), pos.x, pos.y, getDirection());
    }
}
