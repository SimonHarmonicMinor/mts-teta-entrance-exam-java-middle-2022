package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DemoApplication {
    private final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();

        server.stop();
    }
}
