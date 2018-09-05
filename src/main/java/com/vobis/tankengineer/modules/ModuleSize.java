/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vobis.tankengineer.modules;

/**
 *
 * @author Shaun
 */
public enum ModuleSize {
    NANO(-1),
    SMALL(16),
    MEDIUM(32),
    LARGE(64),
    GIANT(-1);

    private final int size;

    private ModuleSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
