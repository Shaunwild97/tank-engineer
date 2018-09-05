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
import com.vobis.tankengineer.circuit.Circuit;
import com.vobis.tankengineer.circuit.NodeInstance;
import com.vobis.tankengineer.circuit.NodePort;

import java.io.IOException;

/**
 *
 * @author Shaun
 */
public class CircuitDeserializer extends StdDeserializer<Circuit> {

    public CircuitDeserializer() {
        this(null);
    }

    public CircuitDeserializer(Class<?> type) {
        super(type);
    }

    @Override
    public Circuit deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);

            JsonNode nodes = node.get("nodes");

            Circuit circuit = new Circuit();
            jp.setCurrentValue(circuit);

            NodeInstance[] nodesArray = VehicleIO.mapper.readValue(nodes.toString(), NodeInstance[].class);
            for (int i = 0; i < nodesArray.length; i++) {
                NodeInstance nodeInstance = nodesArray[i];

                circuit.getNodes().add(nodeInstance);
            }

            for (NodeInstance instance : circuit.getNodes()) {
                for (NodePort port : instance.getInputs()) {
                    if (port.getNodeId() > -1) {
                        port.setNode(port.getNodeOutput(), circuit.getNodeById(port.getNodeId()));
                    }
                }
            }

            return circuit;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
