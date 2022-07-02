package com.example.demo.router;

import com.example.demo.commands.Create;
import com.example.demo.commands.Delete;
import com.example.demo.commands.Get;
import com.example.demo.commands.Update;
import com.example.demo.dto.Request;

public class Router {

    public final static String CREATE_TASK = "CREATE_TASK";
    public final static String CLOSE_TASK = "CLOSE_TASK";
    public final static String DELETE_TASK = "DELETE_TASK";
    public final static String REOPEN_TASK = "REOPEN_TASK";
    public final static String LIST_TASK = "LIST_TASK";
    public final static String __DELETE_ALL = "__DELETE_ALL";

    public final static String CREATED = "CREATED";
    public final static String DELETED = "DELETED";
    public final static String CLOSED = "CLOSED";
    public final static String REOPENED = "REOPENED";

    public final static String WRONG_FORMAT = "WRONG_FORMAT";
    public final static String ACCESS_DENIED = "ACCESS_DENIED";
    public final static String ERROR = "ERROR";

    public static String logicApp(String fullCommand) {
        Request request = new Request(fullCommand);
        if (request.getValidate()) {
            switch (request.getCommand()) {
                case CREATE_TASK: {
                    return Create.createTask(request);
                }
                case CLOSE_TASK: {
                    return Update.closeTask(request);
                }
                case DELETE_TASK: {
                    return Delete.deleteTask(request);
                }
                case REOPEN_TASK: {
                    return Update.reOpenTask(request);
                }
                case LIST_TASK: {
                    return Get.getListTask(request);
                }
                case __DELETE_ALL: {
                    return Delete.deleteAll();
                }
                default:
                    return Router.WRONG_FORMAT;
            }
        } else {
            return Router.WRONG_FORMAT;
        }
    }

}