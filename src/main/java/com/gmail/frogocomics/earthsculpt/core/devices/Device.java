package com.gmail.frogocomics.earthsculpt.core.devices;

import com.gmail.frogocomics.earthsculpt.core.connection.Output;
import com.gmail.frogocomics.earthsculpt.core.data.WorkableType;
import com.gmail.frogocomics.earthsculpt.core.parameters.Parameter;
import com.gmail.frogocomics.earthsculpt.core.connection.Input;

import java.util.List;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public interface Device {

    /**
     * Get the display name of the device.
     *
     * @return Returns the display name of the device.
     */
    String getName();

    /**
     * Get the {@link Input}s of the device.
     *
     * @return Returns the inputs of the device.
     */
    Input[] getInputs();

    /**
     * Get the {@link Output}s of the device.
     *
     * @return Returns the outputs of the device.
     */
    Output[] getOutputs();

    /**
     * Get the type of device.
     *
     * @return Returns the type of device.
     */
    DeviceType getType();

    /**
     * Get the parameters of the device, contained within a list.
     *
     * @return Returns the device parameters.
     */
    List<Parameter> getParameters();

    boolean isBuilt();

    void setBuilt(boolean b);

    /**
     * Build the device at a specified resolution.
     *
     * @param resolution The specified resolution.
     * @param isPreview If it is for generating a preview or not. Generating a preview will result
     *                  in it being stored in RAM and not outputted into a data file.
     * @return Returns the processed workable type. Please do not use the workable type, instead use
     *         {@code Device.getOutputs()[0].getBuild().get()}.
     */
    WorkableType run(int resolution, boolean isPreview);

    WorkableType generatePreview();

    void setValue(String parameterName, Object value);

}
