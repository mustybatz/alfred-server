package com.alfred.httpserver.http;

import java.io.OutputStream;

public class HttpResponse {
    private final String HTTP_VERSION = "HTTP/1.1";
    private final String CRLF = "\r\n";

    protected OutputStream response;
    protected HttpStatusCode statusCode;
    protected String reasonPhrase;
    public HttpResponse(HttpStatusCode statusCode, String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }

    public void sendResponse (OutputStream outputStream) {
        response = outputStream;
    }

    public String toString() {
        return HTTP_VERSION + statusCode.toString() + CRLF;
    }

}
