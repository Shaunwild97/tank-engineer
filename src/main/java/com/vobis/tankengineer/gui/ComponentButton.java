/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.gui;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.render.Screen;

/**
 *
 * @author Shaun
 */
public class ComponentButton extends ComponentLabel {

    private int paddingX = 10, paddingY = 10;

    private int textWidth, textHeight;

    public ComponentButton(String text, int x, int y) {
        super(text, x, y);

        setShadow(true);

        textWidth = width;
        textHeight = height;

        this.width = TankEngineer.gameInstance.screen.stringWidth(font, text) + paddingX * 2;
        this.height = TankEngineer.gameInstance.screen.stringHeight(font, text) + paddingY * 2;
    }

    public ComponentButton(String text, int y) {
        this(text, 0, y);
        this.centered = true;

        this.x = TankEngineer.gameInstance.container.getWidth() / 2 - width / 2;
    }

    public void setPaddingX(int paddingX) {
        this.paddingX = paddingX;
    }

    public void setPaddingY(int paddingY) {
        this.paddingY = paddingY;
    }

    @Override
    public void render(Screen screen) {
        screen.drawRect(x, y, width, height, hovered ? 0xAAAAAA : 0x777777);

        if (shadow) {
            screen.drawShadowedString(text, x + width / 2 - textWidth / 2, y + height / 2 - textHeight / 2, 0xFFFFFF);
        } else {
            screen.drawString(text, x + width / 2 - textWidth / 2, y + height / 2 - textHeight / 2, 0xFFFFFF);
        }
    }

    @Override
    public void mouseClicked(int x, int y, int clickCount) {
        parent.actionPerformed(id, this);
    }
}
