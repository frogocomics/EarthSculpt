package com.gmail.frogocomics.earthsculpt.utils;

import java.awt.*;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class ScreenUtils {

    private static int width = -1;
    private static int height = -1;

    private ScreenUtils() {
    }

    public static int getWidth() {
        if(width == -1) {
            width = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
        }
        return width;
    }

    public static int getHeight() {
        if(height == -1) {
            height = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
        }
        return height;
    }

}
