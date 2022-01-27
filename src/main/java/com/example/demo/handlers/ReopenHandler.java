package com.example.demo.handlers;

import com.example.demo.database.IDatabase;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;

public class ReopenHandler implements IHandler {

    @Override
    public Response execute(Request request, IDatabase db) {
        String userName = request.getUser();
        String taskName = request.getArg();

        if (!db.getTasks(userName).contains(taskName)) {
            LOG.debug("Task " + taskName + " missing from the user " + userName);
            return new Response(Result.ACCESS_DENIED);
        }

        if (db.reopenTask(userName, taskName)) {
            LOG.debug("Reopened task " + taskName);
            return new Response(Result.REOPENED);
        } else {
            LOG.debug("Failed reopened task " + taskName);
            return new Response(Result.ERROR);
        }
    }
}
