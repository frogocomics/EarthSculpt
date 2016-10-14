package com.gmail.frogocomics.earthsculpt.core.connection;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class HeightmapInput implements Input {

    private boolean optional;
    private String label;
    private Output output;

    public HeightmapInput(boolean optional, String label) {
        this.optional = optional;
        this.label = label;
    }

    public boolean isOptional() {
        return optional;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Set the attached output that is connected to this input.
     *
     * @param output The attached output.
     */
    public void setOutput(Output output) {
        this.output = output;
    }

    /**
     * Get the output that is sending its data to.
     *
     * @return Return the output that is attached to the input.
     */
    public Output getOutput() {
        return output;
    }

}
