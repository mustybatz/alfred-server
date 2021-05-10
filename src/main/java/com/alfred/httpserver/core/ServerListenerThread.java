package com.alfred.httpserver.core;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListenerThread extends Thread{

    private final static Logger LOGGER = LoggerFactory.getLogger(ServerListenerThread.class);

    private int port;
    private String webroot;
    private ServerSocket serverSocket;

    /**
     * Creates a new ServerListenerThread Object.
     * @param port port in which the server is going to listen.
     * @param webroot filepath for directory of files that are going to be served.
     * @throws IOException throws IOException if the port is already in use.
     */
    public ServerListenerThread(int port, String webroot) throws IOException {
        this.port = port;
        this.webroot = webroot;
        this.serverSocket = new ServerSocket(this.port);
    }


    @Override
    /**
     * Function to run the ServerListenerThread on a dedicated thread.
     */
    public void run() {
        try {

            // While the ServerSocket is not closed and is bound we listen to
            // new incoming connections.
            while(serverSocket.isBound() && !serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();

                LOGGER.info(" * Connection accepted: " + socket.getInetAddress());

                // When a new connection arrives, we spawn a HttpConnectionWorkerThread
                // to handle the new connection on a different thread.
                HttpConnectionWorkerThread workerThread = new HttpConnectionWorkerThread(socket);
                workerThread.start();

            }


        } catch (IOException e) {
            LOGGER.error("Problem with setting socker", e);
        } finally {
            if(serverSocket!=null) {
                try {
                    serverSocket.close();
                } catch (IOException e) { }
            }
        }
    }
}
