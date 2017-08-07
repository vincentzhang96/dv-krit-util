package com.divinitor.kritika.util;

import com.divinitor.kritika.util.io.KritStringUtils;
import org.junit.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Objects;

public class TestUtils {

    private TestUtils() {}

    public static void assertPrivateCtor(Class<?> clazz) throws Exception {
        Constructor<?> ctor = clazz.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(ctor.getModifiers()));
    }

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
