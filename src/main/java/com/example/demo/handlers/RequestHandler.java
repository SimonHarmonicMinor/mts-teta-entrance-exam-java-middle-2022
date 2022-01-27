package com.example.demo.handlers;

import com.example.demo.parsers.RequestParser;
import com.example.demo.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);
    private final TaskService service;

    public RequestHandler(TaskService service) {
        this.service = service;
    }

    public String handle(String rawRequest) {
        LOG.info("Request: {}", rawRequest);
        try {
            return service.processRequest(RequestParser.parse(rawRequest)).toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
