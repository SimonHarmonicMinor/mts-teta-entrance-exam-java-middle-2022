package com.example.demo.commands;

import com.example.demo.dto.Data;
import com.example.demo.dto.Request;
import com.example.demo.dto.Status;
import com.example.demo.router.Router;

import java.util.ArrayList;
import java.util.List;

public class Create {
    public static String createTask(Request request) {
        if (Data.checkAvailabilityTask(request.getArg())) {
            return Router.ERROR;
        }
        List<String> tasks = new ArrayList<>();
        if (Data.getUsersTaskData().containsKey(request.getUser())) {
            tasks = Data.getUsersTaskData().get(request.getUser());
        }

        tasks.add(request.getArg());
        Data.getUsersTaskData().put(request.getUser(), tasks);
        Data.getTaskStatusData().put(request.getArg(), Status.CREATED);
        return Router.CREATED;
    }
}