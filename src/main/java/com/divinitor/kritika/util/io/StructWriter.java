package com.divinitor.kritika.util.io;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public interface StructWriter<T> {

    void write(T t, OutputStream outputStream) throws IOException;

    byte[] writeToArray(T t);

    ByteBuffer writeToBuffer(T t);
}
