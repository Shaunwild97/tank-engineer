package com.vobis.tankengineer.circuit;

import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.Vector2;
import com.vobis.tankengineer.entity.EntityModular;
import com.vobis.tankengineer.render.Screen;
import com.vobis.tankengineer.serialize.VehicleIO;
import org.newdawn.slick.Input;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shaun
 */
public class Circuit {

    public EntityModular owner;
    private final List<NodeInstance> nodes = new ArrayList<>();
    private NodeInstance selected;
    private float x, y;
    private float anchorX, anchorY;
    public int selectedIndex = -1;
    private Vector2 mousePos;
    private NodeInstance hovered;
    private int hoveredPort;
    private int nextId;

    public Circuit(EntityModular owner) {
        this.owner = owner;
    }

    public Circuit() {
    }

    public void initModuleNodes() {
        if (!circuitHasModule(owner)) {
            addNode(owner.getCircuitNode(), owner, 0, 0);
        }

        List<EntityModular> modules = owner.getChildModules();
        for (int i = 0; i < modules.size(); i++) {
            EntityModular module = modules.get(i);

            if (!circuitHasModule(module)) {
                addNode(module.getCircuitNode(), module, 110 + i * 110, 110 + i * 110);
            }
        }
    }

    public List<EntityModular> recurseAllChildren(EntityModular module) {
        List<EntityModular> result = new ArrayList<>();

        List<EntityModular> children = module.getChildModules();

        for (int i = 0; i < children.size(); i++) {
            EntityModular ent = children.get(i);
            result.add(ent);
            result.addAll(recurseAllChildren(ent));
        }

        return result;
    }

    public void update() {
        for (int i = 0; i < nodes.size(); i++) {
            nodes.get(i).update();
        }

        int x = TankEngineer.gameInstance.input.getMouseX();
        int y = TankEngineer.gameInstance.input.getMouseY();

        mousePos = new Vector2(x, y);

        hovered = getNodeUnder(mousePos.x, mousePos.y, false);

        if (hovered != null) {
            hoveredPort = getNodePort(hovered, false);
        } else {
            hoveredPort = -1;
        }

        if (TankEngineer.gameInstance.input.isKeyPressed(Input.KEY_S)) {
            VehicleIO.saveVehicle(owner, (System.currentTimeMillis() / 1000) + "Vehicle");
        }

        if (TankEngineer.gameInstance.input.isKeyPressed(Input.KEY_DELETE)) {
            NodeInstance toDelete = hovered;

            if (toDelete != null && toDelete.getOwner() == null) {
                nodes.remove(toDelete);
            }
        }

        if (TankEngineer.gameInstance.input.isKeyPressed(Input.KEY_A)) {
            NodeDefinition nodeDefinition = Nodes.getNode(JOptionPane.showInputDialog("Enter name of node to spawn"));

            if (nodeDefinition != null) {
                addNode(nodeDefinition, mousePos.x - this.x, mousePos.y - this.y);
            }
        }

        if (TankEngineer.gameInstance.input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
            if (selected != null) {
                if (selectedIndex == -1) {
                    selected.setX(mousePos.x - this.x - anchorX);
                    selected.setY(mousePos.y - this.y - anchorY);
                } else {
                    anchorX = mousePos.x;
                    anchorY = mousePos.y;
                }
            } else {
                selected = getNodeUnder(mousePos.x, mousePos.y, true);

                if (selected != null) {
                    anchorX = mousePos.x - this.x - selected.getX();
                    anchorY = mousePos.y - this.y - selected.getY();
                } else {
                    setNodePort(false);
                }
            }
        } else {
            if (selectedIndex > -1) {
                setNodePort(true);
                selectedIndex = -1;
            }

            selected = null;
        }
    }

    public NodeInstance getNodeUnder(float x, float y, boolean top) {
        for (int i = 0; i < nodes.size(); i++) {
            NodeInstance node = nodes.get(i);

            float nx = node.getX() + this.x;
            float ny = node.getY() + this.y;

            if (x > nx && x < nx + 100 && y > ny && y < ny + (top ? 20 : 100)) {
                return node;
            }
        }

        return null;
    }

    public void setNodePort(boolean input) {
        for (int i = 0; i < nodes.size(); i++) {
            NodeInstance node = nodes.get(i);

            int port = getNodePort(node, input);

            if (port > -1) {
                if (input) {
                    NodeInstance other = getNodeUnder(mousePos.x, mousePos.y, false);
                    selected.connectTo(other, selectedIndex, port);
                } else {
                    selected = node;
                    selectedIndex = port;
                }
            }

        }
    }

    public int getNodePort(NodeInstance node, boolean input) {
        float offset = (input ? 0 : node.getInputs().length * 15) + 25;

        for (int j = 0; j < (input ? node.getInputs() : node.getOutputs()).length; j++) {
            float nx = node.getX() + this.x + (input ? 0 : 90);
            float ny = node.getY() + this.y + offset + j * 15;

            if (mousePos.x > nx && mousePos.x < nx + 15 && mousePos.y > ny && mousePos.y < ny + 15) {
                return j;
            }
        }
        return -1;
    }

    public boolean circuitHasModule(EntityModular module) {
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i).getOwner() == module) {
                return true;
            }
        }

        return false;
    }

    public void addNode(NodeDefinition def, float x, float y) {
        addNode(def, null, x, y);
    }

    public void addNode(NodeDefinition def, EntityModular owner, float x, float y) {
        NodeInstance instance;
        nodes.add(instance = new NodeInstance(def));
        instance.setId(nextId++);
        instance.setX(x);
        instance.setY(y);

        if (owner != null) {
            instance.setOwner(owner);
            instance.setOwnerId(owner.getId());
        }
    }

    public NodeInstance getNodeById(int id) {
        for (NodeInstance node : nodes) {
            if (node.getId() == id) {
                return node;
            }
        }

        return null;
    }

    public List<NodeInstance> getNodes() {
        return nodes;
    }

    public void setOwner(EntityModular owner) {
        this.owner = owner;
    }

    public void render(Screen screen) {
        screen.drawRect(x, y, 1200, 600, 0xCC9F9F9F);

        for (int i = 0; i < nodes.size(); i++) {
            NodeInstance node = nodes.get(i);
            node.render(screen, x, y);
        }

        if (selected != null) {
            if (selectedIndex > -1) {
                float offset = selected.getInputs().length * 15 + selectedIndex * 15 + 30;
                screen.clerpLine(x + selected.getX() + 97, y + selected.getY() + offset + 2, mousePos.x, mousePos.y, 0xFFFFFF);
            }
        }

        if (hovered != null) {
            if (hoveredPort > -1) {
                Object value = hovered.getOutput(hoveredPort);

                if (value != null) {
                    screen.drawShadowedString("Node Output: " + value, mousePos.x + 5, mousePos.y, 0xFFFFFF);
                }
            }
        }
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
