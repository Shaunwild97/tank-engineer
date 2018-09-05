package com.vobis.tankengineer;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

/**
 *
 * @author Shaun
 */
public class TankEngineerStartup {

    public static void main(String[] args) {
        try {
            AppGameContainer container = new AppGameContainer(new TankEngineer(), 1024, 512, false);
            container.setVSync(true);
            container.start();
        } catch (SlickException ex) {
        }
    }
}
