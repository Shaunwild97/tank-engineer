/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.circuit;

import com.vobis.tankengineer.entity.*;

/**
 *
 * @author Shaun
 */
public class Nodes {

    public static NodeDefinition getNode(String name) {
        try {
            return (NodeDefinition) Nodes.class.getField(name).get(null);
        } catch (Exception ex) {
            return null;
        }
    }

    public static NodeDefinition<EntityCannon> cannon = new NodeDefinition<EntityCannon>()
            .name("Cannon")
            .shortName("cannon")
            .inputs("Fire", "Turn")
            .outputs("Damage", "Direction", "Ready")
            .action(c -> {
                if (c.inputActive(0)) {
                    c.getOwner().fire();
                }
                if (c.inputActive(1)) {
                    float value = c.getInput(Float.class, 1);

                    float turnSpeed = c.getOwner().getTurnSpeed();

                    if (value > turnSpeed) {
                        c.getOwner().turn(-turnSpeed);
                    } else if (value < -turnSpeed) {
                        c.getOwner().turn(turnSpeed);
                    } else {
                        c.getOwner().turn(-value / turnSpeed);
                    }
                }

                c.setOutput(1, c.getOwner().relativeDir);
                c.setOutput(2, c.getOwner().readyToFire());
            });

    public static NodeDefinition<EntityTank> tank = new NodeDefinition<EntityTank>()
            .name("Tank")
            .shortName("tank")
            .inputs("Forward", "Turn")
            .outputs("Damage", "Direction")
            .action(c -> {
                c.setOutput(0, 1f);
                c.setOutput(1, c.getOwner().getDirection());

                if (c.inputActive(0)) {
                    float value = c.getInput(Float.class, 0);

                    float speed = c.getOwner().getSpeed();

                    if (value > 0) {
                        c.getOwner().move(speed);
                    } else if (value < 0) {
                        c.getOwner().move(-speed);
                    }
                }
                if (c.inputActive(1)) {
                    float value = c.getInput(Float.class, 1);

                    float turnSpeed = c.getOwner().getTurnSpeed();

                    if (value > turnSpeed) {
                        c.getOwner().turn(-turnSpeed);
                    } else if (value < -turnSpeed) {
                        c.getOwner().turn(turnSpeed);
                    } else {
                        c.getOwner().turn(-value / turnSpeed);
                    }
                }
            });

    public static NodeDefinition<EntityTargetFinder> targetFinder = new NodeDefinition<EntityTargetFinder>()
            .name("Target Finder")
            .shortName("targetFinder")
            .outputs("Target")
            .action(c -> {
                c.setOutput(0, c.getOwner().world.getNearestEntityTo(EntityModular.class, c.getOwner()));
            });

    public static NodeDefinition<EntityLocator> locator = new NodeDefinition<EntityLocator>()
            .name("Locator")
            .shortName("locator")
            .inputs("Target")
            .outputs("Distance", "Bearing")
            .action(c -> {
                if (c.inputActive(0)) {
                    EntityModular target = c.getInput(EntityModular.class, 0);

                    if (target != null) {
                        float distance = c.getOwner().pos.distance(target.pos);

                        float wantedDir = c.getOwner().pos.angleTo(target.pos);
                        float bearing = c.getOwner().getDirection() - wantedDir;

                        float absTheta = Math.abs(bearing);

                        if (absTheta > 180) {
                            bearing += bearing > 0 ? -360 : 360;
                        }

                        c.setOutput(0, distance);
                        c.setOutput(1, bearing);
                    }
                }
            });

    public static NodeDefinition<EntityLaser> laser = new NodeDefinition<EntityLaser>()
            .name("Laser")
            .shortName("laser")
            .inputs("Power", "Turn")
            .outputs("Distance", "Direction")
            .action(c -> {
                c.getOwner().setOn(c.inputActive(0));
                c.setOutput(1, c.getOwner().getDirection());

                if (c.inputActive(1)) {
                    float value = c.getInput(Float.class, 1);

                    float turnSpeed = c.getOwner().getTurnSpeed();

                    if (value > turnSpeed) {
                        c.getOwner().turn(-turnSpeed);
                    } else if (value < -turnSpeed) {
                        c.getOwner().turn(turnSpeed);
                    } else {
                        c.getOwner().turn(-value / turnSpeed);
                    }
                }

                if (c.getOwner().getTrace() != null) {
                    c.setOutput(0, c.getOwner().getTrace().getDistance());
                }
            });

    public static NodeDefinition<EntityDrone> drone = new NodeDefinition<EntityDrone>()
            .name("Drone")
            .shortName("drone")
            .inputs("Forward", "Turn")
            .outputs("Damage")
            .action(c -> {
                c.setOutput(0, 1);

                if (c.inputActive(0)) {
                    float value = c.getInput(Float.class, 0);

                    if (value > 0) {
                        c.getOwner().move(1);
                    } else if (value < 0) {
                        c.getOwner().move(-1);
                    }
                }
                if (c.inputActive(1)) {
                    float value = c.getInput(Float.class, 1);

                    if (value > 0) {
                        c.getOwner().turn(-1);
                    } else if (value < 0) {
                        c.getOwner().turn(1);
                    }
                }
            });

    public static final NodeDefinition add = new NodeDefinition<>()
            .name("Add")
            .shortName("add")
            .inputs("a", "b")
            .outputs("Result")
            .action(c -> {
                Float a = c.getInput(Float.class, 0);
                Float b = c.getInput(Float.class, 1);

                if (a != null && b != null) {
                    c.setOutput(0, a + b);
                } else {
                    c.setOutput(0, null);
                }
            });
    public static final NodeDefinition subtract = new NodeDefinition<>()
            .name("Subtract")
            .shortName("subtract")
            .inputs("a", "b")
            .outputs("Result")
            .action(c -> {
                Float a = c.getInput(Float.class, 0);
                Float b = c.getInput(Float.class, 1);

                if (a != null && b != null) {
                    c.setOutput(0, a - b);
                } else {
                    c.setOutput(0, null);
                }
            });
    public static final NodeDefinition absolute = new NodeDefinition<>()
            .name("Absolute")
            .shortName("absolute")
            .inputs("a")
            .outputs("Result")
            .action(c -> {
                Float a = c.getInput(Float.class, 0);

                if (a != null) {
                    c.setOutput(0, Math.abs(a));
                } else {
                    c.setOutput(0, 0f);
                }
            });

    public static final NodeDefinition greaterThan = new NodeDefinition<>()
            .name("Greater Than")
            .shortName("greaterThan")
            .inputs("a", "b")
            .outputs("Result")
            .action(c -> {
                Float a = c.getInput(Float.class, 0);
                Float b = c.getInput(Float.class, 1);

                if (a != null && b != null) {
                    c.setOutput(0, a > b);
                } else {
                    c.setOutput(0, false);
                }
            });

    public static final NodeDefinition lessThan = new NodeDefinition<>()
            .name("Less Than")
            .shortName("lessThan")
            .inputs("a", "b")
            .outputs("Result")
            .action(c -> {
                Float a = c.getInput(Float.class, 0);
                Float b = c.getInput(Float.class, 1);

                if (a != null && b != null) {
                    c.setOutput(0, a < b);
                } else {
                    c.setOutput(0, false);
                }
            });
}
