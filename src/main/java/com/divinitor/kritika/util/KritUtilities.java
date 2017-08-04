package com.divinitor.kritika.util;

public class KritUtilities {

    private KritUtilities() {}

    public static String printHexDump(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02X", Byte.toUnsignedInt(b)));
        }
        return builder.toString();
    }

}
