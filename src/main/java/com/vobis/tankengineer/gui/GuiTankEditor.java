package com.vobis.tankengineer.gui;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.entity.EntityModular;
import com.vobis.tankengineer.serialize.VehicleIO;

/**
 *
 * @author Shaun
 */
public class GuiTankEditor extends GuiBase {

    private EntityModular vehicle;
    private String name;

    public GuiTankEditor(String name, EntityModular vehicle) {
        this.name = name;
        this.vehicle = vehicle;

        addComponent(4, new ComponentLabel("Editing " + name, 50, 50));
        addComponent(0, new ComponentButton("Edit Circuit", 50, 100));
        addComponent(1, new ComponentButton("Save", 50, 150));
        addComponent(2, new ComponentButton("Cancel", 50, 200));
    }

    @Override
    public void actionPerformed(int id, Component component) {
        switch (id) {
            case 0:
                TankEngineer.gameInstance.addGUI(new GuiCircuit(vehicle.getCircuit()));
                break;
            case 1:
                VehicleIO.saveVehicle(vehicle, name);
                break;
            case 2:
                TankEngineer.gameInstance.setGUI(new GuiStartMenu());
                TankEngineer.gameInstance.setGameWorld(null);
                break;
        }
    }
}
