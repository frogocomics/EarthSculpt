package com.gmail.frogocomics.earthsculpt.core.connection;

import com.gmail.frogocomics.earthsculpt.core.data.WorkableType;

/**
 * This represents an input or output port that receives or produces {@link WorkableType}s,
 * respectively.
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public interface Port {

    /**
     * Get if this is an optional point of not. Optional ports are usually mask inputs or map
     * outputs. All primary inputs or outputs should not be marked as optional.
     *
     * @return Returns if this port is optional or not.
     */
    boolean isOptional();

    /**
     * Get the label that displays when the user hovers over the port. Some standard labels would
     * be, for example, "Primary Input", etc.
     *
     * @return Returns the port label.
     */
    String getLabel();

}
