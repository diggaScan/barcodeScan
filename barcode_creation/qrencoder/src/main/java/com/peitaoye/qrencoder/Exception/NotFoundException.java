package com.peitaoye.qrencoder.Exception;

public final class NotFoundException extends ReaderException {
    private static final NotFoundException INSTANCE;

    private NotFoundException() {
    }

    public static NotFoundException getNotFoundInstance() {
        return INSTANCE;
    }

    static {
        (INSTANCE = new NotFoundException()).setStackTrace(NO_TRACE);
    }
}
