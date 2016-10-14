package com.gmail.frogocomics.earthsculpt.utils;

import com.gmail.frogocomics.earthsculpt.core.devices.Device;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;

import java.util.Optional;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 *
 * @deprecated Currently no use
 */
@Deprecated
public final class ParameterUtils {

    private ParameterUtils() {
    }

    @Deprecated
    public static Optional<Parameter> getParameterFromName(String name, Device device) {
        for(Parameter p : device.getParameters()) {
            if(p.getName().equals(name)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }
}
