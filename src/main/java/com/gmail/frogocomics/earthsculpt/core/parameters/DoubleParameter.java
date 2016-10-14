package com.gmail.frogocomics.earthsculpt.core.parameters;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class DoubleParameter implements Parameter {

    private double payload = 0;
    private String name;

    public DoubleParameter(String name) {
        this.name = name;
    }

    public DoubleParameter(String name, double value) {
        this.name = name;
        this.payload = value;
    }

    public String getName() {
        return name;
    }

    @ClassType(type = Double.class)
    public Object getValue() {
        return payload;
    }

    @ClassType(type = Double.class)
    public void setValue(Object value) {
        payload = (Double) value;
    }
}
