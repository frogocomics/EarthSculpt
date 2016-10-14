package com.gmail.frogocomics.earthsculpt.core.parameters;

import com.gmail.frogocomics.earthsculpt.core.devices.Device;

/**
 * A parameter is a variable value that is used to configure settings on a {@link Device}. The
 * parameter could be for example, a double parameter or even a color parameter.
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public interface Parameter {

    /**
     * Get the display name of the parameter.
     *
     * @return Returns the name of the parameter.
     */
    String getName();

    /**
     * Get the payload of the value. To know what type of value it will return, go to the
     * declaration and find the type that {@link ClassType} points out.
     *
     * @return Returns the payload.
     */
    Object getValue();

    /**
     * Set the value of the parameter. Be sure to use the correct type of value for the parameter,
     * or else exceptions might be thrown. Check the {@link ClassType} annotation for more details
     * on the type of value.
     *
     * @param value The possible value to set.
     */
    void setValue(Object value);

}
