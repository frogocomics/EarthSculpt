package com.gmail.frogocomics.earthsculpt.core.parameters;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class IntegerParameter implements Parameter {

    private int payload = 0;
    private String name;
    private int min;
    private int max;

    public IntegerParameter(int min, int max, String name) {
        this.min = min;
        this.max = max;
        this.name = name;
    }

    public IntegerParameter(int min, int max, String name, int value) {
        this.min = min;
        this.max = max;
        this.name = name;
        this.payload = value;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    @ClassType(type = Integer.class)
    public Object getValue() {
        return payload;
    }

    @ClassType(type = Integer.class)
    public void setValue(Object value) {
        if(!((Integer) value > max || (Integer) value < min)) {
            payload = (Integer) value;
        }
    }
}
