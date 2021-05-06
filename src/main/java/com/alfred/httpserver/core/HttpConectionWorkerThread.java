package com.alfred.httpserver.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpConectionWorkerThread extends Thread {

    private Socket socket;

    public HttpConectionWorkerThread(Socket socket) {
        this.socket = socket;
    }
    private final static Logger LOGGER = LoggerFactory.getLogger(HttpConectionWorkerThread.class);

    @Override
    public void run() {


        InputStream inputStream = null;
        OutputStream outputStream= null;

        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            int inputbytes;

            while((inputbytes = inputStream.read()) >= 0) {
                System.out.print((char) inputbytes);
            }

            String html = "<html> <head><title>Me pica el huevo izquierdo</title></head> <body ><h1 style=\"color=\"red\"\">WHY DO YOU CUM</h1></body> </html>";
            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                            "Content-Length: " + html.getBytes().length + CRLF +
                            CRLF +
                            html +
                            CRLF + CRLF;

            outputStream.write(response.getBytes());


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
