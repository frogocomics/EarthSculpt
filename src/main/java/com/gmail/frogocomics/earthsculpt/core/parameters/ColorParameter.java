package com.gmail.frogocomics.earthsculpt.core.parameters;

import java.awt.*;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class ColorParameter implements Parameter {

    private Color payload;
    private String name;

    public ColorParameter(String name) {
        this.name = name;
        this.payload = new Color(255, 255, 255);
    }

    public ColorParameter(String name, Color value) {
        this.name = name;
        this.payload = value;
    }

    public String getName() {
        return name;
    }

    @ClassType(type = Color.class)
    public Object getValue() {
        return payload;
    }

    @ClassType(type = Color.class)
    public void setValue(Object value) {
        payload = (Color) value;
    }
}
