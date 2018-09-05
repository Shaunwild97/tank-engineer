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
public class GuiStartMenu extends GuiBase {

    public GuiStartMenu() {
        ComponentLabel title;
        addComponent(4, title = new ComponentLabel("Tank Engineer", 100, 100));

        title.setFont(Screen.GAME_FONT_LARGE);

        addComponent(0, new ComponentButton("Start Campaign", 100, 175));
        addComponent(1, new ComponentButton("Editor", 100, 225));
        addComponent(2, new ComponentButton("Options", 100, 275));
        addComponent(3, new ComponentButton("Exit", 100, 325));
    }

    @Override
    public void actionPerformed(int id, Component component) {
        switch (id) {
            case 0:
                TankEngineer.gameInstance.setGUI(new GuiTankSelection());
                break;
            case 1:
                TankEngineer.gameInstance.setGUI(new GuiEditorTankSelection());
                break;
            case 2:
                break;
            case 3:
                System.exit(0);
                break;
        }
    }

}
