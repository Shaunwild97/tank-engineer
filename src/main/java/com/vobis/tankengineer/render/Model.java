/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.render;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author Shaun
 */
public class Model {

    private String imageName;
    private String name;
    private Integer[] sprite;
    private Image image;
    private Animation animation;
    private Polygon collission;

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer[] getSprite() {
        return sprite;
    }

    public void setSprite(Integer[] sprite) {
        this.sprite = sprite;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Polygon getCollission() {
        return collission;
    }

    public void setCollission(Polygon collission) {
        this.collission = collission;
    }

    public Animation getAnimation() {
        return animation;
    }

    public void setAnimation(Animation animation) {
        this.animation = animation;
    }
}
