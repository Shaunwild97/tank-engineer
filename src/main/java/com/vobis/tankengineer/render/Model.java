package com.vobis.tankengineer.render;

import lombok.Data;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Polygon;

/**
 *
 * @author Shaun
 */
@Data
public class Model {
    private String imageName;
    private String name;
    private Integer[] sprite;
    private Image image;
    private Animation animation;
    private Polygon collission;
}
