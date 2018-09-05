/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.circuit.NodeDefinition;
import com.vobis.tankengineer.circuit.Nodes;
import com.vobis.tankengineer.modules.ModuleSize;
import com.vobis.tankengineer.render.Animation;
import com.vobis.tankengineer.render.Resources;
import com.vobis.tankengineer.render.Screen;

/**
 *
 * @author Shaun
 */
public class EntityTargetFinder extends EntityModular {

    public EntityTargetFinder() {
        setAnimation(new Animation(Resources.targetFinder, 32), true);
    }

    @Override
    public void initModuleSlots() {
        zOffset = 9;
    }

    @Override
    public ModuleSize getModuleSize() {
        return ModuleSize.SMALL;
    }

    @Override
    public NodeDefinition getCircuitNode() {
        return Nodes.targetFinder;
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
        screen.drawImage(getAnimation().getCurrentFrame(), pos.x, pos.y, getDirection());
    }
}
