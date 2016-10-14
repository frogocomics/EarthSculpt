package com.gmail.frogocomics.earthsculpt.core.connection;

import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.utils.HeightmapUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class HeightmapOutput implements Output {

    private boolean optional;
    private String label;
    private List<Input> inputs;
    private File buildLocation;

    public HeightmapOutput(boolean optional, String label) {
        this.optional = optional;
        this.label = label;
        inputs = new ArrayList<>();
    }

    /**
     * This method deletes the written heightmap file, if any. This method should be used when the
     * workflow of the user is updated in any way, or when the project settings is changed in any
     * way.
     */
    public void resetPayload() {
        if(buildLocation != null) {
            buildLocation.delete();
        }
    }

    /**
     * Get if this output is an optional output or not.
     *
     * @return Returns <code>true</code> if the output is indeed optional.
     */
    public boolean isOptional() {
        return optional;
    }

    public String getLabel() {
        return label;
    }

    public Optional<HeightField> getBuild() {
        if(buildLocation == null) {
            return Optional.empty();
        } else {
            if(!ProjectSettings.COMPRESS_DATA) {
                try {
                    return Optional.of(HeightmapUtils.load(buildLocation));
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            } else {
                try {
                    return Optional.of(HeightmapUtils.loadZip(buildLocation));
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }
        }
    }

    public Optional<File> getBuildLocation() {
        if(buildLocation != null) {
            return Optional.of(buildLocation);
        }
        return Optional.empty();
    }

    public void setBuildLocation(File f) {
        buildLocation = f;
    }

    public List<Input> getConnectedInputs() {
        return inputs;
    }

    public void addConnection(Input input) {
        if(input instanceof HeightmapInput || input instanceof MaskInput) {
            inputs.add(input);
        }
    }

    public void removeConnection(Input input) {
        inputs.remove(input);
    }
}
