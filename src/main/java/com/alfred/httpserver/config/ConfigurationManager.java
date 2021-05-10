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

    private ConfigurationManager(){ }

    /**
     * ConfigurationManager class is a singleton, on this function we make sure that the object is only
     * instantiated once.
     * @return
     */
    public static ConfigurationManager getInstance(){
        if (MyConfigurationManager == null){
            MyConfigurationManager= new ConfigurationManager();
        }
        return MyConfigurationManager;
    }

    /**
     * Function that seeks for a given configuration file and loads it's content into a class.
     * @param filePath
     */
    public void LoadConfigurationFile(String filePath) {
        FileReader fileReader = null;
        try {
            // We open the file.
            fileReader = new FileReader(filePath);
        } catch (FileNotFoundException e) {
            throw new HttpConfigurationExeption(e);
        }

        StringBuffer sb = new StringBuffer();

        int i;

        try {
            while ( (i = fileReader.read()) != -1 ) {
                // We read the file and append it's contents into a stringBuffer
                sb.append((char)i);
            }
        } catch (IOException e) {
            throw new HttpConfigurationExeption(e);
        }

        JsonNode conf = null;
        try {
            // We parse the json into a JsonNode
            conf = Json.parse(sb.toString());
        } catch (IOException e) {
            throw new HttpConfigurationExeption("Error parsing the configuration file", e);
        }

        try {
            // If everything went alright, the json is serialized into a Configuration object.
            myCurrentConfiguration = Json.fromJson(conf, Configuration.class);
        } catch (JsonProcessingException e) {
            throw new HttpConfigurationExeption("Error parsing the configuration file, internal", e);
        }

    }

    /**
     * Function that returns the current loaded configuration.
     * @return Configuration object containing port and webroot.
     */
    public Configuration getCurrentConfig(){
        if( myCurrentConfiguration == null ) {
            throw new HttpConfigurationExeption("No current configuration set");
        }
        return myCurrentConfiguration;
    }
}
