package com.gmail.frogocomics.earthsculpt.core.data;

import java.io.Serializable;

/**
 * Represents a data type that can be used as inputs and outputs of devices. A data type can be a
 * heightfield, etc.
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public interface WorkableType extends Serializable {

    int getResolution();



}
