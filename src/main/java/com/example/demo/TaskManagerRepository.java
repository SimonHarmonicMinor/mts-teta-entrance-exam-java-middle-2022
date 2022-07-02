package com.example.demo;

import com.example.demo.enums.ETaskStatus;
import com.example.models.Request;
import com.example.models.Response;
import com.example.models.Task;

import java.util.List;

public interface TaskManagerRepository {

    Response changeTaskStatus(Request request, ETaskStatus taskStatus);

    Response deleteTask(Request request);

    Response createTask(Request request);

    Response getListTask(Request request);

    void deleteAllUserTasks();

    Response addNewTask(List<Task> tasks, Request request);
}
