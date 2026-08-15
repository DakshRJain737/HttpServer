package com.httpserver.core.io;

public class WebRootNotFoundException extends Exception {
    public WebRootNotFoundException() {
    }

    public WebRootNotFoundException(String message) {
        super(message);
    }
}
