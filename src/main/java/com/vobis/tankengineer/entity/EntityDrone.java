package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.circuit.NodeDefinition;
import com.vobis.tankengineer.circuit.Nodes;
import com.vobis.tankengineer.modules.ModuleSize;
import com.vobis.tankengineer.modules.ModuleSlot;
import com.vobis.tankengineer.render.ModelLoader;

/**
 *
 * @author Shaun
 */
public class EntityDrone extends EntityModular {

    public EntityDrone() {
        zOffset = 1;
        setModel(ModelLoader.loadModel("Drone"));
    }

    @Override
    public void update() {
        super.update();
        dir += .2f;
        move(1);
    }

    @Override
    public void initModuleSlots() {
        getModuleSlots().add(new ModuleSlot(ModuleSize.SMALL, 0, 0));
    }

    @Override
    public ModuleSize getModuleSize() {
        return ModuleSize.GIANT;
    }

    @Override
    public NodeDefinition getCircuitNode() {
        return Nodes.drone;
    }
}
