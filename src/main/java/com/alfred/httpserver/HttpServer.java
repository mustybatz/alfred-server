package com.alfred.httpserver;

import com.alfred.httpserver.config.Configuration;
import com.alfred.httpserver.config.ConfigurationManager;

public class HttpServer {
    public static void main(String[] args) {

        System.out.println("Server starting...");
        ConfigurationManager.getInstance().LoadConfigurationFile("src/httpconf.json");

        Configuration conf =  ConfigurationManager.getInstance().getCurrentConfig();

        System.out.println(conf.getPort());
        System.out.println(conf.getWebroot());

    }
}
