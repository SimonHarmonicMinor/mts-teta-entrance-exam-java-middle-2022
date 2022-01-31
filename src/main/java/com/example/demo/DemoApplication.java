package com.example.demo;


import com.example.demo.config.TaskConfig;
import com.example.demo.logic.TaskController;
import com.example.demo.logic.TaskResponser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DemoApplication {
    private final Logger log = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }
}
