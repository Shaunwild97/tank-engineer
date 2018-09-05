package com.vobis.tankengineer.circuit;

import com.vobis.tankengineer.entity.EntityModular;
import com.vobis.tankengineer.render.Screen;

/**
 *
 * @author Shaun
 */
public class NodeInstance<T extends EntityModular> {

    private float x, y;
    private T owner;
    private String ownerId;
    private NodePort[] inputs;
    private Object[] outputs;
    private NodeDefinition node;
    private int id;

    public NodeInstance(NodeDefinition node) {
        this.node = node;
        inputs = new NodePort[node.getInputs().length];
        outputs = new Object[node.getOutputs().length];

        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = new NodePort(node.getInputs()[i]);
            inputs[i].setIndex(i);
        }
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void connectTo(NodeInstance other, int output, int input) {
        other.getInputs()[input].setNode(output, this);
    }

    public void update() {
        node.getAction().accept(this);
    }

    public void setInput(int index, Object value) {
        inputs[index].setValue(value);
    }

    public Object getInput(int index) {
        return inputs[index].getValue();
    }

    public <R> R getInput(Class<R> type, int index) {
        if (inputActive(index)) {
            if (type.isAssignableFrom(inputs[index].getValue().getClass())) {
                return type.cast(inputs[index].getValue());
            }

            return (R) attemptCast(inputs[index].getValue(), type);
        }

        return null;
    }

    public Object attemptCast(Object obj, Class type) {
        if (obj instanceof Boolean) {
            if (type.equals(Float.class)) {
                return ((boolean) obj) ? 1f : 0f;
            } else if (type.equals(String.class)) {
                return ((Boolean) obj).toString();
            }
        }
        return null;
    }

    public boolean inputActive(int index) {
        return inputs[index].isActive();
    }

    public NodePort[] getInputs() {
        return inputs;
    }

    public Object[] getOutputs() {
        return outputs;
    }

    public void setOutput(int index, Object value) {
        outputs[index] = value;
    }

    public Object getOutput(int index) {
        return outputs[index];
    }

    public NodeDefinition getNode() {
        return node;
    }

    public void setNode(NodeDefinition node) {
        this.node = node;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public T getOwner() {
        return owner;
    }

    public void setOwner(T owner) {
        this.owner = owner;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void render(Screen screen, float x, float y) {
        float mx = this.x + x;
        float my = this.y + y;

        node.render(screen, mx, my);

        for (int i = 0; i < inputs.length; i++) {
            NodePort port = inputs[i];

            if (port.getNode() != null) {
                NodeInstance node = port.getNode();

                screen.clerpLine(mx + 2, 33 + my + i * 15, x + node.x + 97, 15 + y + node.y + port.getNodeOutput() * 15 + inputs.length * 15, port.isActive() ? 0xFFFFFF : 0x555555);
            }
        }
    }
}
