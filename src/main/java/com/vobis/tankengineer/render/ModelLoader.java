/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.render;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vobis.tankengineer.serialize.VehicleIO;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Polygon;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Shaun
 */
public class ModelLoader {

    private static final Map<String, Model> models = new HashMap<>();

    public static void loadAllModels() {
        try {
            List<Model> models = VehicleIO.mapper.readValue(ClassLoader.getSystemResourceAsStream("models/models.json"), new TypeReference<List<Model>>() {});

            for (Model model : models) {
                registerModel(model);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Could not load game models: " + ex.getMessage());
        }
    }

    public static Model loadModel(String name) {
        return models.get(name);
    }

    public static void registerModel(Model model) {
        Image image = Resources.getImage(model.getImageName());

        if (image == null) {
            return;
        }

        models.put(model.getName(), model);

        if (model.getSprite() != null) {
            Integer[] sprites = model.getSprite();
            image = ((SpriteSheet) image).getSprite(sprites[0], sprites[1]);
        }

        Animation animation = model.getAnimation();

        if (animation != null) {
            animation.setPlaying(false);
            animation.setSpriteSheet((SpriteSheet) image);
        }

        model.setImage(image);

        Polygon polygon = Resources.spriteToPolygon(animation != null ? animation.getCurrentFrame() : image, Resources.BEST_PRECISION);
        model.setCollission(polygon);
    }
}
