package com.example.demo;


import com.example.demo.handlers.RequestHandler;
import com.example.demo.repository.TaskRepository;
import com.example.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DemoApplication {

    private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        LOG.info("Hello, world");
        RequestHandler requestHandler = new RequestHandler(new TaskService(new TaskRepository()));

        try (Server server = new Server(requestHandler)) {
            server.start();
        } catch (IOException e) {
            LOG.error("IOException: {}", e);
        }

    }

}
