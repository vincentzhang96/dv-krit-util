package com.divinitor.kritika.util;

import org.junit.Assert;
import org.junit.Test;

import static com.divinitor.kritika.util.TestUtils.assertPrivateCtor;
import static com.divinitor.kritika.util.TestUtils.fromHexString;

public class TestUtilsTest {

    @Test(expected = AssertionError.class)
    public void testPrivateCtorTest() throws Exception {
        assertPrivateCtor(PublicCtorCLass.class);
    }

    @Test
    public void testPrivateCtorPrivateTest() throws Exception {
        assertPrivateCtor(PrivateCtorClass.class);
    }

    @Test(expected = NoSuchMethodException.class)
    public void testPrivateCtorNoZeroArgTest() throws Exception {
        assertPrivateCtor(NoZeroArgCtorClass.class);
    }

    @Test
    public void fromHexStringTest() throws Exception {
        byte[] data = {0x00, 0x12, 0x34, 0x56, 0x78,
                (byte) 0x9a, (byte) 0xbc, (byte) 0xde, (byte) 0xfA, (byte) 0xBC, (byte) 0xDE, (byte) 0xF0};
        String strData = "00123456789abcdefABCDEF0";
        Assert.assertArrayEquals(data, fromHexString(strData));
    }

    @Test(expected = IllegalArgumentException.class)
    public void fromHexStringBadLenTest() throws Exception {
        String strData = "00123456789abcdefABCDEF";
        fromHexString(strData);
    }

    @Test(expected = NumberFormatException.class)
    public void fromHexStringBadCharTest() throws Exception {
        String strData = "001234g6789abcdefABCDEF0";
        fromHexString(strData);
    }

    public static class PublicCtorCLass {
        public PublicCtorCLass() {
        }
    }

    public static class PrivateCtorClass {
        private PrivateCtorClass() {
        }
    }

    public static class NoZeroArgCtorClass {
        public NoZeroArgCtorClass(int dummy) {
        }
    }
}
