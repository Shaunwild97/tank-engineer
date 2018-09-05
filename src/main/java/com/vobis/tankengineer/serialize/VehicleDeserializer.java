package com.vobis.tankengineer.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.vobis.tankengineer.TankEngineer;
import com.vobis.tankengineer.circuit.Circuit;
import com.vobis.tankengineer.entity.EntityModular;
import com.vobis.tankengineer.modules.ModuleSlot;
import com.vobis.tankengineer.render.ModelLoader;

import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author Shaun
 */
public class VehicleDeserializer extends StdDeserializer<EntityModular> {

    public VehicleDeserializer() {
        this(null);
    }

    public VehicleDeserializer(Class<?> type) {
        super(type);
    }

    @Override
    public EntityModular deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        try {
            JsonNode node = jp.getCodec().readTree(jp);

            String type = node.get("type").asText();
            EntityModular vehicle = (EntityModular) Class.forName(type).newInstance();
            vehicle.init(TankEngineer.gameInstance.gameWorld);

            String id = node.get("id").asText();
            vehicle.setId(id);

            JsonNode modules = node.get("modules");

            JsonNode model = node.get("model");

            if (model != null) {
                vehicle.setModel(ModelLoader.loadModel(model.asText()));
            }

            ModuleSlot[] moduleSlots = VehicleIO.mapper.readValue(modules.toString(), ModuleSlot[].class);
            for (int i = 0; i < moduleSlots.length; i++) {
                vehicle.getModules().add(moduleSlots[i]);

                if (moduleSlots[i].getSlotted() != null) {
                    vehicle.insertModule(i, moduleSlots[i].getSlotted());
                }
            }

            JsonNode cNode = node.get("circuit");
            Circuit circuit = VehicleIO.mapper.readValue(cNode.toString(), Circuit.class);

            if (circuit.getNodes().size() > 0) {
                circuit.setOwner(vehicle);
                vehicle.setCircuit(circuit);

                circuit.getNodes()
                        .forEach(n -> n.setOwner(findOwner(vehicle, n.getOwnerId())));
            }

            return vehicle;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public EntityModular findOwner(EntityModular parent, String id) {
        Optional<EntityModular> optional
                = parent.getChildModules()
                .stream()
                .filter(c -> c.getId().equals(id))
                .findAny();

        if (optional.isPresent()) {
            return optional.get();
        } else if (parent.getId().equals(id)) {
            return parent;
        }

        return null;
    }

}
