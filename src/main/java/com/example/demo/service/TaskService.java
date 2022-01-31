package com.example.demo.service;

import com.example.demo.Server;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.model.Task;
import com.example.demo.model.types.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.model.types.ResponseType.ACCESS_DENIED;
import static com.example.demo.model.types.ResponseType.CLOSED;
import static com.example.demo.model.types.ResponseType.CREATED;
import static com.example.demo.model.types.ResponseType.DELETED;
import static com.example.demo.model.types.ResponseType.ERROR;
import static com.example.demo.model.types.ResponseType.REOPENED;
import static com.example.demo.model.types.ResponseType.TASKS;

public class TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(Server.class);
    private List<Task> taskList = new ArrayList<>();

    public TaskService() {
    }

    public String createTask(Request request) {
        if (!taskList.isEmpty()) {
            for (Task taskFromList : taskList) {
                if (taskFromList.getTaskName().equals(request.getRequestArgument())) {
                    return new Response(ERROR).toString();
                }
            }
        }
        Task task = new Task(request.getUser(), request.getRequestArgument(), TaskStatus.CREATED);
        taskList.add(task);
        show();
        return new Response(CREATED).toString();
    }

    public String closeTask(Request request) {
        if (!isUserTask(request)) {
            return new Response(ACCESS_DENIED).toString();
        } else {
            for (Task taskFromList : taskList) {
                if (taskFromList.getTaskName().equals(request.getRequestArgument())
                        && taskFromList.getStatus() == TaskStatus.CREATED) {
                    taskFromList.setStatus(TaskStatus.CLOSED);
                    show();
                    return new Response(CLOSED).toString();
                }
            }
        }
        return new Response(ERROR).toString();
    }

    public String deleteTask(Request request) {
        if (!isUserTask(request)) {
            return new Response(ACCESS_DENIED).toString();
        } else {
            for (Task taskFromList : taskList) {
                if (taskFromList.getTaskName().equals(request.getRequestArgument())
                        && taskFromList.getStatus() == TaskStatus.CLOSED) {
                    taskFromList.setStatus(TaskStatus.DELETED);
                    show();
                    return new Response(DELETED).toString();
                }
            }
        }
        return new Response(ERROR).toString();
    }

    public String reopenTask(Request request) {
        if (!isUserTask(request)) {
            return new Response(ACCESS_DENIED).toString();
        } else {
            for (Task taskFromList : taskList) {
                if (taskFromList.getTaskName().equals(request.getRequestArgument())
                        && taskFromList.getStatus() == TaskStatus.CLOSED) {
                    taskFromList.setStatus(TaskStatus.CREATED);
                    show();
                    return new Response(REOPENED).toString();
                }
            }
        }
        return new Response(ERROR).toString();
    }

    public String listTask(Request request) {
        List<String> userTasks = new ArrayList<>();
        for (Task taskFromList : taskList) {
            if (taskFromList.getOwner().toString().equals(request.getRequestArgument())) {
                userTasks.add(taskFromList.getTaskName());
            }
        }
        show();
        return new Response(TASKS, userTasks).toString();
    }

    private void show() {
        for (Task taskFromList : taskList) {
            LOG.info("Existing tasks: " + taskFromList);
        }
    }

    private Boolean isUserTask(Request request) {
        for (Task taskFromList : taskList) {
            if (taskFromList.getTaskName().equals(request.getRequestArgument())
                    && taskFromList.getOwner().equals(request.getUser())) {
                return true;
            }
        }
        return false;
    }

    public List<Task> getTaskList() {
        return taskList;
    }
}
