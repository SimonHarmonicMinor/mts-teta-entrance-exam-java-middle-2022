package com.example.demo.handlers;

import com.example.demo.database.IDatabase;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;

import java.util.Set;

public class TaskListHandler implements IHandler {

    @Override
    public Response execute(Request request, IDatabase db) {
        Set<String> userTaskSet = db.getTasks(request.getArg());
        Response response = new Response(Result.TASKS);

        response.setArgs(userTaskSet);

        LOG.debug("Forming tasks for user " + request.getArg());
        return response;
    }
}
