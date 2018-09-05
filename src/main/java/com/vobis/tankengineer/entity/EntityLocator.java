package com.vobis.tankengineer.entity;

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
public class EntityLocator extends EntityModular {

    public EntityLocator() {
        zOffset = 9;
        setModel(ModelLoader.loadModel("Locator"));
    }

    @Override
    public void initModuleSlots() {
    }

    @Override
    public ModuleSize getModuleSize() {
        return ModuleSize.SMALL;
    }

    @Override
    public NodeDefinition getCircuitNode() {
        return Nodes.locator;
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);
        screen.drawImage(Resources.locator, pos.x, pos.y, getDirection());
    }
}
