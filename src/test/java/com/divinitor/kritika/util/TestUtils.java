package com.divinitor.kritika.util;

import com.divinitor.kritika.util.io.KritStringUtils;
import org.junit.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Objects;

/**
 * Utilities for unit testing.
 */
public class TestUtils {

    /**
     * Private ctor.
     */
    private TestUtils() {
    }

    /**
     * Asserts that the given class's no-args constructor is private.
     *
     * @param clazz The class to check
     * @throws Exception If there was an error checking
     */
    public static void assertPrivateCtor(Class<?> clazz) throws Exception {
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(ctor.getModifiers()));
    }

    /**
     * Creates a byte array from a given hex string (e.g. 124F7A2C).
     *
     * @param hexData The string containing the hex data
     * @return A byte array with the given values
     */
    public static byte[] fromHexString(String hexData) {
        Objects.requireNonNull(hexData);
        if (hexData.length() % 2 != 0) {
            throw new IllegalArgumentException("Invalid hex string");
        }

        byte[] ret = new byte[hexData.length() / 2];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = (byte) Integer.parseInt(hexData.substring(i * 2, i * 2 + 2), 16);
        }

        return ret;
    }

}
