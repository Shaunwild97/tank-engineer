package com.vobis.tankengineer.modules;

import com.vobis.tankengineer.entity.EntityModular;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author Shaun
 */
@Data
@AllArgsConstructor
public class ModuleSlot {
    private EntityModular slotted;
    private ModuleSize moduleSize;
    private float angleOffset;
    private float distanceOffset;
}
