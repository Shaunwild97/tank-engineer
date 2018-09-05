package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.Vector2;
import com.vobis.tankengineer.circuit.Circuit;
import com.vobis.tankengineer.circuit.NodeDefinition;
import com.vobis.tankengineer.level.LevelType;
import com.vobis.tankengineer.modules.ModuleSize;
import com.vobis.tankengineer.modules.ModuleSlot;
import com.vobis.tankengineer.render.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shaun
 */
public abstract class EntityModular extends EntityPhysical {

    private Circuit circuit;

    private String name;
    private List<ModuleSlot> modules = new ArrayList<>();
    private List<EntityModular> childModules = new ArrayList<>();
    private EntityModular moduleOwner;
    private boolean selected;
    private float speed = .3f;
    private float turnSpeed = 2f;
    private boolean moveable = true;

    public EntityModular() {
        circuit = new Circuit(this);
        initModuleSlots();
    }

    public abstract void initModuleSlots();

    public abstract ModuleSize getModuleSize();

    public abstract NodeDefinition getCircuitNode();

    @Override
    public void update() {
        super.update();

        if (world.getLevelType() == LevelType.NORMAL) {
            updateModules();
        }
    }

    public void updateModules() {
        circuit.update();
    }

    public List<ModuleSlot> getModuleSlots() {
        return modules;
    }

    public void setModules(List<ModuleSlot> modules) {
        this.modules = modules;
    }

    public ModuleSlot getModule(int index) {
        return modules.get(index);
    }

    public void insertModule(int index, EntityModular module) {
        ModuleSlot slot = modules.get(index);

        if (slot.getSlotted() == null && module.getModuleSize().equals(slot.getModuleSize())) {
            slot.setSlotted(module);
            module.attach(this, slot.getAngleOffset(), slot.getDistanceOffset());
            module.setModuleOwner(this);

            getTopLevelOwner().childModules.add(module);
            getTopLevelOwner().circuit.initModuleNodes();

            addNewModules(module.getChildModules());

            world.addEntity(module, pos.x, pos.y);
        }
    }

    public void addNewModules(List<EntityModular> modules) {
        for (EntityModular module : modules) {
            if (!getChildModules().contains(module)) {
                getChildModules().add(module);
            }
        }
    }

    public void move(float dir) {
        if (dir > speed) {
            dir = speed;
        } else if (dir < -speed) {
            dir = -speed;
        }

        Vector2 vel = Vector2.createFromDirection(getDirection());
        this.vel.add(vel.multiply(dir));
    }

    public EntityModular getTopLevelOwner() {
        EntityModular owner = this;

        while (owner.getModuleOwner() != null) {
            owner = owner.getModuleOwner();
        }

        return owner;
    }

    public Circuit getCircuit() {
        return circuit;
    }

    public void setCircuit(Circuit circuit) {
        this.circuit = circuit;
    }

    public EntityModular getModuleOwner() {
        return moduleOwner;
    }

    public void setModuleOwner(EntityModular moduleOwner) {
        this.moduleOwner = moduleOwner;
    }

    public List<EntityModular> getChildModules() {
        return childModules;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getTurnSpeed() {
        return turnSpeed;
    }

    public void setTurnSpeed(float turnSpeed) {
        this.turnSpeed = turnSpeed;
    }

    public List<ModuleSlot> getModules() {
        return modules;
    }

    public float getSpeed() {
        return speed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isMoveable() {
        return moveable;
    }

    @Override
    public void render(Screen screen) {
        super.render(screen);

        for (int i = 0; i < getModuleSlots().size(); i++) {
            ModuleSlot slot = getModuleSlots().get(i);

            if (slot.getSlotted() == null) {
                double radians = Math.toRadians(-getDirection());

                double x = Math.sin(radians + angleOffset);
                double y = Math.cos(radians + angleOffset);

                float xOff = (float) x * distanceOffset;
                float yOff = (float) y * distanceOffset;

                int s = slot.getModuleSize().getSize();

                screen.drawRect(pos.x + xOff - s / 2, pos.y + yOff - s / 2, s, s, 0x000000);
            }
        }

        //screen.drawShape(getCollisionPolygon(), 0xFF0000);
    }
}
