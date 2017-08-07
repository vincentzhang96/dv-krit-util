package com.divinitor.kritika.util;

import com.google.common.io.LittleEndianDataInputStream;
import com.google.common.io.LittleEndianDataOutputStream;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * General utilities used in Divinitor Kritika projects.
 */
public class KritUtilities {

    private KritUtilities() {
    }

    /**
     * Prints a byte array as a hex dump string (e.g. A48C44F1)
     *
     * @param bytes The bytes to dump
     * @return A string with the bytes written out as a continuous list of hex values
     */
    public static String printHexDump(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", Byte.toUnsignedInt(b)));
        }

        return builder.toString();
    }

    /**
     * Ensures that the provided InputStream is Little Endian.
     *
     * @param inputStream The input stream to cast or convert to LittleEndianDataInputStream
     * @return The input stream as a LittleEndianDataInputStream
     */
    public static LittleEndianDataInputStream ensureLittleEndian(InputStream inputStream) {
        LittleEndianDataInputStream dis;
        if (inputStream instanceof LittleEndianDataInputStream) {
            dis = (LittleEndianDataInputStream) inputStream;
        } else {
            dis = new LittleEndianDataInputStream(inputStream);
        }

        return dis;
    }

    /**
     * Ensures that the provided OutputStream is Little Endian.
     *
     * @param outputStream The output stream to cast or convert to LittleEndianDataOutputStream
     * @return The output stream as a LittleEndianDataOutputStream
     */
    public static LittleEndianDataOutputStream ensureLittleEndian(OutputStream outputStream) {
        LittleEndianDataOutputStream dis;
        if (outputStream instanceof LittleEndianDataOutputStream) {
            dis = (LittleEndianDataOutputStream) outputStream;
        } else {
            dis = new LittleEndianDataOutputStream(outputStream);
        }

        return dis;
    }
}
