package com.vobis.tankengineer.render;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.Vector2;
import com.vobis.tankengineer.entity.Entity;
import com.vobis.tankengineer.gui.GuiBase;
import org.newdawn.slick.*;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

/**
 *
 * @author Shaun
 */
public class Screen {

    public static final Font GAME_FONT = new TrueTypeFont(new java.awt.Font("Consolas", java.awt.Font.BOLD, 14), true);
    public static final Font GAME_FONT_MEDIUM = new TrueTypeFont(new java.awt.Font("Consolas", java.awt.Font.BOLD, 20), true);
    public static final Font GAME_FONT_LARGE = new TrueTypeFont(new java.awt.Font("Consolas", java.awt.Font.BOLD, 48), true);

    public Graphics graphics;
    public float scale = 4f;
    public float wantedScale = 1f;
    public float xScroll, yScroll;

    public float cameraShake;

    public void update(TankEngineer game) {
        xScroll += game.rand.nextGaussian() * cameraShake;
        yScroll += game.rand.nextGaussian() * cameraShake;

        cameraShake *= .9f;

        if (cameraShake < .1f) {
            cameraShake = 0f;
        }
    }

    public void render(Graphics g, TankEngineer game) {
        this.graphics = g;

        g.translate(game.container.getWidth() / 2, game.container.getHeight() / 2);
        g.scale(scale, scale);
        g.translate(-xScroll, -yScroll);

        int width = game.container.getWidth();
        int height = game.container.getHeight();

        if (game.gameWorld != null) {
            int tSize = 512;

            float nx = width / tSize / scale;
            float ny = height / tSize / scale;

            int sx = (int) (xScroll / tSize);
            int sy = (int) (yScroll / tSize);

            for (int i = sx - 10; i < sx + nx + 2; i++) {
                for (int j = sy - 10; j < sy + ny + 2; j++) {
                    drawImage(Resources.concrete, i * tSize, +j * tSize);
                }
            }

            for (int i = 0; i < game.gameWorld.entities.size(); i++) {
                Entity e = game.gameWorld.entities.get(i);
                e.render(this);
            }
        }

        if (game.hovered != null) {
            Polygon polygon = game.hovered.getCollisionPolygon();

            float minX = polygon.getMinX();
            float minY = polygon.getMinY();
            float maxX = polygon.getMaxX();
            float maxY = polygon.getMaxY();

            drawImage(Resources.hovered.getSprite(0, 0), minX, minY);
            drawImage(Resources.hovered.getSprite(1, 0), maxX, minY);
            drawImage(Resources.hovered.getSprite(0, 1), minX, maxY);
            drawImage(Resources.hovered.getSprite(1, 1), maxX, maxY);
        }

        g.resetTransform();

        for (GuiBase gui : game.guiStack) {
            gui.render(this);
        }
    }

    public Vector2 toWorldSpace(int x, int y) {
        GameContainer container = TankEngineer.gameInstance.container;
        return new Vector2((x - container.getWidth() / 2) / scale + xScroll, (y - container.getHeight() / 2) / scale + yScroll);
    }

    public Vector2 toWorldSpace(Vector2 v) {
        GameContainer container = TankEngineer.gameInstance.container;
        return new Vector2((v.x - container.getWidth() / 2) / scale + xScroll, (v.y - container.getHeight() / 2) / scale + yScroll);
    }

    public Vector2 toScreenSpace(float x, float y) {
        GameContainer container = TankEngineer.gameInstance.container;
        return new Vector2((x + container.getWidth() / 2) * scale - xScroll, (y + container.getHeight() / 2) * scale - yScroll);
    }

    public void drawImage(Image img, float x, float y) {
        drawImage(img, x, y, 0, 1f);
    }

    public void drawImage(Image img, float x, float y, float dir) {
        drawImage(img, x, y, dir, 1f);
    }

    public void drawImage(Image img, float x, float y, float dir, float transparency) {
        float ix = img.getWidth() / 2;
        float iy = img.getHeight() / 2;

        graphics.rotate(x, y, dir);
        graphics.drawImage(img, x - ix, y - iy, new Color(1, 1, 1, transparency));
        graphics.rotate(x, y, -dir);
    }

    public void drawRect(float x, float y, int width, int height, int color) {
        graphics.setColor(new Color(color));
        graphics.fillRect(x, y, width, height);
    }

    public void drawString(String str, float x, float y, int color) {
        graphics.setFont(GAME_FONT);
        graphics.setColor(new Color(color));
        graphics.drawString(str, x, y);
    }

    public void drawShadowedString(String str, float x, float y, int color) {
        graphics.setFont(GAME_FONT);
        drawString(str, x + 2, y + 2, 0x222222);
        drawString(str, x, y, color);
    }

    public void drawString(String str, float x, float y, int color, Font font) {
        graphics.setFont(font);
        graphics.setColor(new Color(color));
        graphics.drawString(str, x, y);
    }

    public void drawShadowedString(String str, float x, float y, int color, Font font) {
        graphics.setFont(font);
        drawString(str, x + 2, y + 2, 0x222222);
        drawString(str, x, y, color);
    }

    public int stringWidth(Font font, String str) {
        return font.getWidth(str);
    }

    public int stringHeight(Font font, String str) {
        return font.getHeight(str);
    }

    public void drawLine(float x1, float y1, float x2, float y2, int colour) {
        graphics.setColor(new Color(colour));
        graphics.setLineWidth(1f);
        graphics.drawLine(x1, y1, x2, y2);
    }

    public void clerpLine(float x, float y, float x1, float y1, int color) {
        graphics.setLineWidth(3f);
        graphics.setColor(new Color(color));
        Polygon polygon = new Polygon();
        polygon.setClosed(false);
        float precision = 15f;
        polygon.addPoint(x, y);

        for (int j = 0; j < precision; j++) {
            polygon.addPoint((int) lerp(x, x1, j / precision), (int) clerp(y, y1, j / precision));
        }

        polygon.addPoint(x1, y1);
        graphics.draw(polygon);
    }

    public void backClerpLine(float x, float y, float x1, float y1, int color) {
        graphics.setLineWidth(3f);
        graphics.setColor(new Color(color));
        Polygon polygon = new Polygon();
        polygon.setClosed(false);
        float precision = 20f;
        polygon.addPoint(x, y);

        for (int j = 0; j < precision; j++) {
            polygon.addPoint((int) clerp(x * 1.05, x1 * .95, j / precision), (int) lerp(y, y1, j / precision));
        }

        polygon.addPoint(x1, y1);
        graphics.draw(polygon);
    }

    public double clerp(double y1, double y2, double mu) {
        double mu2;
        mu2 = (1 - Math.cos(mu * Math.PI)) / 2;
        return (y1 * (1 - mu2) + y2 * mu2);
    }

    public double slerp(double y1, double y2, double mu) {
        double mu2;
        mu2 = (1 - Math.sin(mu * Math.PI)) / 2;
        return (y1 * (1 - mu2) + y2 * mu2);
    }

    public float lerp(float v0, float v1, float t) {
        return (1 - t) * v0 + t * v1;
    }

    double cosineInterpolate(double y1, double y2, double mu) {
        double mu2;
        mu2 = (1 - Math.cos(mu * Math.PI)) / 2;

        return (y1 * (1 - mu2) + y2 * mu2);
    }

    public void drawShape(Shape shape, int colour) {
        graphics.setColor(new Color(colour));
        graphics.draw(shape);
    }

    public static Vector2 getImageBounds(Image image) {
        int minX = image.getWidth(), minY = image.getHeight();
        int maxX = 0, maxY = 0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                if (image.getColor(i, j).getAlpha() > 0) {
                    if (i < minX) {
                        minX = i;
                    } else if (i > maxX) {
                        maxX = i;
                    }

                    if (j < minY) {
                        minY = j;
                    } else if (j > maxY) {
                        maxY = j;
                    }
                }

            }
        }

        return new Vector2(maxX - minX, maxY - minY);
    }
}
