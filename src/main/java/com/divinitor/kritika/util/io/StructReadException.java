package com.divinitor.kritika.util.io;

public class StructReadException extends RuntimeException {

    private final long offset;
    private final String fieldName;

    public StructReadException(long offset, String fieldName) {
        super(format(offset, fieldName));
        this.offset = offset;
        this.fieldName = fieldName;
    }

    public StructReadException(String message, long offset, String fieldName) {
        super(format(message, offset, fieldName));
        this.offset = offset;
        this.fieldName = fieldName;
    }

    public StructReadException(String message, Throwable cause, long offset, String fieldName) {
        super(format(message, offset, fieldName), cause);
        this.offset = offset;
        this.fieldName = fieldName;
    }

    public StructReadException(Throwable cause, long offset, String fieldName) {
        super(format(offset, fieldName), cause);
        this.offset = offset;
        this.fieldName = fieldName;
    }

    public StructReadException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, long offset, String fieldName) {
        super(format(message, offset, fieldName), cause, enableSuppression, writableStackTrace);
        this.offset = offset;
        this.fieldName = fieldName;
    }

    private static String format(long offset, String fieldName) {
        return String.format("%08X: %s", offset, fieldName);
    }

    private static String format(String msg, long offset, String fieldName) {
        return String.format("%s\n%08X: %s", msg, offset, fieldName);
    }

    public long getOffset() {
        return offset;
    }

    public String getFieldName() {
        return fieldName;
    }
}
