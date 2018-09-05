package com.vobis.tankengineer.circuit;

/**
 *
 * @author Shaun
 */
public class Conversion {

    private Class type1, type2;

    public Conversion(Class type1, Class type2) {
        this.type1 = type1;
        this.type2 = type2;
    }

    public Class getType1() {
        return type1;
    }

    public Class getType2() {
        return type2;
    }

    @Override
    public int hashCode() {
        return type1.hashCode() + type2.hashCode();
    }
}
