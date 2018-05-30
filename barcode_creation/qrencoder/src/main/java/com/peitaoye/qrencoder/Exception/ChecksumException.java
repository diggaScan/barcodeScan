package com.peitaoye.qrencoder.Exception;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//



public final class ChecksumException extends ReaderException {
    private static final ChecksumException INSTANCE;

    private ChecksumException() {
    }

    private ChecksumException(Throwable cause) {
        super(cause);
    }

    public static ChecksumException getChecksumInstance() {
        return isStackTrace ? new ChecksumException() : INSTANCE;
    }

    public static ChecksumException getChecksumInstance(Throwable cause) {
        return isStackTrace ? new ChecksumException(cause) : INSTANCE;
    }

    static {
        (INSTANCE = new ChecksumException()).setStackTrace(NO_TRACE);
    }
}
