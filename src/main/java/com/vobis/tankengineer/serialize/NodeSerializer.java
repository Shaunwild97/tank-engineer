/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.serialize;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vobis.tankengineer.circuit.NodeInstance;

import java.io.IOException;

/**
 *
 * @author Shaun
 */
public class NodeSerializer extends StdSerializer<NodeInstance> {

    public NodeSerializer() {
        this(null);
    }

    public NodeSerializer(Class<NodeInstance> t) {
        super(t);
    }

    @Override
    public void serialize(NodeInstance n, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonGenerationException {
        jg.writeStartObject();

        jg.writeObjectField("type", n.getNode().getShortName());
        jg.writeNumberField("x", n.getX());
        jg.writeNumberField("y", n.getY());
        jg.writeNumberField("id", n.getId());
        jg.writeObjectField("inputs", n.getInputs());

        if (n.getOwner() != null) {
            jg.writeStringField("owner", n.getOwner().getId());
        }

        jg.writeEndObject();
    }

}
