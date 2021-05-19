package com.alfred.httpserver.core;

import com.alfred.httpserver.config.ConfigurationManager;
import com.alfred.httpserver.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


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
                HttpResponse.sendHttpResponse(
                        outputStream,
                        e.getErrorCode(),
                        e.getMessage()
                        );
                return;
            }

            byte[] target = null;
            String webroot = ConfigurationManager.getInstance().getCurrentConfig().getWebroot();
            try {
                target = RequestTargetHandler.seekFile(
                        webroot ,request.getRequestTarget()
                );
            } catch (HttpParsingException e) {
                LOGGER.error(String.valueOf(e));
                HttpResponse.sendHttpResponse(
                        outputStream,
                        e.getErrorCode(),
                        e.getMessage()
                );

            }

            HttpResponse.sendHttpResponse(
                    outputStream,
                    HttpStatusCode.CLIENT_OK_200,
                    "OK",
                    target
            );

            LOGGER.info("* Connection finished.");
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
