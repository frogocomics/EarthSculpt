package com.gmail.frogocomics.earthsculpt.core;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class ProjectSettings {

    private ProjectSettings() {
    }

    //Build resolution of the world.
    public static int SIZE = 4096;

    //The version of the devices.
    public static int VERSION = 2;

    //The size of the world.
    public static int WORLD_SIZE = 1; //kilometers

    public static int THREADS = Runtime.getRuntime().availableProcessors() * 2;

    public static boolean COMPRESS_DATA = true;

}
