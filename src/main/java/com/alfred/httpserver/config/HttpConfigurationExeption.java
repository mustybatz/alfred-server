package com.alfred.httpserver.config;

public class HttpConfigurationExeption extends RuntimeException{
    public HttpConfigurationExeption() {
    }

    public HttpConfigurationExeption(String message) {
        super(message);
    }

    public HttpConfigurationExeption(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpConfigurationExeption(Throwable cause) {
        super(cause);
    }
}
