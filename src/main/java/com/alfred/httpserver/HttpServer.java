package com.alfred.httpserver;

import com.alfred.httpserver.config.Configuration;
import com.alfred.httpserver.config.ConfigurationManager;
import com.alfred.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    /**
     * On this function the server is started and the httpconf.json file is loaded, parsed and serialized into
     * a Configuration Object.
     *
     * Then we instantiate a ServerListenerThread object which starts listening on a specified port and it can
     * serve files that exists on the webroot location.
     */
    public static void main(String[] args) {

        LOGGER.info("Server starting...");
        ConfigurationManager.getInstance().LoadConfigurationFile("src/httpconf.json");

        Configuration conf =  ConfigurationManager.getInstance().getCurrentConfig();

        LOGGER.info("Using port: " + conf.getPort());
        LOGGER.info("Using webroot: " + conf.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(conf.getPort(), conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
