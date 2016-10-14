package com.gmail.frogocomics.earthsculpt.utils;

import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapInput;
import com.gmail.frogocomics.earthsculpt.core.connection.HeightmapOutput;
import com.gmail.frogocomics.earthsculpt.core.connection.Input;
import com.gmail.frogocomics.earthsculpt.core.connection.Output;
import com.gmail.frogocomics.earthsculpt.core.data.HeightField;
import com.gmail.frogocomics.earthsculpt.core.devices.Device;

import org.javatuples.Pair;

import java.util.Optional;

public final class FlowUtils {

    private FlowUtils() {
    }

    public static Optional<HeightField> getHeightFieldFromInput(HeightmapInput[] inputs) {
        if(inputs[0].getOutput() != null) {
            if(inputs[0].getOutput().getBuild().isPresent()) {
                if(inputs[0].getOutput().getBuild().get() instanceof HeightField) {
                    return Optional.of((HeightField) inputs[0].getOutput().getBuild().get());
                }
            }
        }
        return Optional.empty();
    }

    public static Optional<Pair<Input, Output>> join(Input input, Output output) {
        if(input instanceof HeightmapInput & output instanceof HeightmapOutput) {
            input.setOutput(output);
            output.addConnection(input);
            return Optional.of(new Pair<>(input, output));
        }
        return Optional.empty();
    }

    public static Device disconnect(Device device) {
        for(int i = 0; i < device.getInputs().length; i++) {
            device.getInputs()[i].setOutput(null);
        }
        for(int o = 0; o < device.getOutputs().length; o++) {
            device.getOutputs()[o].resetPayload();
            for(Input input : device.getOutputs()[o].getConnectedInputs()) {
                input.setOutput(null);
            }
        }
        return device;
    }
}
