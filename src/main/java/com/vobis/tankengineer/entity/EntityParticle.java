package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.render.Animation;
import com.vobis.tankengineer.render.Screen;

/**
 *
 * @author Shaun
 */
public class EntityParticle extends Entity {

    public EntityParticle(Animation animation) {
        this.animation = animation;
        zOffset = 9;
        animation.setLoop(false);
        setSolid(false);
        mass = 0;
        size = 0;
    }

    @Override
    public void update() {
        super.update();

        if (animation.isFinished()) {
            setDead(true);
        }
    }

    @Override
    public void render(Screen screen) {
        screen.drawImage(animation.getCurrentFrame(), pos.x, pos.y);
    }
}
