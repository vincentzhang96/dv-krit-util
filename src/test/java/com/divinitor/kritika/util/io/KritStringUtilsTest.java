package com.divinitor.kritika.util.io;

import com.divinitor.kritika.util.TestUtils;
import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import static com.divinitor.kritika.util.TestUtils.assertPrivateCtor;
import static com.divinitor.kritika.util.io.KritStringUtils.readSPString;
import static com.divinitor.kritika.util.io.KritStringUtils.writeSPString;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class KritStringUtilsTest {

    private static final String TEST_STR = "test";
    private static final byte[] TEST_STR_BYTES = TestUtils.fromHexString("04007400650073007400");

    @Test
    public void utilityClass() throws Exception {
        assertPrivateCtor(KritStringUtils.class);
    }

    @Test
    public void readSPStringTest() throws Exception {
        LittleEndianDataInputStream ldis = new LittleEndianDataInputStream(new ByteArrayInputStream(TEST_STR_BYTES));
        assertEquals(TEST_STR, readSPString(ldis));
    }

    @Test
    public void readSPStringByteBufferTest() throws Exception {
        assertEquals(TEST_STR, readSPString(ByteBuffer.wrap(TEST_STR_BYTES).order(ByteOrder.LITTLE_ENDIAN)));
    }

    @Test
    public void writeSPStringTest() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int written = writeSPString(TEST_STR, new LittleEndianDataOutputStream(out));
        assertArrayEquals(TEST_STR_BYTES, out.toByteArray());
        assertEquals(TEST_STR_BYTES.length, written);
    }

    @Test
    public void writeSPStringByteBufferTest() throws Exception {
        ByteBuffer buf = ByteBuffer.allocate(TEST_STR_BYTES.length).order(ByteOrder.LITTLE_ENDIAN);
        int written = writeSPString(TEST_STR, buf);
        buf.flip();
        assertArrayEquals(TEST_STR_BYTES, buf.array());
        assertEquals(TEST_STR_BYTES.length, written);
    }
}
