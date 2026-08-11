package com.httpserver;

import com.httpserver.config.Configuration;
import com.httpserver.config.ConfigurationManager;

public class HttpServer {
    public static void main( String[] args ) {
        System.out.println( "Hello World!" );

        ConfigurationManager.getInstance().loadConfigurationFile("/home/daksh/HTTPServer/src/main/resources/http.json");
        Configuration configuration = ConfigurationManager.getInstance().getCurrentConfiguration();

        System.out.println(configuration.getPort());
    }
}
