package com.gmail.frogocomics.earthsculpt.core.devices;

import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;
import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapOutput;
import com.gmail.frogocomics.earthsculpt.core.connection.Input;
import com.gmail.frogocomics.earthsculpt.core.connection.Output;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.core.data.WorkableType;
import com.gmail.frogocomics.earthsculpt.core.noise.PerlinInterpolationStyle;
import com.gmail.frogocomics.earthsculpt.core.noise.PerlinNoise;
import com.gmail.frogocomics.earthsculpt.core.parameters.ClassType;
import com.gmail.frogocomics.earthsculpt.core.parameters.DoubleParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.IntegerParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.ListParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.LongParameter;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;
import com.gmail.frogocomics.earthsculpt.utils.HeightmapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Perlin noise is a procedural texture primitive, a type of gradient noise used by visual effects
 * artists to increase the appearance of realism in computer graphics. The function has a
 * pseudo-random appearance, yet all of its visual details are the same size. This property allows
 * it to be readily controllable; multiple scaled copies of Perlin noise can be inserted into
 * mathematical expressions to create a great variety of procedural textures. Synthetic textures
 * using Perlin noise are often used in CGI to make computer-generated visual elements – such as
 * object surfaces, fire, smoke, or clouds – appear more natural, by imitating the controlled random
 * appearance of textures of nature. (Taken from Perlin Noise, Wikipedia)
 *
 * <p>This perlin device generates a perlin with settable scale, style (Billowy and ridged), seed,
 * and a custom interpolation method, making this a highly configurable device.</p>
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class PerlinDevice extends DefaultDevice implements Device, Mutlithreadable {

    private Input[] inputs = new Input[0];
    private Output[] outputs = new Output[1];
    private List<Parameter> parameters = new ArrayList<>();
    private boolean built = false;

    private static final int SCALE = 0;
    private static final int PERSISTENCE = 1;
    private static final int STYLE = 2;
    private static final int INTERPOLATION = 3;
    private static final int OCTAVES = 4;
    private static final int SEED = 5;

    public PerlinDevice() {
        List<String> styles = new ArrayList<>();
        styles.add("Standard");
        styles.add("Ridged");
        styles.add("Billowy");
        List<String> interpolation = new ArrayList<>();
        interpolation.add("Linear");
        interpolation.add("Cosine");
        interpolation.add("Cubic");
        parameters.add(new DoubleParameter("Scale", 0.25));
        parameters.add(new DoubleParameter("Persistence", 0.4));
        parameters.add(new ListParameter("Style", styles));
        parameters.add(new ListParameter("Interpolation", interpolation));
        parameters.add(new IntegerParameter(1, 15, "Octaves", 8));
        parameters.add(new LongParameter(0, Long.MAX_VALUE - 1, "Seed", 11540));
        outputs[0] = new HeightmapOutput(false, "Primary Output");
    }

    /**
     * Get the name of this device.
     *
     * @return Returns "Perlin Noise"
     */
    public String getName() {
        return "Perlin Noise";
    }

    /**
     * Get the inputs.
     *
     * @return Returns no inputs, since this device does not have any inputs.
     */
    public Input[] getInputs() {
        return inputs;
    }

    /**
     * Get the outputs.
     *
     * @return Returns 1 primary output.
     */
    public Output[] getOutputs() {
        return outputs;
    }

    /**
     * Get the type of device.
     *
     * @return Returns {@link DeviceType#GENERATOR}.
     */
    public DeviceType getType() {
        return DeviceType.GENERATOR;
    }

    /**
     * Get the parameters of this device.
     *
     * @return Return the parameters of this device.
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
     * Create a perlin noise with a specifiable style and interpolation method.
     *
     * @since 0.0.1
     */
    public HeightField run(int resolution, boolean isPreview) {

        //<editor-fold desc=Set the perlin interpolation style the user has chosen">
        PerlinInterpolationStyle style;

        int chosenInterpolationStyle = ((ListParameter)parameters.get(INTERPOLATION)).getSelected();
        if(chosenInterpolationStyle==0) {
            style = PerlinInterpolationStyle.LINEAR;
        } else if(chosenInterpolationStyle==1) {
            style = PerlinInterpolationStyle.COSINE;
        } else if(chosenInterpolationStyle==2) {
            style = PerlinInterpolationStyle.COSINE; // todo: This should be cubic, cubic needs work
                                                     // todo: on. I don't understand
        } else {
            style = PerlinInterpolationStyle.COSINE;
        }
        //</editor-fold>

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

        //Generate the perlin based on the settings the user has specified
        for(int n = 0; n < resolution; n++) {
            x += scale;
            for(int m = 0; m < resolution; m++) {
                y += scale;
                int chosenPerlinVariant = ((ListParameter)parameters.get(STYLE)).getSelected();
                if(chosenPerlinVariant==0) {
                    values[n][m] = PerlinNoise.generateStandardPerlinNoise(x, y,
                            (Double) parameters.get(PERSISTENCE).getValue(),
                            (Integer) parameters.get(OCTAVES).getValue(), style, (
                                    Long) parameters.get(SEED).getValue());
                } else if(chosenPerlinVariant==1) {
                    values[n][m] = PerlinNoise.generateRidgedPerlinNoise(x, y,
                            (Double) parameters.get(PERSISTENCE).getValue(),
                            (Integer) parameters.get(OCTAVES).getValue(), style, (
                                    Long) parameters.get(SEED).getValue());
                } else if(chosenPerlinVariant==2) {
                    values[n][m] = PerlinNoise.generateBillowyPerlinNoise(x, y,
                            (Double) parameters.get(PERSISTENCE).getValue(),
                            (Integer) parameters.get(OCTAVES).getValue(), style, (
                                    Long) parameters.get(SEED).getValue());
                }
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

        built = true;
        return null;
    }

    @ClassType(type = HeightField.class)
    public WorkableType generatePreview() {
        return run(256, true);
    }

    /**
     * Set the value of a parameter.
     *
     * @param parameterName The name of the parameter.
     * @param value The value of the parameter
     */
    public void setValue(String parameterName, Object value) {
        if(parameterName.equals("Scale")) {
            parameters.get(SCALE).setValue(value);
        }
        if(parameterName.equals("Persistence")) {
            parameters.get(PERSISTENCE).setValue(value);
        }
        if(parameterName.equals("Style")) {
            ((ListParameter) parameters.get(STYLE)).select((Integer) value);
        }
        if(parameterName.equals("Interpolation")) {
            ((ListParameter) parameters.get(INTERPOLATION)).select((Integer) value);
        }
        if(parameterName.equals("Octaves")) {
            parameters.get(OCTAVES).setValue(value);
        }
        if(parameterName.equals("Seed")) {
            parameters.get(SEED).setValue(value);
        }
    }
}
