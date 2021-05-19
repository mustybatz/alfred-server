package com.alfred.httpserver.http;

import com.alfred.httpserver.core.HttpConnectionWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * This class helps to parse and serialize incoming HTTP requests.
 */
public class HttpParser {
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpParser.class);

    // ASCII code for different types of characters,
    private static final int SP = 0x20;
    private static final int CR = 0x0D;
    private static final int LF = 0x0A;

    /**
     * This function parses an HTTP request incoming from a
     * inputStream, it parses the requestLine, headers and body.
     * @param inputStream Incoming request as an inputStream
     * @return returns an object serialized with the request data.
     */
    public HttpRequest parseHttpRequest(InputStream inputStream) throws HttpParsingException {
        InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.US_ASCII);

        HttpRequest request = new HttpRequest();
        try {
            parseRequestLine(inputStream, request);
        } catch (IOException e) {
            e.printStackTrace();
        }
        parseHeaders(inputStream, request);
        parseBody(inputStream, request);
        
        return request;
    }

    private void parseBody(InputStream reader, HttpRequest request) {
    }

    /**
     * Function to parse the incoming request headers.
     * @param reader Incoming request as a InputStream
     * @param request HttpRequest to serialize values to
     */
    private void parseHeaders(InputStream reader, HttpRequest request) {
    }

    /**
     * Function to parse the requestLine from an HTTP/1.1 request
     * @param reader Incoming request as a InputStream
     * @param request HttpRequest to serialize values into.
     * @throws IOException Throws an IOException if we receive a bad request.
     */
    private void parseRequestLine(InputStream reader, HttpRequest request) throws IOException, HttpParsingException {

        // Control vars
        boolean methodParsed = false;
        boolean requestTargetParsed = false;

        // We use a StringBuilder as a buffer to save incoming data.
        StringBuilder processingDataBuffer = new StringBuilder();
        int _byte;

        // While the client keeps sending data, we read it and save into the _byte variable
        while ((_byte = reader.read()) >= 0) {

            // If our byte is an CR character, we continue reading data.
            if (_byte == CR) {
               _byte = reader.read();
               // If the _byte is a LF character, it means that the request line is over, so we return.
               if(_byte == LF) {
                   LOGGER.debug("REQUEST LINE VERSION TO PROCESS: {}", processingDataBuffer.toString());

                   if(!methodParsed || !requestTargetParsed) {
                       throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                   }
                   return;
               }
            }

            // We seek for spaces as values are incoming, when we find one it means that
            // we are receiving the request line in this specific order:
            // - HTTP METHOD
            // - TARGET (file that is being requested)
            // - HTTP VERSION (In this case we just accept HTTP/1.1)
            if(_byte == SP) {
                LOGGER.info("SP detected");

                // If our HTTP method has not been parsed, we parse it.
                if(!methodParsed) {
                    LOGGER.debug("REQUEST LINE METHOD TO PROCESS: {}", processingDataBuffer.toString());
                    request.setMethod(processingDataBuffer.toString());
                    methodParsed = true;
                    // Otherwise it means that we need to parse the target.
                } else if(!requestTargetParsed) {
                    LOGGER.debug("REQUEST LINE TARGET TO PROCESS: {}", processingDataBuffer.toString());
                    request.setRequestTarget(processingDataBuffer.toString());
                    requestTargetParsed = true;
                } else {
                    throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_400_BAD_REQUEST);
                }

                // Every time we receive a space we clear our buffer.
                processingDataBuffer.delete(0, processingDataBuffer.length());

                // If the current char is not a SP, we just append it into the buffer.
            } else {
                processingDataBuffer.append((char)_byte);

                if(!methodParsed) {
                    if(processingDataBuffer.length() > HttpMethod.MAX_LENGTH) {
                        throw new HttpParsingException(
                                HttpStatusCode.SERVER_ERROR_501_NOT_IMPLEMENTED
                        );
                    }
                }
            }
        }
    }
}
