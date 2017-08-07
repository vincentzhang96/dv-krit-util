package com.divinitor.kritika.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface StructReader<T> {

    default T read(InputStream inputStream) throws IOException, StructReadException {
        return read(inputStream, 0);
    }

    T read(InputStream inputStream, int bytesRead) throws IOException, StructReadException;

    T read(ByteBuffer byteBuffer) throws StructReadException;
}
