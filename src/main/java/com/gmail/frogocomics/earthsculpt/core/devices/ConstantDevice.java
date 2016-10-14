package com.gmail.frogocomics.earthsculpt.core.devices;

import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;
import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapOutput;
import com.gmail.frogocomics.earthsculpt.core.connection.Input;
import com.gmail.frogocomics.earthsculpt.core.connection.Output;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.core.data.WorkableType;
import com.gmail.frogocomics.earthsculpt.core.parameters.ClassType;
import com.gmail.frogocomics.earthsculpt.core.parameters.DoubleParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;
import com.gmail.frogocomics.earthsculpt.utils.HeightmapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Generates a solid("constant") value depending on a user specified height, relative to the world
 * height. This device should be extremely fast, fast to the point where it should only take a few
 * seconds even at a extreme 16k resolution.
 *
 * @since 0.0.1
 * @author Jeff Chen
 */

public class ConstantDevice extends DefaultDevice implements Device {

    private Input[] inputs = new Input[0];
    private Output[] outputs = new Output[1];
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private boolean built = false;

    private static final int HEIGHT = 0;

    public ConstantDevice() {
        parameters.add(new DoubleParameter("Height", (double) 0));
        outputs[0] = new HeightmapOutput(false, "Primary Output");
    }

    /**
     * Get the name of this device.
     *
     * @return Returns "Constant"
     */
    public String getName() {
        return "Constant";
    }

    /**
     * Get the inputs.
     *
     * @return Returns the inputs.
     */

    public Input[] getInputs() {
        return inputs;
    }

    /**
     * Get the outputs.
     *
     * @return Returns the outputs.
     */

    public Output[] getOutputs() {
        return outputs;
    }

    /**
     * Get the type of device.
     *
     * @return Returns the type of device
     */

    public DeviceType getType() {
        return DeviceType.GENERATOR;
    }

    /**
     * Get the parameters.
     *
     * @return Returns the parameters.
     */

    public List<Parameter> getParameters() {
        return parameters;
    }

    /**
     * Check if this device is built.
     *
     * @return Returns true if the device is built.
     */
    public boolean isBuilt() {
        return built;
    }

    /**
     * Set if the device is built or not.
     *
     * @param b True if the device is built.
     */
    public void setBuilt(boolean b) {
        built = b;
    }

    /**
     * Build this device.
     */
    public WorkableType run(int resolution) {
        double[][] values = new double[resolution][resolution];
        double height = (double) parameters.get(0).getValue();
        for(int x = 0; x < resolution; x++) {
            for(int y = 0; y < resolution; y++) {
                values[x][y] = height;
            }
        }
        try {
            //Create the heightfield based on the generated perlin values from the methods above
            HeightField field = new HeightField(ProjectSettings.WORLD_SIZE, ProjectSettings.WORLD_SIZE, values, ProjectSettings.SIZE);
            //Save the built perlin to a temporary file while setting the location of the file
            //Generated to the main output.
            outputs[0].setBuildLocation(HeightmapUtils.save(field));
            //Save memory by setting all values to null
            Arrays.fill(values, null);
            return field;

        } catch (Exception e) {
            e.printStackTrace();
            //todo: error message
        }
        return null;
    }

    @ClassType(type = HeightField.class)
    public WorkableType generatePreview() {
        return run(256);
    }

    /**
     * Set the value of a parameter.
     *
     * @param parameterName The name of the parameter.
     * @param value The value of the parameter
     */

    public void setValue(String parameterName, Object value) {
        if(parameterName.equals("Height")) {
            parameters.get(0).setValue(value);
        }
    }
}
