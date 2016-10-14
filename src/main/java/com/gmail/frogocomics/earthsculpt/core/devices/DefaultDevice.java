package com.gmail.frogocomics.earthsculpt.core.devices;

import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public class DefaultDevice {

    public int getVersion() {
        return ProjectSettings.VERSION;
    }

}
