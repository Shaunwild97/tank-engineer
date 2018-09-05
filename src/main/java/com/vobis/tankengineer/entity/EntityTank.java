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
public class EntityTank extends EntityModular {

    public EntityTank() {
        zOffset = 2;
        setModel(ModelLoader.loadModel("KV2_Body"));
        mass = 1000;
        setMoveable(false);
    }

    @Override
    public void update() {
        super.update();
        //        float velocityDir = Math.abs(getDirection() - vel.toAngle());
        //        velocityDir = Math.abs(90 - velocityDir) / 90f;
        //        System.out.println(velocityDir);
        //
        //        velocityDir = velocityDir / (1 - velocityDir) / 10;
        //        velocityDir = MathUtil.clamp(velocityDir, 0, 1);
        //
        //        vel.multiply(.99f * (velocityDir));
        //        world.addEntity(new EntityParticle(new Animation(Resources.explosion, 32)), pos.x, pos.y);
    }

    @Override
    public void initModuleSlots() {
        getModuleSlots().add(new ModuleSlot(ModuleSize.LARGE, 0, 0));
        getModuleSlots().add(new ModuleSlot(ModuleSize.SMALL, 20, 100));
        getModuleSlots().add(new ModuleSlot(ModuleSize.SMALL, 340, 100));
    }

    @Override
    public ModuleSize getModuleSize() {
        return ModuleSize.GIANT;
    }

    @Override
    public NodeDefinition getCircuitNode() {
        return Nodes.tank;
    }
}
