package com.example.demo;


import com.example.demo.repo.TaskRepo;
import com.example.demo.utils.Mapper;
import com.example.demo.utils.handlers.ErrorHandler;
import com.example.demo.utils.handlers.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class DemoApplication {
    private static final Logger LOG = LoggerFactory.getLogger(DemoApplication.class);

    public static void main(String[] args) {
        LOG.info("Hello, server!");

        try {
            RequestHandler handler = new RequestHandler(new TaskRepo(), new Mapper(), new ErrorHandler());
            Server server = new Server(handler);
            server.start();
        } catch (IOException e) {
            LOG.error("Server IOException: ", e);
        } catch (Exception e) {
            LOG.error("Server Exception: ", e);
        }
    }
}
