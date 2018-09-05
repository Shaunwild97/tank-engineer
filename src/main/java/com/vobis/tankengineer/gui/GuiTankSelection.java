/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.gui;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.entity.*;
import com.vobis.tankengineer.level.Level;
import com.vobis.tankengineer.level.TankCreatorLevel;
import com.vobis.tankengineer.render.ModelLoader;
import com.vobis.tankengineer.render.Screen;
import com.vobis.tankengineer.serialize.VehicleIO;

import java.util.List;

/**
 *
 * @author Shaun
 */
public class GuiTankSelection extends GuiBase {

    public GuiTankSelection() {
        ComponentLabel title;
        addComponent(0, title = new ComponentLabel("Editor", 100, 100));
        title.setFont(Screen.GAME_FONT_LARGE);

        addLoadedVehicleButtons(VehicleIO.fetchAllVehicles());
    }

    @Override
    public void actionPerformed(int id, Component component) {
        super.actionPerformed(id, component);

        TankEngineer.gameInstance.initTestWorld();

        String name = ((ComponentButton) components[id]).getText();
        EntityModular vehicle = VehicleIO.loadVehicle(name);


        TankEngineer.gameInstance.gameWorld.addEntity(vehicle, 0, 0);
        TankEngineer.gameInstance.trackedEntity = vehicle;

        TankEngineer.gameInstance.setGUI(null);
    }

    public void addLoadedVehicleButtons(List<String> vehicles) {
        for (int i = 0; i < vehicles.size(); i++) {
            String vehicle = vehicles.get(i).replace(".json", "");
            addComponent(i + 2, new ComponentButton(vehicle, 100, 225 + (50 * i)));
        }
    }
}
