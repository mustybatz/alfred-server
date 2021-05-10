package com.alfred.httpserver.core;

import com.alfred.httpserver.config.ConfigurationManager;
import com.alfred.httpserver.http.HttpParser;
import com.alfred.httpserver.http.HttpParsingException;
import com.alfred.httpserver.http.HttpRequest;
import com.alfred.httpserver.http.RequestTargetHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpConnectionWorkerThread extends Thread {

    private final Socket socket;

    /**
     * Constructor that returns an HttpConnectionWorkerThread object.
     * @param socket connection to handle.
     */
    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private final static HttpParser parser = new HttpParser();


    @Override
    /**
     * Function to handle a connection on a different thread.
     */
    public void run() {


        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            // We get both input and output streams to read and send data from the socket.
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            HttpRequest request = null;

            try {
                request = parser.parseHttpRequest(inputStream);
            } catch (HttpParsingException e) {
                // TODO Handle request here
                e.printStackTrace();
            }

            assert request != null;
            RequestTargetHandler targetHandler = new RequestTargetHandler(
                    ConfigurationManager.getInstance().getCurrentConfig().getWebroot(),
                    request.getRequestTarget()
            );


            byte[] target = null;
            try {
                target = targetHandler.seekFile();
            } catch (HttpParsingException e) {
                // Handle error
                e.printStackTrace();
            }


            String html = "<html> <head><title>Me pica el huevo izquierdo</title></head> <body ><h1 style=\"color=\"red\"\">WHY DO YOU CUM</h1></body> </html>";
            final String CRLF = "\r\n";

            // We build an HTTP response
            String response =
                    "HTTP/1.1 200 OK" + CRLF +

                            "Content-Length: " + target.length + CRLF +
                    CRLF;

            outputStream.write(response.getBytes());
            outputStream.write(target);
            outputStream.write((CRLF + CRLF).getBytes());


            LOGGER.info("* Connection finished puta.");
        } catch (IOException e) {
            LOGGER.error("Problems with communication aqui funca", e);
            e.printStackTrace();
        } finally {

            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {}
            }
            if(outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {}
            }

            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }
    }
}
