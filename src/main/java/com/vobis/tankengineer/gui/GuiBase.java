package com.vobis.tankengineer.gui;

import com.vobis.tankengineer.render.Screen;

/**
 *
 * @author Shaun
 */
public abstract class GuiBase {

    protected final Component[] components = new Component[50];

    public void actionPerformed(int id, Component component) {

    }

    public void addComponent(int id, Component component) {
        components[id] = component;
        component.setId(id);
        component.parent = this;
    }

    public void render(Screen screen) {
        for (Component component : components) {
            if (component != null) {
                component.render(screen);
            }
        }
    }

    public void update() {
        for (Component component : components) {
            if (component != null) {
                component.update();
            }
        }
    }

    public void mouseMoved(int x, int y) {
        for (Component c : components) {
            if (c != null) {
                if (x > c.x && x < c.x + c.width && y > c.y && y < c.y + c.height) {
                    c.setHovered(true);
                } else {
                    c.setHovered(false);
                }
            }
        }
    }

    public void mouseClicked(int x, int y, int clickCount) {
        for (Component c : components) {
            if (c != null) {
                if (x > c.x && x < c.x + c.width && y > c.y && y < c.y + c.height) {
                    c.mouseClicked(x, y, clickCount);
                }
            }
        }
    }

    public boolean blocksInput() {
        return false;
    }
}
