package com.vobis.tankengineer.gui;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.circuit.Circuit;
import com.vobis.tankengineer.render.Screen;

/**
 * @author Shaun
 */
public class GuiCircuit extends GuiBase {

    private final Circuit circuit;

    public GuiCircuit(Circuit circuit) {
        this.circuit = circuit;

        addComponent(0, new ComponentLabel("Editing Circuit", 20));
        addComponent(1, new ComponentButton("Done", 50, 50));
    }

    @Override
    public void update() {
        super.update();
        circuit.update();
    }

    @Override
    public void render(Screen screen) {
        circuit.render(screen);
        super.render(screen);
    }

    @Override
    public void actionPerformed(int id, Component component) {
        switch (id) {
            case 1:
                TankEngineer.gameInstance.popGui();
                break;
        }
    }

    @Override
    public boolean blocksInput() {
        return true;
    }
}
