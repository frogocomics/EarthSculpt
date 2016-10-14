package com.gmail.frogocomics.earthsculpt.core.connection;

import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.core.data.WorkableType;
import com.gmail.frogocomics.earthsculpt.core.devices.Device;
import com.gmail.frogocomics.earthsculpt.utils.HeightmapUtils;
import com.gmail.frogocomics.earthsculpt.utils.annotations.Important;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Represents a output port for a specific device. An output has the ability to store a linked built
 * {@link HeightField} or something else along those lines. This is effectively a way to safeguard
 * against device recursion, or feedback, in "World Machine" terms. An output can be various types
 * such as a heightmap output, image output (tbd), and perhaps even parameter outputs.
 *
 * <p>The output was originally able to store built values, but due to RAM concerns (High resolution
 * builds use a lot of ram!) the output instead uses the alternative method of storing a link to
 * a file, where the built {@link WorkableType} is written to the disk. Although this method is
 * obviously slower, it almost completely removes RAM concerns. Writing to a SSD is only slightly
 * slower than RAM.</p>
 *
 * <p>Below is an example on how to create a {@link HeightmapOutput}, which is needed to create a
 * {@link Device}.</p>
 *
 * <p><code>
 *     public ExampleDevice() {
 *         this.outputs[0] = new HeightmapOutput("Primary Output", false); <i>//Main</i>
 *         this.outputs[1] = new HeightmapOutput("Secondary Output", true); <i>//Secondary</i>
 *     }
 *
 *     public void run() {
 *         <i>//Run Code</i>
 *         HeightField hf = <i>//blah blah blah</i>
 *         this.outputs[0].setBuildLocation(HeightmapUtils.save(hf));
 *     }
 * </code>
 * In the second output, the second parameter of the {@link HeightmapOutput} constructor specifies
 * if the port is optional or not. This is just a way to specify unimportant inputs (mask inputs) or
 * outputs. (Map outputs)</p>
 *
 * <p>When the map is built, the built {@link File} obtained from {@link HeightmapUtils#load(File)}
 * is stored into the output using {@link Output#setBuildLocation(File)}. When the method
 * {@link Output#getBuild()} is called, the stored data file is loaded back into a
 * {@link WorkableType}.</p>
 *
 * @author Jeff Chen
 * @since 0.0.1
 * @see HeightmapOutput
 * @see HeightmapUtils
 */
public interface Output extends Port {

    /**
     * In the case the user changed the settings, changed the project settings, or disconnected any
     * part of the device the output is on, the outputted data is automatically deleted. In
     * <code>resetPayload()</code>, the payload specifies the possible built data file it is linked
     * to. In the case that this method is called when the device is not built, nothing will
     * obviously happen.
     */
    void resetPayload();

    /**
     * Get the loaded payload of the output, if it is built.
     *
     * @return Returns the loaded payload of the output. This will return {@link Optional#empty()}
     *         if it was not built.
     */
    @Important
    Optional<? extends WorkableType> getBuild();

    /**
     * Get the location of the written data file, if it is built.
     *
     * @return Returns the location of the written data file. If it is not built, it will instead
     *         return {@link Optional#empty()}.
     */
    @Important
    Optional<File> getBuildLocation();

    /**
     * Set the location of the data file, written to the hard disk.
     *
     * @param f The location of the outputted data file, produced from
     *          {@link HeightmapUtils#save(HeightField)}.
     */
    void setBuildLocation(File f);

    /**
     * Get all the possible inputs the output might be connected to. This is highly useful in the
     * build process since it can send all the outputs to the connected devices to be built, and it
     * is also crucial in checking for recursion in devices.
     *
     * @return Returns all possible inputs the output might be connected to, if there is any at all.
     */
    @Important
    List<? extends Input> getConnectedInputs();

    /**
     * Add a connection to a input the user has connected the output into.
     *
     * @param input This is the input the user has connnected the output into.
     */
    void addConnection(Input input);

    /**
     * Remove a connection to a input the user has deconnected from the output.
     *
     * @param input This is the input the user has disconnected from the output.
     */
    void removeConnection(Input input);

}
