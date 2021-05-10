package com.alfred.httpserver.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
    private static ObjectMapper myObjectMapper = new ObjectMapper();


    private static ObjectMapper defaultObjectMapper() {
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    }

    /**
     * Function that parses a json as a string into a JsonNode.
     * @param jsonSrc json file as a string
     * @return JsonNode object
     * @throws IOException Throws an exception if the json file is not correct.
     */
    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    /**
     * Function that serializes a jsonNode into a given Object.
     * @param node JsonNode containing the json data as a data structure.
     * @param clazz Class to serialize values to.
     * @param <T> Type of given class.
     * @return returns an object of type T
     * @throws JsonProcessingException Throws an exception if processing failed.
     */
    public static <T> T fromJson(JsonNode node, Class<T> clazz) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, clazz);
    }

    /**
     * Function that turns an object into a JsonNode
     * @param obj Object to turn into json
     * @return JsonNode of the given object.
     */
    public static JsonNode toJson(Object obj) {
        return myObjectMapper.valueToTree(obj);
    }

    /**
     * Methods to stringify a given JsonNode.
     * @param node Json node to stringify
     * @return a string containing the json.
     * @throws JsonProcessingException Throws an exception if the given json is incorrect.
     */
    public static String stringify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    /**
     * Methods to pretty stringify a given JsonNode.
     * @param node Json node to stringify
     * @return a string containing the json.
     * @throws JsonProcessingException Throws an exception if the given json is incorrect.
     */
    public static String stringifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    /**
     * Function to generate a json from a given object
     * @param o Object that's going to be used to generate the json.
     * @param pretty Boolean that helps us to know if we should use the pretty format or the normal format.
     * @return Object as a json string.
     * @throws JsonProcessingException Throws an exception if the given json is incorrect.
     */
    private static String generateJson(Object o, boolean pretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();

        if(pretty) {
            objectWriter.with(SerializationFeature.INDENT_OUTPUT);
        }

        return objectWriter.writeValueAsString(o);
    }
}
