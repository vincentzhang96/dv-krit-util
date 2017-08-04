package com.divinitor.kritika.util.crypto;

import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

public class RotatingXorTest {

    @Test
    public void keyBytes() throws Exception {
        RotatingXor xor = new RotatingXor(0x11223344);
        Assert.assertEquals(0x11, Byte.toUnsignedInt(xor.keyBytes[0]));
        Assert.assertEquals(0x22, Byte.toUnsignedInt(xor.keyBytes[1]));
        Assert.assertEquals(0x33, Byte.toUnsignedInt(xor.keyBytes[2]));
        Assert.assertEquals(0x44, Byte.toUnsignedInt(xor.keyBytes[3]));
    }

    @Test
    public void keyRotations() throws Exception {
        RotatingXor xor = new RotatingXor(0x11223344);
        Assert.assertEquals(0x11223344, xor.rotations[0]);
        Assert.assertEquals(0x22334411, xor.rotations[1]);
        Assert.assertEquals(0x33441122, xor.rotations[2]);
        Assert.assertEquals(0x44112233, xor.rotations[3]);
    }

    @Test
    public void offset() throws Exception {
        RotatingXor xor = new RotatingXor(0);
        //  INIT
        Assert.assertEquals(0, xor.getOffset());

        //  ADVANCE
        xor.xor((byte) 0);
        Assert.assertEquals(1, xor.getOffset());

        //  INIT2
        xor = new RotatingXor(0, 2);
        Assert.assertEquals(2, xor.getOffset());

        //  ROLLAROUND
        xor.xor((byte) 0);
        xor.xor((byte) 0);
        Assert.assertEquals(0, xor.getOffset());
    }

    @Test
    public void getKey() throws Exception {
        RotatingXor xor = new RotatingXor(1234);

        //  INIT
        Assert.assertEquals(1234, xor.getKey());
    }

    @Test
    public void setOffset() throws Exception {
        RotatingXor xor = new RotatingXor(0, 2);

        //  SET
        xor.setOffset(3);
        Assert.assertEquals(3, xor.getOffset());

        //  MOD 4
        xor.setOffset(6);
        Assert.assertEquals(2, xor.getOffset());
    }

    @Test
    public void xor() throws Exception {
        int key = 0xFFF77700;
        byte b = 0x0F;
        RotatingXor xor = new RotatingXor(key);
        Assert.assertEquals(0xF0, Byte.toUnsignedInt(xor.xor(b)));
        Assert.assertEquals(0xF8, Byte.toUnsignedInt(xor.xor(b)));
        Assert.assertEquals(0x78, Byte.toUnsignedInt(xor.xor(b)));
        Assert.assertEquals(0x0F, Byte.toUnsignedInt(xor.xor(b)));
    }

    @Test
    public void xor1() throws Exception {
        int key = 0xFFF77700;
        int i = 0x12345678;
        RotatingXor xor = new RotatingXor(key);
        Assert.assertEquals(0xEDC32178, xor.xor(i));
        Assert.assertEquals(0xEDC32178, xor.xor(i));
        xor.setOffset(1);
        Assert.assertEquals(0xE5435687, xor.xor(i));
        xor.setOffset(2);
        Assert.assertEquals(0x6534A98F, xor.xor(i));
        xor.setOffset(3);
        Assert.assertEquals(0x12CBA10F, xor.xor(i));

        Assert.assertEquals(i, xor.xor(xor.xor(i)));
    }

    @Test
    public void xor2() throws Exception {
        RotatingXor xor = new RotatingXor(0x11223344);

        long l = 0x1122334400000000L;
        Assert.assertEquals(0x11223344L, xor.xor(l));
        Assert.assertEquals(l, xor.xor(xor.xor(l)));
    }

    @Test
    public void xor3() throws Exception {
        RotatingXor xor = new RotatingXor(0x11223344);

        String s = "the quick brown fox is big";
        byte[] data = s.getBytes(StandardCharsets.UTF_8);

        xor.xor(data);
        xor.reset();
        xor.xor(data);

        Assert.assertEquals(s, new String(data, StandardCharsets.UTF_8));
    }

    @Test
    public void reset() throws Exception {
        RotatingXor xor = new RotatingXor(1, 2);

        xor.reset();
        Assert.assertEquals(0, xor.getOffset());
    }

}
