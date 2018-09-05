/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.circuit;

/**
 *
 * @author Shaun
 */
public class NodePort {

    private String name;
    private NodeInstance node;
    private int nodeId = -1;
    private int outputIndex;
    private Object value;
    private Class expectedType;
    private int index;

    public int getNodeOutput() {
        return outputIndex;
    }

    public void setNode(int outputIndex, NodeInstance node) {
        this.node = node;
        this.outputIndex = outputIndex;
        this.nodeId = node.getId();
    }

    public void setOutputIndex(int outputIndex) {
        this.outputIndex = outputIndex;
    }

    public NodePort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NodeInstance getNode() {
        return node;
    }

    public Object getValue() {
        if (node != null) {
            return node.getOutput(outputIndex);
        } else {
            return value;
        }
    }

    public void setValue(Object value) {
        if (value instanceof NodeInstance) {
            node = (NodeInstance) value;
            value = null;
        } else {
            this.value = value;
        }
    }

    public int getNodeId() {
        return nodeId;
    }

    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    public boolean isActive() {
        Object value = getValue();
        return value != null || (value instanceof Integer && ((Integer) value) != 0);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
