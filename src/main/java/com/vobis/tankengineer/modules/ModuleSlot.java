package com.vobis.tankengineer.modules;

import com.vobis.tankengineer.entity.EntityModular;

/**
 *
 * @author Shaun
 */
public class ModuleSlot {

    private EntityModular slotted;
    private ModuleSize moduleSize;
    private float angleOffset;
    private float distanceOffset;

    public ModuleSlot() {
    }

    public ModuleSlot(ModuleSize moduleSize, float angleOffset, float distanceOffset) {
        this.moduleSize = moduleSize;
        this.angleOffset = angleOffset;
        this.distanceOffset = distanceOffset;
    }

    public ModuleSize getModuleSize() {
        return moduleSize;
    }

    public EntityModular getSlotted() {
        return slotted;
    }

    public void setSlotted(EntityModular slotted) {
        this.slotted = slotted;
    }

    public float getAngleOffset() {
        return angleOffset;
    }

    public float getDistanceOffset() {
        return distanceOffset;
    }

    public void setModuleSize(ModuleSize moduleSize) {
        this.moduleSize = moduleSize;
    }

    public void setAngleOffset(float angleOffset) {
        this.angleOffset = angleOffset;
    }

    public void setDistanceOffset(float distanceOffset) {
        this.distanceOffset = distanceOffset;
    }

}
