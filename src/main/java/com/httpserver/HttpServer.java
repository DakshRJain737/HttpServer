package com.httpserver;

import com.httpserver.config.Configuration;
import com.httpserver.config.ConfigurationManager;
import com.httpserver.core.ServerListenerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public static void main( String[] args ) {

        LOGGER.info("Hello World!");

        ConfigurationManager.getInstance().loadConfigurationFile("/home/daksh/HTTPServer/src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        LOGGER.info("Using port: " + configuration.getPort());
        LOGGER.info("Using Webroot: " + configuration.getWebroot());

        try {
            ServerListenerThread serverListenerThread = new ServerListenerThread(configuration.getPort(), configuration.getWebroot());
            serverListenerThread.start();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
