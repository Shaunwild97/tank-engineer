/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.circuit;

import com.vobis.tankengineer.entity.EntityModular;
import com.vobis.tankengineer.render.Screen;

import java.util.function.Consumer;

/**
 *
 * @author Shaun
 */
public class NodeDefinition<T extends EntityModular> {

    private String name;
    private String shortName;
    private String[] inputs = new String[0];
    private String[] outputs = new String[0];
    private int width, height;
    private Consumer<NodeInstance<T>> action;
    private Consumer<NodePort> operation;

    public String[] getInputs() {
        return inputs;
    }

    public String[] getOutputs() {
        return outputs;
    }

    public String getName() {
        return name;
    }

    public Consumer<NodeInstance<T>> getAction() {
        return action;
    }

    public String getShortName() {
        return shortName;
    }

    public NodeDefinition<T> name(String name) {
        this.name = name;
        return this;
    }

    public NodeDefinition<T> inputs(String... inputs) {
        this.inputs = inputs;
        return this;
    }

    public NodeDefinition<T> outputs(String... outputs) {
        this.outputs = outputs;
        return this;
    }

    public NodeDefinition<T> action(Consumer<NodeInstance<T>> action) {
        this.action = action;
        return this;
    }

    public NodeDefinition<T> shortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public void render(Screen screen, float x, float y) {
        screen.drawRect(x, y, 100, 100, 0x636363);
        screen.drawRect(x, y, 100, 20, 0x939393);
        screen.drawShadowedString(getName(), x + 2, y + 2, 0xFFFFFF);

        for (int i = 0; i < inputs.length; i++) {
            screen.drawRect(x, 30 + y + i * 15, 5, 5, 0xDDDDDD);
            screen.drawString(inputs[i], x + 10, 25 + y + i * 15, 0xDDDDDD);
        }

        y += inputs.length * 15;

        for (int i = 0; i < outputs.length; i++) {
            screen.drawRect(x + 95, 30 + y + i * 15, 5, 5, 0xDDDDDD);
            screen.drawString(outputs[i], x + 90 - screen.stringWidth(Screen.GAME_FONT, outputs[i]), 25 + y + i * 15, 0xDDDDDD);
        }
    }
}
