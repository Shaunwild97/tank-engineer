/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.vobis.tankengineer.circuit.NodeInstance;
import com.vobis.tankengineer.circuit.NodePort;
import com.vobis.tankengineer.circuit.Nodes;

import java.io.IOException;

/**
 *
 * @author Shaun
 */
public class NodeDeserializer extends StdDeserializer<NodeInstance> {

    public NodeDeserializer() {
        this(null);
    }

    public NodeDeserializer(Class<?> type) {
        super(type);
    }

    @Override
    public NodeInstance deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            JsonNode jNode = jp.getCodec().readTree(jp);

            JsonNode type = jNode.get("type");

            NodeInstance node = new NodeInstance(Nodes.getNode(type.asText()));

            int id = jNode.get("id").asInt();
            float x = (float) jNode.get("x").asDouble();
            float y = (float) jNode.get("y").asDouble();
            String ownerId = jNode.get("owner").asText();

            node.setId(id);
            node.setX(x);
            node.setY(y);
            node.setOwnerId(ownerId);

            JsonNode inputs = jNode.get("inputs");
            for (final JsonNode objNode : inputs) {
                int from = objNode.get("from").asInt();
                int fromIndex = objNode.get("fromIndex").asInt();
                int index = objNode.get("index").asInt();

                NodePort port = node.getInputs()[index];
                port.setNodeId(from);
                port.setOutputIndex(fromIndex);
            }

            return node;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
