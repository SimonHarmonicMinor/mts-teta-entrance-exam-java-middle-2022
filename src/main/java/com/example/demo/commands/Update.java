package com.example.demo.commands;

import com.example.demo.dto.Data;
import com.example.demo.dto.Request;
import com.example.demo.dto.Status;
import com.example.demo.router.Router;

public class Update {
    public static String closeTask(Request request) {
        if (!Data.checkAvailabilityTask(request.getArg())) {
            return Router.ERROR;
        }
        if (Data.checkPermissionTask(request.getUser(), request.getArg())) {
            return Router.ACCESS_DENIED;
        }
        Data.getTaskStatusData().put(request.getArg(), Status.CLOSED);
        return Router.CLOSED;
    }

    public static String reOpenTask(Request request) {
        if (!Data.checkAvailabilityTask(request.getArg())) {
            return Router.ERROR;
        }
        if (Data.checkPermissionTask(request.getUser(), request.getArg())) {
            return Router.ACCESS_DENIED;
        }
        if (Data.getTaskStatusData().get(request.getArg()).equals(Status.CREATED)) {
            return Router.ERROR;
        }
        Data.getTaskStatusData().put(request.getArg(), Status.CREATED);
        return Router.REOPENED;
    }
}