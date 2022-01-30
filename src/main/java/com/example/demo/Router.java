package com.example.demo;

import com.example.demo.controllers.Controller;
import com.example.demo.requests.Request;
import com.example.demo.responses.Response;

public class Router {
    private final Controller controller;

    public Router(Controller controller) {
        this.controller = controller;
    }

    public Response call(Request request) throws Exception {
        switch (request.getCommand()) {
            case Request.CMD_CREATE_TASK:
                return this.controller.createTask(request);
            case Request.CMD_DELETE_TASK:
                return this.controller.deleteTask(request);
            case Request.CMD_CLOSE_TASK:
                return this.controller.closeTask(request);
            case Request.CMD_REOPEN_TASK:
                return this.controller.reopenTask(request);
            case Request.CMD_LIST_TASK:
                return this.controller.listTask(request);
            default:
                throw new Exception();
        }
    }
}
