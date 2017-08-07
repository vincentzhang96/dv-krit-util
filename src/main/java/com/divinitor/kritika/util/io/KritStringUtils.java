package com.divinitor.kritika.util.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.*;

/**
 * Provides methods to read/write strings from Kritika files.
 */
public class KritStringUtils {

    /**
     * Private constructor (static utility class).
     */
    private KritStringUtils() {
    }

    /**
     * Reads a length prefixed string from the input stream.
     * The string <b>codepoint count</b> is read out as a short first,
     * then the string data is read in and decoded.
     * @param in The input stream to read from.
     * @return The decoded string.
     * @throws IOException If there was an error reading the data.
     */
    public static String readSPString(DataInput in)
            throws IOException {
        //  Multiply by 2 to get data length
        int len = in.readUnsignedShort() * 2;
        byte[] buf = new byte[len];
        in.readFully(buf);

        return new String(buf, UTF_16LE);
    }

    /**
     * Reads a length prefixed string from the data buffer.
     * The string <b>codepoint count</b> is read out as a short first,
     * then the string data is read in and decoded.
     * @param in The buffer to read from.
     * @return The decoded string.
     */
    public static String readSPString(ByteBuffer in) {
        //  Multiply by 2 to get data length
        int len = Short.toUnsignedInt(in.getShort()) * 2;
        byte[] buf = new byte[len];
        in.get(buf);

        return new String(buf, UTF_16LE);
    }

    /**
     * Writes a length prefixed string to the output stream.
     * The string <b>codepoint</b> count is written out as a short first,
     * then the string data encoded as UTF-16LE is written out.
     * @param s The string to write.
     * @param out The output stream to write to.
     * @return The number of bytes written.
     * @throws IOException If there was an error writing the data.
     */
    public static int writeSPString(String s, DataOutput out)
            throws IOException {
        byte[] data = s.getBytes(UTF_16LE);

        //  Divide by 2 to get the number of codepoints. Cap out at 65535.
        int len = Math.min(data.length / 2, 0xFFFF);
        out.writeShort(len);

        //  Write the string data
        int bytesToWrite = len * 2;
        out.write(data, 0, bytesToWrite);

        return 2 + bytesToWrite;
    }

    /**
     * Writes a length prefixed string to the data buffer.
     * The string <b>codepoint</b> count is written out as a short first,
     * then the string data encoded as UTF-16LE is written out.
     * @param s The string to write.
     * @param out The buffer to write to.
     * @return The number of bytes written.
     */
    public static int writeSPString(String s, ByteBuffer out) {
        byte[] data = s.getBytes(UTF_16LE);

        //  Divide by 2 to get the number of codepoints. Cap out at 65535.
        int len = Math.min(data.length / 2, 0xFFFF);
        out.putShort((short) len);

        //  Write the string data
        int bytesToWrite = len * 2;
        out.put(data, 0, bytesToWrite);

        return 2 + bytesToWrite;
    }
}
