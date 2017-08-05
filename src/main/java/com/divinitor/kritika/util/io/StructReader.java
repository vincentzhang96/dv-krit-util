package com.divinitor.kritika.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public interface StructReader<T> {

    T read(InputStream inputStream) throws IOException, StructReadException;

    T read (ByteBuffer byteBuffer) throws StructReadException;
}
