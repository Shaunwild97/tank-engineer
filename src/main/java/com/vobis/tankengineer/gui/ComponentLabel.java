/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.gui;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.render.Screen;
import org.newdawn.slick.Font;
import org.newdawn.slick.Image;

/**
 *
 * @author Shaun
 */
public class ComponentLabel extends Component {

    protected String text;
    protected Image sprite;
    protected boolean centered;
    protected boolean shadow;
    protected Font font = Screen.GAME_FONT;

    public ComponentLabel(String text, int y) {
        this(text, 0, y);
        this.centered = true;
    }

    public ComponentLabel(Image sprite, int y) {
        this(sprite, 0, y);
        this.centered = true;
    }

    public ComponentLabel(String text, int x, int y) {
        this.x = x;
        this.y = y;

        setText(text);
    }

    @Override
    public void update() {
        if (centered) {
            this.x = TankEngineer.gameInstance.container.getWidth() / 2 - width / 2;
        }
    }

    public ComponentLabel(Image sprite, int x, int y) {
        this((String) null, x, y);

        setSprite(sprite);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        updateSize();
    }

    public boolean isShadow() {
        return shadow;
    }

    public void setShadow(boolean shadow) {
        this.shadow = shadow;
    }

    public void setSprite(Image sprite) {
        this.sprite = sprite;
        updateSize();
    }

    public Image getSprite() {
        return sprite;
    }

    public boolean isCentered() {
        return centered;
    }

    public void setFont(Font font) {
        this.font = font;
        updateSize();
    }

    public Font getFont() {
        return font;
    }

    public void updateSize() {
        int textWidth = 0, textHeight = 0;
        int imageWidth = 0, imageHeight = 0;

        if (text != null) {
            textWidth = TankEngineer.gameInstance.screen.stringWidth(font, text);
            textHeight = TankEngineer.gameInstance.screen.stringHeight(font, text);
        }

        if (sprite != null) {
            imageWidth = sprite.getWidth();
            imageHeight = sprite.getHeight();
        }

        this.width = textWidth > imageWidth ? textWidth : imageWidth;
        this.height = textHeight > imageHeight ? textHeight : imageHeight;
    }

    @Override
    public void render(Screen screen) {
        if (shadow) {
            screen.drawShadowedString(text, x, y, 0xFFFFFF, font);
        } else {
            screen.drawString(text, x, y, 0xFFFFFF, font);
        }
    }
}
