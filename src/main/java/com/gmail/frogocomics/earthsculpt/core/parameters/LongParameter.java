package com.gmail.frogocomics.earthsculpt.core.parameters;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class LongParameter implements Parameter {

    private long payload = 0;
    private String name;
    private long min;
    private long max;

    public LongParameter(long min, long max, String name) {
        this.min = min;
        this.max = max;
        this.name = name;
    }

    public LongParameter(long min, long max, String name, long value) {
        this.min = min;
        this.max = max;
        this.name = name;
        this.payload = value;
    }

    public long getMin() {
        return min;
    }

    public long getMax() {
        return max;
    }

    public String getName() {
        return name;
    }

    @ClassType(type = Long.class)
    public Object getValue() {
        return payload;
    }

    @ClassType(type = Long.class)
    public void setValue(Object value) {
        if(!((Long) value > max || (Long) value < min)) {
            payload = (Long) value;
        }
    }
}
