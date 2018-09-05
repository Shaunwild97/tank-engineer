/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.level;

import com.vobis.tankengineer.RayTrace;
import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.Vector2;
import com.vobis.tankengineer.entity.Entity;
import com.vobis.tankengineer.entity.EntityModular;
import org.newdawn.slick.GameContainer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Shaun
 */
public class Level {

    private LevelType levelType = LevelType.NORMAL;
    public final List<Entity> entities = new ArrayList<>();
    public Entity hovered;

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);

            if (e.isDead()) {
                entities.remove(e);
            } else {
                e.update();

                if (e.isSolid()) {
                    for (int j = 0; j < entities.size(); j++) {
                        Entity other = entities.get(j);

                        if (!other.isSolid() || other == e || e.connectedTo(other)) {
                            continue;
                        }

                        if (e.shouldCollide(other) && other.shouldCollide(e)) {
                            if (e.getCollisionPolygon().intersects(other.getCollisionPolygon())) {
                                e.onCollision(other);
                            }
                        }
                    }
                }
            }
        }
    }

    public void addEntity(Entity e, float x, float y) {
        entities.add(e);
        e.pos.set(x, y);
        e.init(this);

        entities.sort(Comparator.comparingInt(Entity::getZOffset));
    }

    public void removeEntity(Entity e) {
        e.setDead(true);
    }

    public Entity getEntityAtPoint(Vector2 point) {
        for (int j = entities.size() - 1; j >= 0; j--) {
            Entity ent = entities.get(j);

            if (!ent.isSolid()) {
                continue;
            }

            if (ent.getCollisionPolygon().contains(point.x, point.y)) {
                return ent;
            }
        }

        return null;
    }

    public Entity getNearestEntityTo(Class superClass, Entity entity) {
        Entity closest = null;
        float closestDist = Float.MAX_VALUE;

        for (int i = 0; i < entities.size(); i++) {
            Entity other = entities.get(i);

            if (other == entity || other.parent != null || other.connectedTo(entity)) {
                continue;
            }

            if (!superClass.isAssignableFrom(other.getClass())) {
                continue;
            }

            float distance = other.pos.distanceSq(entity.pos);

            if (distance < closestDist) {
                closestDist = distance;
                closest = other;
            }
        }

        return closest;
    }

    public void deselectAll() {
        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof EntityModular) {
                ((EntityModular) entities.get(i)).setSelected(false);
            }
        }
    }

    public RayTrace rayTrace(Entity source, Vector2 pos, float angle, int maxDistance, int resolution) {
        pos = pos.copy();
        Vector2 origin = pos.copy();
        Vector2 add = Vector2.createFromDirection(angle).multiply(resolution);

        RayTrace result = new RayTrace();
        result.setOrigin(origin);
        result.setDirection(angle);

        if (maxDistance == 0) {
            maxDistance = 3000;
        }

        for (int i = 0; i < maxDistance; i += resolution) {
            pos.add(add);

            Entity entity = getEntityAtPoint(pos);

            if (entity == null || entity.connectedTo(source)) {
                continue;
            }

            if (resolution > 1) {
                return rayTrace(source, pos, angle, resolution, 1);
            } else {
                result.setEntity(entity);
                break;
            }

        }

        result.setCollision(pos);

        return result;
    }

    public LevelType getLevelType() {
        return levelType;
    }

    public final void setLevelType(LevelType levelType) {
        this.levelType = levelType;
    }

    public boolean isOnScreen(Entity entity) {
        Vector2 screenPos = TankEngineer.gameInstance.screen.toScreenSpace(entity.pos.x, entity.pos.y);
        GameContainer container = TankEngineer.gameInstance.container;

        return screenPos.x > 0 && screenPos.x < container.getWidth() && screenPos.y > 0 && screenPos.y < container.getHeight();
    }
}
