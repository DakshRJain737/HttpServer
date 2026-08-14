package com.httpserver.http;

public class BadHttpVersionException extends Exception {
    public BadHttpVersionException() {
        super();
    }

    public BadHttpVersionException(String message) {
        super(message);
    }
}
