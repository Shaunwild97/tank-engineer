package com.vobis.tankengineer.level;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.entity.EntityModular;
import org.newdawn.slick.Input;

/**
 *
 * @author Shaun
 */
public class TankCreatorLevel extends Level {

    private EntityModular grabbed;

    public TankCreatorLevel() {
        setLevelType(LevelType.TANK_CREATOR);
    }

    @Override
    public void update() {
        super.update();

        if (hovered != null) {
            if (TankEngineer.gameInstance.input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
                if (hovered instanceof EntityModular) {
                    grabbed = (EntityModular) hovered;

                    if(!grabbed.isMoveable()) {
                        grabbed = null;
                    } else if (grabbed.parent != null) {
                        grabbed.detach();
                    }
                }
            } else {
                grabbed = null;
            }
        } else {
            grabbed = null;
        }

        if (grabbed != null) {
            grabbed.pos.set(TankEngineer.gameInstance.screen.toWorldSpace(TankEngineer.gameInstance.getMousePosition()));
        }
    }
}
