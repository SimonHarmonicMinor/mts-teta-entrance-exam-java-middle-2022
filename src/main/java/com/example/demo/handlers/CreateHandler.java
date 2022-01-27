package com.example.demo.handlers;

import com.example.demo.database.IDatabase;
import com.example.demo.enums.Result;
import com.example.demo.models.Request;
import com.example.demo.models.Response;

public class CreateHandler implements IHandler {

    @Override
    public Response execute(Request request, IDatabase db) {
        String userName = request.getUser();
        String taskName = request.getArg();

        if (!db.checkUsers(userName)) {
            db.addUser(userName);
            LOG.debug("Created new user " + userName);
        }

        if (db.checkTasks(taskName)) {
            LOG.debug("Task with name " + taskName + " exist");
            return new Response(Result.ERROR);
        }

        if (db.addTask(userName, taskName)) {
            LOG.debug("Created new task " + taskName + " for user " + userName);
            return new Response(Result.CREATED);
        } else {
            return new Response(Result.ERROR);
        }

    }
}
