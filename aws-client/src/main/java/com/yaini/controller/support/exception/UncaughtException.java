package com.yaini.controller.support.exception;

public class UncaughtException extends RuntimeException {

    public UncaughtException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public UncaughtException(final String message) {
        super(message);
    }

    public UncaughtException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
