package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.Randomizer;
import com.vobis.tankengineer.Vector2;
import com.vobis.tankengineer.level.Level;
import com.vobis.tankengineer.render.Animation;
import com.vobis.tankengineer.render.Model;
import com.vobis.tankengineer.render.Resources;
import com.vobis.tankengineer.render.Screen;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Transform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Shaun
 */
public abstract class Entity implements Serializable {

    public Vector2 pos = new Vector2();
    public Vector2 vel = new Vector2();
    public float dir;
    public Level world;
    public int age;
    public Entity parent;
    public Vector2 offset;
    public float angleOffset;
    public float distanceOffset;
    public float relativeDir;
    public int zOffset;
    public int team;
    public float slowdownFactor = .9f;
    private boolean staticObject;
    private String id;

    protected Animation animation;
    protected Model model;
    protected Random rand;

    private boolean dead;
    private List<Entity> children = new ArrayList<>();
    private Polygon collisionPolygon;
    private float width, height;
    public int size = 30;
    public int mass = 10;
    private boolean solid = true;

    public Entity() {
        setSize(80, 200);
        rand = new Random();
        id = Randomizer.nextUID();
    }

    public final void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        this.collisionPolygon = new Polygon(new float[]{0, 0, width, 0, width, height, 0, height});
    }

    public void init(Level world) {
        this.world = world;
    }

    public void update() {
        age++;
        vel.multiply(slowdownFactor);

        if (animation != null) {
            animation.update();
        }

        if (dir > 360f) {
            dir -= 360f;
        } else if (dir < 0) {
            dir += 360f;
        }

        if (relativeDir > 360f) {
            relativeDir -= 360f;
        } else if (relativeDir < 0) {
            relativeDir += 360f;
        }

        pos.add(vel);

        if (parent != null) {
            pos.x = parent.pos.x;
            pos.y = parent.pos.y;

            if (offset != null) {
                pos.x += offset.x;
                pos.y += offset.y;
            }

            double radians = Math.toRadians(-parent.getDirection());

            double x = Math.sin(radians + angleOffset);
            double y = Math.cos(radians + angleOffset);

            pos.x += x * distanceOffset;
            pos.y += y * distanceOffset;

            dir = parent.getDirection();
        }
    }

    public Polygon getCollisionPolygon() {
        Transform transform = Transform.createRotateTransform((float) Math.toRadians(getDirection()), pos.x, pos.y);
        transform.concatenate(Transform.createTranslateTransform(pos.x, pos.y));

        return (Polygon) collisionPolygon.transform(transform);
    }

    public void onCollision(Entity other) {
        Vector2 totalVelocity = vel.copy()
                .add(other.vel)
                .add(pos
                        .copy()
                        .subtract(other.pos)
                        .normalize());

        int tSize = size * mass;
        int otSize = other.size * other.mass;
        float relativeWeight = (float) (otSize) / (tSize + otSize);

        if (!isStaticObject()) {
            vel.set(totalVelocity.multiply(relativeWeight));
        }
    }

    public boolean shouldCollide(Entity other) {
        return true;
    }

    public float getDirection() {
        return dir + relativeDir;
    }

    public void render(Screen screen) {
        if (model != null) {
            Animation animation = model.getAnimation();

            screen.drawImage(animation != null ? animation.getCurrentFrame() : model.getImage(), pos.x, pos.y, getDirection());
        }
    }

    public boolean connectedTo(Entity other) {
        Entity topLevel = getTopLevelParent();
        boolean result = topLevel.recurseAllChildren().contains(other);

        return result;
    }

    public List<Entity> recurseAllChildren() {
        List<Entity> result = new ArrayList<>();

        result.add(this);

        for (int i = 0; i < children.size(); i++) {
            Entity ent = children.get(i);
            result.addAll(ent.recurseAllChildren());
        }

        return result;
    }

    public Entity getTopLevelParent() {
        Entity parent = this;

        while (parent.parent != null) {
            parent = parent.parent;
        }

        return parent;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isDead() {
        return dead;
    }

    public void attach(Entity parent, float dir, float dist) {
        this.parent = parent;
        this.angleOffset = (float) Math.toRadians(dir);
        this.distanceOffset = dist;
        if (this.zOffset <= parent.zOffset) {
            this.zOffset = parent.zOffset + 1;
        }
        parent.children.add(this);
    }

    public void detach() {
        if (parent != null) {
            parent.children.remove(this);
            this.angleOffset = 0;
            this.distanceOffset = 0;
            parent = null;
        }
    }

    public int getZOffset() {
        return zOffset;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getTeam() {
        return team;
    }

    public final void setAnimation(Animation animation, boolean setSize) {
        this.animation = animation;
        if (setSize) {
            this.collisionPolygon = Resources.spriteToPolygon(animation.getCurrentFrame(), 32);
        }
    }

    public final Animation getAnimation() {
        return animation;
    }

    public Model getModel() {
        return model;
    }

    public List<Entity> getChildren() {
        return children;
    }

    public final void setModel(Model model) {
        this.model = model;

        if (model.getCollission() != null) {
            this.collisionPolygon = model.getCollission();
        }

        if (model.getAnimation() != null) {
            this.animation = model.getAnimation();
        }
    }

    public void setCollisionPolygon(Polygon collisionPolygon) {
        this.collisionPolygon = collisionPolygon;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isStaticObject() {
        return staticObject;
    }

    public void setStaticObject(boolean staticObject) {
        this.staticObject = staticObject;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
