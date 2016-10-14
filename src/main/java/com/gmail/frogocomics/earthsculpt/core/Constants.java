package com.gmail.frogocomics.earthsculpt.core;

import java.io.File;

import javax.swing.JFileChooser;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class Constants {

    private Constants() {
    }

    public static final String VERSION = "0.0.2";
    public static final File ROOT_DIRECTORY = new File(new JFileChooser().getFileSystemView().getDefaultDirectory().getAbsolutePath() + "\\EarthSculpt");

}
