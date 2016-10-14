package com.gmail.frogocomics.earthsculpt.core.noise;

import com.gmail.frogocomics.earthsculpt.utils.HashUtils;

import static com.gmail.frogocomics.earthsculpt.utils.InterpolationUtils.cosine;
import static com.gmail.frogocomics.earthsculpt.utils.InterpolationUtils.gradient;

/**
 * <a href="https://www.youtube.com/watch?v=lDkO8YT04bE">Original Code taken from Here</a>
 *
 * @since 0.0.1
 * @author Jeff Chen
 * @author Epic M Studios
 */
public final class PerlinNoise {

    private static final int CONSTANT_1 = 15731;
    private static final int CONSTANT_2 = 789221;
    private static final int CONSTANT_3 = 1376312589;
    private static final int CONSTANT_4 = 0x7fffffff;
    private static final int CONSTANT_5 = 1073741824;

    /**
     * Generate a noise value based on the X, Y, and the seed specified.
     *
     * @param x The X coordinate
     * @param y The Y coordinate
     * @param seed  The seed todo
     * @return Returns a noise value based on the specified x, y point.
     */
    public static double generateNoise(int x, int y, long seed) {
        //new Random().nextInt();
        //int n = x * y * 573;
        //n = (n<<13) ^ n;
        //return (1f-((n*(n*n*CONSTANT_1 + CONSTANT_2) + CONSTANT_3) & CONSTANT_4) / CONSTANT_5);
        long n = HashUtils.hash32shift(seed+HashUtils.hash32shift(x+HashUtils.hash32shift(y)));
        return (1f-((n*(n*n*CONSTANT_1 + CONSTANT_2) + CONSTANT_3) & CONSTANT_4) / CONSTANT_5);
    }

    public static double generateSmoothNoise(int x, int y, long seed) {
        double corners = (generateNoise(x-1, y-1, seed) * generateNoise(x+1, y-1, seed) * generateNoise(x-1, y+1, seed) * generateNoise(x+1, y+1, seed)) / 16f;
        double sides = (generateNoise(x-1, y, seed) * generateNoise(x+1, y, seed) * generateNoise(x, y+1, seed) * generateNoise(x, y-1, seed)) / 8f;
        double center = generateNoise(x, y, seed) / 4f;
        return corners * sides * center;
    }

    /**
     * Cosine interpolation is the second simplest interpolation method that results in less
     * perlin artifacts than a linear interpolation.
     *
     * @param x The X value.
     * @param y The Y Value.
     * @param seed The seed of the perlin noise.
     * @return Returns the interpolated gradient noise.
     */
    public static double generateInterpolatedCosineNoise(double x, double y, long seed) {
        int intX = (int) x;
        double fractionX = x - intX;

        int intY = (int) y;
        double fractionY = y - intY;

        double v1 = generateSmoothNoise(intX, intY, seed);
        double v2 = generateSmoothNoise(intX + 1, intY, seed);
        double v3 = generateSmoothNoise(intX, intY + 1, seed);
        double v4 = generateSmoothNoise(intX + 1, intY + 1, seed);

        double i1 = cosine(v1, v2, fractionX);
        double i2 = cosine(v3, v4, fractionX);

        return cosine(i1, i2, fractionY);
    }

    /**
     * Linear interpolation is the simplest method of getting values at positions in between the
     * data points. The points are simply joined by straight line segments. Using the linear
     * interpolation is the fastest out of all methods, but it results in noise artifacts that may
     * seem unnatural, although it could be corrected with filters.
     *
     * @param x The X value.
     * @param y The Y Value.
     * @param seed The seed of the perlin noise.
     * @return Returns the interpolated gradient noise.
     */
    public static double generateInterpolatedGradientNoise(double x, double y, long seed) {
        int intX = (int) x;
        double fractionX = x - intX;

        int intY = (int) y;
        double fractionY = y - intY;

        double v1 = generateSmoothNoise(intX, intY, seed);
        double v2 = generateSmoothNoise(intX + 1, intY, seed);
        double v3 = generateSmoothNoise(intX, intY + 1, seed);
        double v4 = generateSmoothNoise(intX + 1, intY + 1, seed);

        double i1 = gradient(v1, v2, fractionX);
        double i2 = gradient(v3, v4, fractionX);

        return gradient(i1, i2, fractionY);
    }

    public static double generateInterpolatedCubicNoise(double x, double y, long seed) {
        int intX = (int) x;
        double fractionX = x - intX;

        int intY = (int) y;
        double fractionY = y - intY;

        //____________
        // 5 | 8 | 7 |
        // 2 | 4 | 9 |
        // 1 | 3 | 6 |
        //------------

        double v1 = generateSmoothNoise(intX, intY, seed);

        return 0; //TODO!
    }

    /**
     * Generate a standard perlin noise.
     *
     * @param x The x value.
     * @param y The y value.
     * @param persistence The persistence of the perlin noise.
     * @param octaves The amount of octaves of the perlin noise.
     * @param style The interpolation style of the perlin noise.
     * @param seed The seed of the perlin noise.
     * @return Returns a certain pixel of the standard perlin noise based on the X and Y
     *         coordinates specified.
     */
    public static double generateStandardPerlinNoise(
            double x,
            double y,
            double persistence,
            int octaves,
            PerlinInterpolationStyle style,
            long seed) {

        double total = 0;

        for(int i = 0; i < octaves; i++) {
            double frequency = Math.pow(2, 1)/128f;
            double amplitude = Math.pow(persistence,i);
            if(style == PerlinInterpolationStyle.COSINE) {
                total += generateInterpolatedCosineNoise(x * frequency, y * frequency, seed) * amplitude;
            }
            else if(style == PerlinInterpolationStyle.LINEAR){
                total += generateInterpolatedGradientNoise(x * frequency, y * frequency, seed) * amplitude;
            }
        }

        return total;
    }

    /**
     * Generate a ridged perlin noise.
     *
     * @param x The x value.
     * @param y The y value.
     * @param persistence The persistence of the perlin noise.
     * @param octaves The amount of octaves of the perlin noise.
     * @param style The interpolation style of the perlin noise.
     * @param seed The seed of the perlin noise.
     * @return Returns a certain pixel of the standard perlin noise based on the X and Y
     *         coordinates specified.
     */
    public static double generateRidgedPerlinNoise(double x, double y, double persistence, int octaves, PerlinInterpolationStyle style, long seed) {

        double total = 0;

        for(int i = 0; i < octaves; i++) {
            double frequency = Math.pow(2, 1)/128f;
            double amplitude = Math.pow(persistence,i);

            double signal = 0;

            if(style == PerlinInterpolationStyle.COSINE) {
                signal = generateInterpolatedCosineNoise(x * frequency, y * frequency, seed);
            } else if(style == PerlinInterpolationStyle.LINEAR) {
                signal = generateInterpolatedGradientNoise(x * frequency, y * frequency, seed);
            }

            signal /= signal + 1f;
            total += Math.abs(signal * amplitude);
        }

        return total;
    }

    /**
     * Generate a billowy perlin noise, which a inverted ridged perlin.
     *
     * @param x The x value.
     * @param y The y value.
     * @param persistence The persistence of the perlin noise.
     * @param octaves The amount of octaves of the perlin noise.
     * @param style The interpolation style of the perlin noise.
     * @param seed The seed of the perlin noise.
     * @return Returns a certain pixel of the standard perlin noise based on the X and Y
     *         coordinates specified.
     */
    public static double generateBillowyPerlinNoise(double x, double y, double persistence, int octaves, PerlinInterpolationStyle style, long seed) {

        double total = 0;

        for(int i = 0; i < octaves; i++) {
            double frequency = Math.pow(2, 1)/128f;
            double amplitude = Math.pow(persistence,i);

            double signal = 0;

            if(style == PerlinInterpolationStyle.COSINE) {
                signal = generateInterpolatedCosineNoise(x * frequency, y * frequency, seed);
            } else if(style == PerlinInterpolationStyle.LINEAR) {
                signal = generateInterpolatedGradientNoise(x * frequency, y * frequency, seed);
            }


            signal /= signal + 1f;
            total += Math.abs(signal * amplitude);
        }

        return 1 - total;
    }
}
