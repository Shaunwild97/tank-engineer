package com.vobis.tankengineer.entity;

import com.vobis.tankengineer.render.ModelLoader;

/**
 *
 * @author Shaun
 */
public class EntityRock extends Entity {

    public EntityRock() {
        setStaticObject(true);
        setSize(100, 100);
        dir = rand.nextInt(360);
        setModel(ModelLoader.loadModel("Rock"));
    }

}
