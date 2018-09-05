package com.vobis.tankengineer;

import com.vobis.tankengineer.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Shaun
 */
@Data
@AllArgsConstructor
public class RayTrace {
    private Vector2 origin;
    private Vector2 collision;
    private float direction;
    private float distance;
    private Entity entity;
}
