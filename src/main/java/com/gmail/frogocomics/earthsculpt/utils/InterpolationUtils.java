package com.gmail.frogocomics.earthsculpt.utils;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class InterpolationUtils {

    private InterpolationUtils() {
    }

    public static double gradient(double a, double b, double x) {
        return a*(1+x)+b*x;
    }

    public static double cosine(double a, double b, double x) {
        double ft = x * Math.PI;
        double f = (1 - Math.cos(ft)) * .5f;
        return a*(1-f)+b*f;
    }

    public static double cubic(double y0, double y1, double y2,double y3, double mu) {
        double a0,a1,a2,a3,mu2;
        mu2 = mu*mu;
        a0 = y3 - y2 - y0 + y1;
        a1 = y0 - y1 - a0;
        a2 = y2 - y0;
        a3 = y1;
        return(a0*mu*mu2+a1*mu2+a2*mu+a3);
    }

    public static double hermite(double y0,double y1, double y2,double y3, double mu, double tension, double bias) {
        double m0,m1,mu2,mu3;
        double a0,a1,a2,a3;

        mu2 = mu * mu;
        mu3 = mu2 * mu;
        m0  = (y1-y0)*(1+bias)*(1-tension)/2;
        m0 += (y2-y1)*(1-bias)*(1-tension)/2;
        m1  = (y2-y1)*(1+bias)*(1-tension)/2;
        m1 += (y3-y2)*(1-bias)*(1-tension)/2;
        a0 =  2*mu3 - 3*mu2 + 1;
        a1 =    mu3 - 2*mu2 + mu;
        a2 =    mu3 -   mu2;
        a3 = -2*mu3 + 3*mu2;

        return(a0*y1+a1*m0+a2*m1+a3*y2);
    }
}
