package com.example.demo.handlers;

import com.example.demo.database.IDatabase;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;

public class TaskListHandler implements IHandler {
    @Override
    public Response execute(Request request, IDatabase db) {
        Response response = new Response(Result.TASKS);
        response.setArgs(db.getTasks(request.getUser()));

        return response;
    }
}
