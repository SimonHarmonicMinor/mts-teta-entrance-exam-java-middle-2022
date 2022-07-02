package com.example.demo.commands;

import com.example.demo.dto.Data;
import com.example.demo.dto.Request;
import com.example.demo.router.Router;

public class Get {
    public static String getListTask(Request request) {
        if (Data.getUsersTaskData().containsKey(request.getArg())) {
            return "TASKS" + " " + Data.getUsersTaskData().get(request.getArg()).toString();
        } else {
            return Router.ERROR;
        }
    }
}
