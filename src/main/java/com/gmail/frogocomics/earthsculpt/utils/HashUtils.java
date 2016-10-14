package com.gmail.frogocomics.earthsculpt.utils;

/**
 *
 *
 * @since 0.0.1
 * @author Jeff Chen
 */
public final class HashUtils {

    private HashUtils() {
    }

    public static long hash32shift(long key) {
        key = ~key + (key << 15); // key = (key << 15) - key - 1;
        key = key ^ (key >>> 12);
        key = key + (key << 2);
        key = key ^ (key >>> 4);
        key = key * 2057; // key = (key + (key << 3)) + (key << 11);
        key = key ^ (key >>> 16);
        return key;
    }

}
