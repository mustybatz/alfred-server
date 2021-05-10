package com.alfred.httpserver.http;

import ch.qos.logback.core.util.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RequestTargetHandler {
    protected String webroot;
    protected String target;

    public RequestTargetHandler(String webroot, String target) {
        this.webroot = webroot;

        if (target.equals("/")) {
            this.target = "\\index.html";
        } else {
            this.target = target.replace("/", "\\");
        }
    }

    public byte[] seekFile() throws HttpParsingException {

        Path path = Paths.get(this.webroot  + this.target);
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new HttpParsingException(HttpStatusCode.CLIENT_ERROR_404_RESOURCE_NOT_FOUND);
        }

    }

}
