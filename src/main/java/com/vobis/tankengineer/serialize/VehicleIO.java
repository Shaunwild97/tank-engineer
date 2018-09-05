package com.vobis.tankengineer.serialize;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.vobis.tankengineer.circuit.Circuit;
import com.vobis.tankengineer.circuit.NodeInstance;
import com.vobis.tankengineer.circuit.NodePort;
import com.vobis.tankengineer.entity.EntityModular;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shaun
 */
public class VehicleIO {

    public static final ObjectMapper mapper = new ObjectMapper();

    private static final String SAVE_FILE_LOCATION = System.getProperty("user.home") + "/TankEngineer/Saves";
    private static final Path saveFolder = Paths.get(SAVE_FILE_LOCATION);

    static {
        if (!Files.exists(saveFolder)) {
            try {
                Files.createDirectories(saveFolder);
            } catch (IOException ex) {
                Logger.getLogger(VehicleIO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        SimpleModule module = new SimpleModule();
        module.addSerializer(EntityModular.class, new VehicleSerializer());
        module.addSerializer(Circuit.class, new CircuitSerializer());
        module.addSerializer(NodeInstance.class, new NodeSerializer());
        module.addSerializer(NodePort.class, new NodePortSerializer());

        module.addDeserializer(EntityModular.class, new VehicleDeserializer());
        module.addDeserializer(Circuit.class, new CircuitDeserializer());
        module.addDeserializer(NodeInstance.class, new NodeDeserializer());
        mapper.registerModule(module);

        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

//        mapper.setVisibilityChecker(mapper.getSerializationConfig().getDefaultVisibilityChecker()
//                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE)
//                .withFieldVisibility(JsonAutoDetect.Visibility.NONE)
//                .withGetterVisibility(JsonAutoDetect.Visibility.DEFAULT)
//                .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
//                .withSetterVisibility(JsonAutoDetect.Visibility.NONE));
    }

    public static EntityModular loadVehicle(String name) {

        Path vehicle = saveFolder.resolve(name + ".json");
        EntityModular result = null;

        try {
            result = mapper.readValue(vehicle.toFile(), EntityModular.class);
        } catch (IOException ex) {
            Logger.getLogger(VehicleIO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }

    public static void saveVehicle(EntityModular modular, String name) {
        Path vehicle = saveFolder.resolve(name + ".json");

        try {
            if (Files.exists(vehicle)) {
                return;
            } else {
                Files.createFile(vehicle);
            }

            mapper.writeValue(vehicle.toFile(), modular);
        } catch (IOException ex) {
            Logger.getLogger(VehicleIO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<String> fetchAllVehicles() {
        String[] files = saveFolder.toFile().list();

        List<String> result = new ArrayList<>();

        for (String file : files) {
            if (file.contains(".json")) {
                result.add(file);
            }
        }

        return result;
    }

}
