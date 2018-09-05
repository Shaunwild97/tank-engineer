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
import com.vobis.tankengineer.circuit.Circuit;

import java.io.IOException;

/**
 *
 * @author Shaun
 */
public class CircuitSerializer extends StdSerializer<Circuit> {

    public CircuitSerializer() {
        this(null);
    }

    public CircuitSerializer(Class<Circuit> t) {
        super(t);
    }

    @Override
    public void serialize(Circuit c, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonGenerationException {
        jg.writeStartObject();

        jg.writeObjectField("nodes", c.getNodes());

        jg.writeEndObject();
    }

}
