package com.gmail.frogocomics.earthsculpt.core.data;

import com.gmail.frogocomics.earthsculpt.core.ProjectSettings;

import java.io.Serializable;

/**
 * A heightfield represents a 2d collection of heights, with 0.0 being the lowest part and 1.0
 * being the highest part of the world. This is used to set the maximum and minimum world heights.
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class HeightField implements WorkableType, Serializable {

    private int x;
    private int y;
    private double[][] heights;
    private int resolution;

    public HeightField(int x, int y, double[][] heights) throws Exception {
        new HeightField(x, y, heights, ProjectSettings.SIZE);
    }

    public HeightField(int x, int y, double[][] heights, int resolution) throws Exception {
        this.x = x;
        this.y = y;
        this.heights = heights;
        this.resolution = resolution;
        if(heights.length > resolution) {
            throw new Exception();
        }
    }

    /**
     * Get the height values in a two dimensional array.
     *
     * @return Returns the height values contained witin the heightfield.
     */
    public double[][] getHeights() {
        return heights;
    }

    /**
     * Get the length of a side.
     *
     * @return Returns the length of the side.
     */
    public int getLengthX() {
        return x;
    }

    /**
     * Get the length of a side.
     *
     * @return Returns the length of the side.
     */
    public int getLengthY() {
        return y;
    }

    /**
     * Get the user specified build resolution of the heightfield.
     *
     * @return Returns the user specified build resolution.
     */
    public int getResolution() {
        return this.resolution;
    }

}
