package com.vobis.tankengineer.serialize;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vobis.tankengineer.circuit.NodePort;

import java.io.IOException;

/**
 *
 * @author Shaun
 */
public class NodePortSerializer extends StdSerializer<NodePort> {

    public NodePortSerializer() {
        this(null);
    }

    public NodePortSerializer(Class<NodePort> t) {
        super(t);
    }

    @Override
    public void serialize(NodePort n, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonGenerationException {
        if (n.getNode() == null) {
            return;
        }

        jg.writeStartObject();

        jg.writeObjectField("from", n.getNode().getId());
        jg.writeNumberField("fromIndex", n.getNodeOutput());
        jg.writeNumberField("index", n.getIndex());

        jg.writeEndObject();
    }

}
