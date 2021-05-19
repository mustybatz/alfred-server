package com.alfred.httpserver.http;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {
    private final String HTTP_VERSION = "HTTP/1.1";
    private final static String CRLF = "\r\n";

    public static void sendHttpResponse(OutputStream response, HttpStatusCode code, String message, byte[] target) {
        String responseString = "HTTP/1.1 " + code.STATUS_CODE +  " " + message + CRLF +
                "Content-Length: " + target.length + CRLF + CRLF;

        try {
            response.write(responseString.getBytes());
            response.write(target);
            response.write((CRLF + CRLF).getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void sendHttpResponse(OutputStream response, HttpStatusCode code, String message) {
        String responseString = "HTTP/1.1 " + code.STATUS_CODE +  " " + message + CRLF +
                CRLF + CRLF;

        try {
            response.write(responseString.getBytes());
            response.write((CRLF + CRLF).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
