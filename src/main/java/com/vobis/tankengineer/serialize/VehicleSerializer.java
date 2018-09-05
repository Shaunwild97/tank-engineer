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
import com.vobis.tankengineer.entity.EntityModular;

import java.io.IOException;

/**
 *
 * @author Shaun
 */
public class VehicleSerializer extends StdSerializer<EntityModular> {

    public VehicleSerializer() {
        this(null);
    }

    public VehicleSerializer(Class<EntityModular> t) {
        super(t);
    }

    @Override
    public void serialize(EntityModular t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonGenerationException {
        jg.writeStartObject();
        jg.writeStringField("type", t.getClass().getName());
        jg.writeStringField("id", t.getId());

        if (t.getModel() != null) {
            jg.writeStringField("model", t.getModel().getName());
        }

        jg.writeObjectField("modules", t.getModules());
        jg.writeObjectField("circuit", t.getCircuit());
        jg.writeEndObject();
    }

}
