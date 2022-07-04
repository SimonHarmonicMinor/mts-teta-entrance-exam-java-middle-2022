package com.example.demo.api.controller;

import com.example.demo.model.Task;

import java.util.List;

public interface ITaskController {

    String showTaskList(String user);

    String createTask(String name, String user);

    String clearTasks();

    String removeTaskByName(String name, String user);

    String closeTaskByName(String name, String user);

    String reopenTaskByName(String name, String user);

    String showTaskListByUser(String user);

}
