package com.example.demo.controller;

import com.example.demo.Security;
import com.example.demo.Storage;
import com.example.demo.Workflow;
import com.example.demo.annotation.Route;
import com.example.demo.controller.base.Controller;
import com.example.demo.entity.Task;
import com.example.demo.protocol.Request;
import com.example.demo.protocol.Response;

import java.util.List;

public class TaskController implements Controller {
    private final Workflow workflow;
    private final Storage storage;
    private final Security security;

    public TaskController(Workflow workflow, Storage storage, Security security) {
        this.workflow = workflow;
        this.storage = storage;
        this.security = security;
    }

    @Route(path = "CREATE_TASK")
    public Response createTask(Request request) throws Exception {
        if (request.getArg().isEmpty()) {
            return Response.ERROR;
        }
        storage.getTaskRepository().create(new Task(request.getUser(), request.getArg()));
        return Response.CREATED;
    }

    @Route(path = "DELETE_TASK")
    public Response deleteTask(Request request) throws Exception {
        changeTaskState(request, Task.State.DELETED);
        return Response.DELETED;
    }

    @Route(path = "CLOSE_TASK")
    public Response closeTask(Request request) throws Exception {
        changeTaskState(request, Task.State.CLOSED);
        return Response.CLOSED;
    }

    @Route(path = "REOPEN_TASK")
    public Response reopenTask(Request request) throws Exception {
        changeTaskState(request, Task.State.CREATED);
        return Response.REOPENED;
    }

    @Route(path = "LIST_TASK")
    public Response listTask(Request request) throws Exception {
        security.getUser(request.getArg());
        List<Task> tasks = storage.getTaskRepository().getTasksByUser(security.getUser(request.getArg()));
        return Response.TASKS.setPayload(tasks.toString());
    }

    private void changeTaskState(Request request, Task.State state) throws Exception {
        Task task = storage.getTaskRepository().findByTitle(request.getArg());
        workflow.apply(request.getUser(), task, state);
    }
}
