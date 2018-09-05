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
public class GuiEditorTankSelection extends GuiBase {

    public GuiEditorTankSelection() {
        ComponentLabel title;
        addComponent(0, title = new ComponentLabel("Editor", 100, 100));
        addComponent(1, new ComponentButton("New", 100, 175));

        title.setFont(Screen.GAME_FONT_LARGE);

        addLoadedVehicleButtons(VehicleIO.fetchAllVehicles());
    }

    @Override
    public void actionPerformed(int id, Component component) {
        super.actionPerformed(id, component);

        Level gameWorld = new TankCreatorLevel();
        TankEngineer.gameInstance.setGameWorld(gameWorld);
        EntityModular vehicle;
        String name;

        if (id == 1) {
            EntityTank testTank = new EntityTank();
            testTank.setModel(ModelLoader.loadModel("PzKpfw_Body"));
            gameWorld.addEntity(testTank, 100, 100);

            EntityCannon cannon = new EntityCannon();
            cannon.setModel(ModelLoader.loadModel("PzKpfw_Cannon"));

            testTank.insertModule(0, cannon);

            EntityLocator locator = new EntityLocator();
            cannon.insertModule(0, locator);

            EntityTargetFinder targetFinder = new EntityTargetFinder();
            testTank.insertModule(2, targetFinder);

            vehicle = testTank;
            name = (System.currentTimeMillis() / 1000) + "Vehicle";
        } else {
            name = ((ComponentButton) components[id]).getText();
            vehicle = VehicleIO.loadVehicle(name);
        }

        gameWorld.addEntity(vehicle, 0, 0);
        TankEngineer.gameInstance.trackedEntity = vehicle;
        TankEngineer.gameInstance.setGUI(new GuiTankEditor(name, vehicle));
    }

    public void addLoadedVehicleButtons(List<String> vehicles) {
        for (int i = 0; i < vehicles.size(); i++) {
            String vehicle = vehicles.get(i).replace(".json", "");
            addComponent(i + 2, new ComponentButton(vehicle, 100, 225 + (50 * i)));
        }
    }
}
