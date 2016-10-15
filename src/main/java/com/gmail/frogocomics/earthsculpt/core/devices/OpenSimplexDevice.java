package com.gmail.frogocomics.earthsculpt.core.devices;

import com.github.gist.kdotjpg.OpenSimplexNoise;
import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;
import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapOutput;
import com.gmail.frogocomics.earthsculpt.core.connection.Input;
import com.gmail.frogocomics.earthsculpt.core.connection.Output;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.core.parameters.DoubleParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.IntegerParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.LongParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;
import com.gmail.frogocomics.earthsculpt.utils.HeightmapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * OpenSimplex noise is an n-dimensional gradient noise function that was developed in order to
 * overcome the patent-related issues surrounding Simplex noise, while continuing to also avoid the
 * visually-significant directional artifacts characteristic of Perlin noise.
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public class OpenSimplexDevice extends DefaultDevice implements Device {

    private Input[] inputs = new Input[0];
    private Output[] outputs = new Output[1];
    private List<Parameter> parameters = new ArrayList<Parameter>();
    private boolean built = false;

    private static final int SCALE = 0;
    private static final int PERSISTENCE = 1;
    private static final int OCTAVES = 2;
    private static final int SEED = 3;

    public OpenSimplexDevice() {
        parameters.add(new DoubleParameter("Scale", 0.5));
        parameters.add(new DoubleParameter("Persistence", 0.4));
        parameters.add(new IntegerParameter(1, 15, "Octaves", 8));
        parameters.add(new LongParameter(0, Long.MAX_VALUE - 1, "Seed", ThreadLocalRandom.current().nextInt(0, 65535 + 1)));
        outputs[0] = new HeightmapOutput(false, "Primary Output");
    }

    public String getName() {
        return "OpenSimplex Noise";
    }

    public Input[] getInputs() {
        return inputs;
    }

    public Output[] getOutputs() {
        return outputs;
    }

    public DeviceType getType() {
        return DeviceType.GENERATOR;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

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

    public HeightField run(int resolution, boolean isPreview) {
        //Set the array to store all the computed perlin values
        double[][] values = new double[resolution][resolution];

        /**
         * Example:
         * Lets say I set the parameter to 0.25
         * It multiplies by 10: 2.5
         * Then it adds 025: 2.75
         * Then it multiplies by the world size, lets say the size is 2km
         * 2.75 * 2 = 5.5
         */
        double scale = (((Double) parameters.get(SCALE).getValue() * 10) + 0.25) * ProjectSettings.WORLD_SIZE;
        double x = 0;
        double y = 0;
        OpenSimplexNoise noise = new OpenSimplexNoise((Long) parameters.get(SEED).getValue());
        for(int n = 0; n < resolution; n++) {
            x += scale;
            for(int m = 0; m < resolution; m++) {
                y += scale;
                values[n][m] = noise.generateOpenSimplexNoise(x, y, (Double) parameters.get(PERSISTENCE).getValue(), (Integer) parameters.get(OCTAVES).getValue());
            }
        }
        try {
            //Create the heightfield based on the generated perlin values from the methods above
            HeightField field = new HeightField(ProjectSettings.WORLD_SIZE, ProjectSettings.WORLD_SIZE, values, ProjectSettings.SIZE);
            if(!isPreview) {
                //Save the built perlin to a temporary file while setting the location of the file
                //Generated to the main output.
                outputs[0].setBuildLocation(HeightmapUtils.save(field));
                //Save memory by setting all values to null
                Arrays.fill(values, null);
                return field;
            } else {
                return field;
            }
        } catch (Exception e) {
            e.printStackTrace();
            //todo: error message
        }
        return null;
    }

    public HeightField generatePreview() {
        return run(255, true);
    }

    public void setValue(String parameterName, Object value) {
        if(parameterName.equals("Scale")) {
            parameters.get(SCALE).setValue(value);
        }
        if(parameterName.equals("Persistence")) {
            parameters.get(PERSISTENCE).setValue(value);
        }
        if(parameterName.equals("Octaves")) {
            parameters.get(OCTAVES).setValue(value);
        }
        if(parameterName.equals("Seed")) {
            parameters.get(SEED).setValue(value);
        }
    }
}