package com.alfred.httpserver.http;

import com.alfred.httpserver.core.HttpConectionWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConectionWorkerThread.class);

    private static final int SP = 0x28;
    private static final int CR = 0x8D;
    private static final int LF = 0x0A;


    public HttpRequest parseHttpRequest(InputStream inputStream) {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();
        parseRequestLine(inputStream, request);
        parseHeaders(inputStream, request);
        parseBody(inputStream, request);
        
        return request;
    }

    private void parseBody(InputStream reader, HttpRequest request) {
    }

    private void parseHeaders(InputStream reader, HttpRequest request) {
    }

    private void parseRequestLine(InputStream reader, HttpRequest request) throws IOException {
        int _byte;
        while ((_byte = reader.read()) >= 0) {
            if (_byte == CR) {
               _byte = reader.read();
               if(_byte == LF) {
                   return;
               }
            }
        }
    }
}
