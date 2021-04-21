package com.alfred.httpserver.config;

import com.alfred.httpserver.utils.Json;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ConfigurationManager {
    private static ConfigurationManager MyConfigurationManager;
    private static Configuration myCurrentConfiguration;

    private ConfigurationManager(){

    }
    public static ConfigurationManager getInstance(){
        if (MyConfigurationManager == null){
            MyConfigurationManager= new ConfigurationManager();
        }
        return MyConfigurationManager;
    }

    public void LoadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationExeption(e);
        }

        StringBuffer sb = new StringBuffer();

        int i;

        try {
            while ( (i = fileReader.read()) != -1 ) {
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationExeption(e);
        }

        JsonNode conf = null;
        try {
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationExeption("Error parsing the configuration file", e);
        }

        try {
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationExeption("Error parsing the configuration file, internal", e);
        }

    }
    public Configuration getCurrentConfig(){
        if( myCurrentConfiguration == null ) {
            throw new HttpConfigurationExeption("No current configuration set");
        }
        return myCurrentConfiguration;
    }
}
