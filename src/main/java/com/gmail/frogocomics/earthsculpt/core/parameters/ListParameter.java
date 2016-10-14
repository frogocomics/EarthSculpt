package com.gmail.frogocomics.earthsculpt.core.parameters;

import java.util.List;
import java.util.ListIterator;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class ListParameter implements Parameter {

    private List<String> payload;
    private String name;
    private int selected;

    public ListParameter(String name, List<String> value) {
        this.name = name;
        payload = value;
        selected = 0;
    }

    public String getName() {
        return name;
    }

    @ClassType(type = List.class)
    public Object getValue() {
        return payload;
    }

    @ClassType(type = List.class)
    @SuppressWarnings("unchecked")
    public void setValue(Object value) {
        payload = (List<String>) value;
    }

    public void select(String s) {
        ListIterator<String> iterator = payload.listIterator();
        while(iterator.hasNext()) {
            int nextIndex = iterator.nextIndex();
            iterator.next();
            String next = payload.get(nextIndex);
            if(next.equals(s)) {
                selected = nextIndex;
            }
        }
    }

    public void select(int i) {
        selected = i;
    }

    public int getSelected() {
        return selected;
    }
}

