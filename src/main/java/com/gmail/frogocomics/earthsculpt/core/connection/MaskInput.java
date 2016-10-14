package com.gmail.frogocomics.earthsculpt.core.connection;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class MaskInput implements Input {

    private Output output;

    public boolean isOptional() {
        return true;
    }

    public String getLabel() {
        return "Mask";
    }

    @Override
    public void setOutput(Output output) {
        this.output = output;
    }

    @Override
    public Output getOutput() {
        return output;
    }
}
