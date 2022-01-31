package com.example.demo.service;

import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.service.handler.CreateHandler;
import com.example.demo.service.handler.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class DispatcherImpl implements Dispatcher {
    private static final Logger LOG = LoggerFactory.getLogger(CreateHandler.class);

    private final Map<Request.Command, Handler> handlers;

    public DispatcherImpl(List<Handler> handlers) {
        this.handlers = handlers.stream().collect(Collectors.toMap(Handler::getCommand, value -> value));
    }

    @Override
    public Response dispatch(String requestString) {
        try {
            Request request = parseRequest(requestString);
            if (!request.isSuccessful())
                return new Response(Response.Result.WRONG_FORMAT, false);

            if (!handlers.containsKey(request.getCommand())) {
                LOG.error("Handler matching '" + request.getCommand() + "' command not found");
                return new Response(Response.Result.ERROR, false);
            }

            return handlers.get(request.getCommand()).handle(request.getUser(), request.getArg());
        }
        catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return new Response(Response.Result.ERROR, false);
        }
    }

    /**
     * Parse and validate request
     * @param line incoming request string
     * @return parsed request or null
     */
    private Request parseRequest(String line) {
        if(line == null || line.isBlank()) {
            LOG.error("Incoming request is empty or blank string");
            return new Request(false);
        }

        List<String> requestParts = Arrays.asList(line.replaceAll("( +)", " ").trim().split(" "));
        if(requestParts.size() != 3) {
            LOG.error("Incoming request doesn't meet the format requirements");
            return new Request(false);
        }

        Optional<Request.Command> command = Request.Command.fromValue(requestParts.get(1));
        if(command.isEmpty()) {
            LOG.error("Command matching string '" + requestParts.get(1) + "' not found");
            return new Request(false);
        }

        LOG.info("Request parsed successfully");
        return new Request(requestParts.get(0), command.get(), requestParts.get(2));
    }
}
