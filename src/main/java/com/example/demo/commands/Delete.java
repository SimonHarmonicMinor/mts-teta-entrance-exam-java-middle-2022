package com.example.demo.commands;

import com.example.demo.dto.Data;
import com.example.demo.dto.Request;
import com.example.demo.dto.Status;
import com.example.demo.router.Router;

import java.util.HashMap;
import java.util.List;

public class Delete {
    public static String deleteTask(Request request) {
        if (!Data.checkAvailabilityTask(request.getArg())) {
            return Router.ERROR;
        }
        if (Data.checkPermissionTask(request.getUser(), request.getArg())) {
            return Router.ACCESS_DENIED;
        }
        if (Data.getTaskStatusData().get(request.getArg()).equals(Status.CREATED)) {
            return Router.ERROR;
        }
        Data.getTaskStatusData().remove(request.getArg());
        List<String> tasks = Data.getUsersTaskData().get(request.getUser());
        tasks.remove(request.getArg());
        Data.getUsersTaskData().put(request.getUser(), tasks);
        return Router.DELETED;
    }

    public static String deleteAll() {
        Data.setUsersTaskData(new HashMap<>());
        Data.setTaskStatusData(new HashMap<>());
        return Router.DELETED;
    }
}
