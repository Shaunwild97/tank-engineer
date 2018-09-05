/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.render;

import com.vobis.tankengineer.Vector2;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

import java.util.function.Consumer;

/**
 *
 * @author Shaun
 */
public class Resources {

    public static final int BEST_PRECISION = 18;
    public static final int LOW_PRECISION = 12;
    public static final int ULTRA_PRECISION = 90;

    public static final Consumer<SpriteSheet> SET_NEAREST_FILTER = e -> e.setFilter(Image.FILTER_NEAREST);

    public static final SpriteSheet KV2 = loadSpriteSheet("tanks/KV-2_strip2.png", 132, 254);
    public static final SpriteSheet VK3601h = loadSpriteSheet("tanks/VK.3601h_strip2.png", 127, 243);
    public static final SpriteSheet PzKpfw = loadSpriteSheet("tanks/Pz.Kpfw.IV-G_strip2.png", 129, 253);
    public static final SpriteSheet targetFinder = loadSpriteSheet("target_finder.png", 64, 64);
    public static final SpriteSheet E100 = loadSpriteSheet("tanks/E-100_strip2.png", 96, 207);
    public static final SpriteSheet laser = loadSpriteSheet("laser_turret.png", 64, 64);
    public static final SpriteSheet hovered = loadSpriteSheet("selection.png", 10, 10);
    public static final SpriteSheet rocks = loadSpriteSheet("rocks.png", 128, 128);
    public static final SpriteSheet alienShip = loadSpriteSheet("tanks/AlienShip.png", 173, 291);
    public static final SpriteSheet PzKpfw_cannon = loadSpriteSheet("tanks/Pz.Kpfw.IV_cannon.png", 129, 253);

    public static final SpriteSheet explosion = loadSpriteSheet("explosion.png", 64, 64);
    public static final SpriteSheet explosion6 = loadSpriteSheet("explosions/explosion6.png", 256, 256);
//    public static final SpriteSheet explosion1 = loadSpriteSheet("explosions/explosion1.png", 256, 256);

    public static final Image emptyModule = loadImage("moduleSlot.png");
    public static final Image machineGun = loadImage("tanks/machine_gun.png");
    public static final Image tankShell = loadImage("tank_shell.png");
    public static final Image bullet = loadImage("bullet.png");
    public static final Image floor = loadImage("terrain.png");
    public static final Image locator = loadImage("locator.png");
    public static final Image drone = loadImage("drone.png");
    public static final Image concrete = loadImage("tiles/concrete.png");

    public static final Image loadImage(String location) {
        Image result;

        try {
            result = new Image(ClassLoader.getSystemResourceAsStream(location), location, false);
        } catch (SlickException ex) {
            throw new RuntimeException("Failed to load image: " + ex.getMessage());
        }

        return result;
    }

    public static final SpriteSheet loadSpriteSheet(String location, int tx, int ty) {
        SpriteSheet result = new SpriteSheet(loadImage(location), tx, ty);
        return result;
    }

    public static final SpriteSheet loadSpriteSheet(String location, int tx, int ty, Consumer<SpriteSheet> preprocessor) {
        SpriteSheet result = loadSpriteSheet(location, tx, ty);
        preprocessor.accept(result);
        return result;
    }

    public static final SpriteSheet loadSpriteSheet(Image img, int tx, int ty, Consumer<SpriteSheet> preprocessor) {
        SpriteSheet result = new SpriteSheet(img, tx, ty);
        preprocessor.accept(result);
        return result;
    }

    public static final Image loadImage(String location, Consumer<Image> postprocessor) {
        Image result = loadImage(location);
        postprocessor.accept(result);
        return result;
    }

    public static final Sound loadSound(String location) {
        Sound result;
        try {
            result = new Sound(Resources.class.getResource(location));
        } catch (SlickException ex) {
            return null;
        }

        return result;
    }

    public static final Image getImage(String name) {
        try {
            return (Image) Resources.class.getField(name).get(null);
        } catch (Exception ex) {
            return null;
        }
    }

    public static final Polygon spriteToPolygon(Image sprite, int precision) {
        Polygon result = new Polygon();

        int iterations = 360 / precision;

        for (int i = 0; i <= 360; i += iterations) {
            double rad = Math.toRadians(i);
            Vector2 vec = new Vector2((float) Math.sin(rad), (float) Math.cos(rad));
            Vector2 origin = new Vector2(sprite.getWidth() / 2, sprite.getHeight() / 2);

            int lastAlpha = 255;
            Vector2 edge = null;

            for (int j = 0; j < sprite.getWidth() + sprite.getHeight(); j++) {
                Vector2 current = origin.add(vec);

                if ((int) current.x < 0 || (int) current.y < 0 || (int) current.x >= sprite.getWidth() || (int) current.y >= sprite.getHeight()) {
                    break;
                }

                int alpha = sprite.getColor((int) current.x, (int) current.y).getAlpha();

                if (alpha < 100) {
                    if (lastAlpha > 100) {
                        edge = current.copy();
                    }
                }

                lastAlpha = alpha;
            }

            if (edge != null) {
                result.addPoint(edge.x - sprite.getWidth() / 2, edge.y - sprite.getHeight() / 2);
            }
        }

        return result;
    }
}
