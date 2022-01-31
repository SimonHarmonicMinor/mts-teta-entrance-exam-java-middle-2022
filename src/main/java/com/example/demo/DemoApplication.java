package com.example.demo;

import com.example.demo.controller.Controller;
import com.example.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DemoApplication {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);

    public static void main(String[] args) throws IOException {
        Controller controller = new Controller(new TaskService());
        Server server = new Server(controller);
        server.start();
        LOG.info("Сервис запущен");
    }
}
