package com.example.demo.utils.handlers;

import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.repo.TaskRepo;
import com.example.demo.utils.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestHandler {
    private static final Logger LOG = LoggerFactory.getLogger(RequestHandler.class);
    private final TaskRepo storage;
    private final Mapper mapper;
    private final ErrorHandler handler;

    public RequestHandler(TaskRepo storage, Mapper mapper, ErrorHandler handler) {
        this.storage = storage;
        this.mapper = mapper;
        this.handler = handler;
    }

    public String handle(String requestStr) {
        try {
            Request request = mapper.inputToRequest(requestStr);
            Response response = storage.applyCommand(request);
            return response.getAsString();
        } catch (Exception e) {
            LOG.error("Request processing error: {}", requestStr);
            return handler.handle(e).getAsString();
        }
    }
}
