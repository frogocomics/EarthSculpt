package com.gmail.frogocomics.earthsculpt.core.parameters;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class BooleanParameter implements Parameter {

    private boolean payload;
    private String name;

    public BooleanParameter(String name) {
        this.name = name;
        this.payload = false;
    }

    public BooleanParameter(String name, boolean value) {
        this.name = name;
        this.payload = value;
    }

    public String getName() {
        return name;
    }

    @ClassType(type = Boolean.class)
    public Object getValue() {
        return payload;
    }

    @ClassType(type = Boolean.class)
    public void setValue(Object value) {
        payload = (Boolean) value;
    }

}
