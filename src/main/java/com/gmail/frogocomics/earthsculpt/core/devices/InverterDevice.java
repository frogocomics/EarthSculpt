package com.gmail.frogocomics.earthsculpt.core.devices;

import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;
import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapInput;
import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapOutput;
import com.gmail.frogocomics.earthsculpt.core.connection.Input;
import com.gmail.frogocomics.earthsculpt.core.connection.Output;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;
import com.gmail.frogocomics.earthsculpt.utils.FlowUtils;
import com.gmail.frogocomics.earthsculpt.utils.HeightmapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *
 * @since 0.0.2
 * @author Jeff Chen
 */
public final class InverterDevice extends DefaultDevice implements Device, Mutlithreadable {

    private Input[] inputs = new Input[1];
    private Output[] outputs = new Output[1];
    private List<Parameter> parameters = new ArrayList<>();
    private boolean built = false;

    public InverterDevice() {
        inputs[0] = new HeightmapInput(false, "Primary Input");
        outputs[0] = new HeightmapOutput(false, "Primary Output");
    }

    @Override
    public String getName() {
        return "Inverter";
    }

    @Override
    public Input[] getInputs() {
        return inputs;
    }

    @Override
    public Output[] getOutputs() {
        return outputs;
    }

    @Override
    public DeviceType getType() {
        return DeviceType.FILTER;
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public boolean isBuilt() {
        return built;
    }

    @Override
    public void setBuilt(boolean b) {
        built = b;
    }

    @Override
    public HeightField run(int resolution, boolean isPreview) {
        if(FlowUtils.getHeightFieldFromInput((HeightmapInput[]) inputs).isPresent()) {
            double[][] oldValues = FlowUtils.getHeightFieldFromInput((HeightmapInput[]) inputs).get().getHeights();
            double[][] newValues = new double[resolution][resolution];
            for (int x = 0; x < resolution; x++) {
                for (int y = 0; y < resolution; y++) {
                    newValues[x][y] = 1 - oldValues[x][y];
                }
            }

            try {
                //Create the heightfield based on the generated perlin values from the methods above
                HeightField field = new HeightField(ProjectSettings.WORLD_SIZE, ProjectSettings.WORLD_SIZE, newValues, ProjectSettings.SIZE);
                if(!isPreview) {
                    //Save the built perlin to a temporary file while setting the location of the file
                    //Generated to the main output.
                    outputs[0].setBuildLocation(HeightmapUtils.save(field));
                    //Save memory by setting all values to null
                    Arrays.fill(newValues, null);
                    return field;
                } else {
                    return field;
                }
            } catch (Exception e) {
                e.printStackTrace();
                //todo: error message
            }
        }
        return null;
    }

    @Override
    public HeightField generatePreview() {
        return run(256, true);
    }

    @Override
    public void setValue(String parameterName, Object value) {}

}
