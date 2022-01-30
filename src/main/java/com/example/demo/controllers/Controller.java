package com.example.demo.controllers;

import com.example.demo.models.Task;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.responses.Response;
import com.example.demo.services.TaskService;
import com.example.demo.requests.Request;
import com.example.demo.services.exceptions.CurrentStatusException;

import java.util.ArrayList;


public class Controller {
    private final TaskService taskService;

    public Controller() {
        this.taskService = new TaskService(new TaskRepository(), new UserRepository());
    }

    public Response createTask(Request request) throws Exception {
        this.taskService.createTask(request.getArg());
        return this.getResponse("CREATED", null);
    }

    public Response deleteTask(Request request) throws Exception {
        try {
            this.taskService.deleteTask(request.getArg());
            return this.getResponse("DELETED", null);
        } catch (CurrentStatusException e) {
            return this.getResponse(e.getMessage(), null);
        }
    }

    public Response closeTask(Request request) throws Exception {
        this.taskService.closeTask(request.getArg());
        return this.getResponse("CLOSED", null);
    }

    public Response reopenTask(Request request) throws Exception {
        this.taskService.reopenTask(request.getArg());
        return this.getResponse("REOPENED", null);
    }

    public Response listTask(Request request) throws Exception {
        ArrayList<String> out = new ArrayList<>();
        for (Task task : this.taskService.listTask(request.getArg())) {
            out.add(task.getTitle());
        }
        return this.getResponse("LIST", out.toString());
    }

    private Response getResponse(String result, String arg) {
        return new Response(result, arg);
    }
}
