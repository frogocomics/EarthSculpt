package com.gmail.frogocomics.earthsculpt.core.connection;

import com.gmail.frogocomics.earthsculpt.core.data.WorkableType;

/**
 * Represents an input port for a specific device. Unlike the {@link Output}, an input port is much
 * simpler and cannot hold {@link WorkableType}s, and can only receive workable types from outputs,
 * which hold them.
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public interface Input extends Port {

    void setOutput(Output output);

    Output getOutput();

}
