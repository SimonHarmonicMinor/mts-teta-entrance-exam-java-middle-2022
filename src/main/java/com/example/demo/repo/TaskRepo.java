package com.example.demo.repo;

import com.example.demo.enums.Result;
import com.example.demo.enums.TaskStatus;
import com.example.demo.exceptions.*;
import com.example.demo.model.Request;
import com.example.demo.model.Response;
import com.example.demo.model.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaskRepo {
    private static final Logger LOG = LoggerFactory.getLogger(TaskRepo.class);
    private final List<Task> tasks = new ArrayList<>();

    public Response applyCommand(Request request) {
        switch (request.getCommand()) {
            case CREATE_TASK:
                return createTask(request.getArg(), request.getUser());
            case CLOSE_TASK:
                return closeTask(request.getArg(), request.getUser());
            case REOPEN_TASK:
                return reopenTask(request.getArg(), request.getUser());
            case DELETE_TASK:
                return deleteTask(request.getArg(), request.getUser());
            case LIST_TASK:
                return getUsersTasks(request.getArg());
            default:
                LOG.error("Unexpected command: {}", request.getCommand());
                throw new UnknownCommandException(request.getCommand());
        }
    }

    private Response createTask(String taskName, String user) {
        if (findActiveTask(taskName) == null) {
            tasks.add(new Task(taskName, user));
            LOG.info("Task created: {}", taskName);
            return new Response(Result.CREATED);
        } else {
            LOG.error("Task already exist: {}", taskName);
            throw new TaskAlreadyExistException(taskName);
        }
    }

    private Response closeTask(String taskName, String user) {
        setStatus(taskName, user, TaskStatus.CLOSED);
        LOG.info("Task closed: {}", taskName);
        return new Response(Result.CLOSED);
    }

    private Response reopenTask(String taskName, String user) {
        setStatus(taskName, user, TaskStatus.REOPENED);
        LOG.info("Task reopened: {}", taskName);
        return new Response(Result.REOPENED);
    }

    private Response deleteTask(String taskName, String user) {
        setStatus(taskName, user, TaskStatus.DELETED);
        LOG.info("Task deleted: {}", taskName);
        return new Response(Result.DELETED);
    }

    private Response getUsersTasks(String user) {
        List<String> userTaskNames = this.tasks
                .stream()
                .filter(t -> t.getUser().equals(user) && !isTaskDeleted(t))
                .map(Task::getName)
                .collect(Collectors.toList());
        LOG.info("Tasks listed: {}", userTaskNames);
        return new Response(Result.TASKS, userTaskNames);
    }

    private Task findActiveTask(String taskName) {
        return tasks
                .stream()
                .filter(t -> taskName.equals(t.getName()) && !isTaskDeleted(t))
                .findFirst()
                .orElse(null);
    }

    private boolean isTaskDeleted(Task task) {
        return TaskStatus.DELETED.equals(task.getStatus());
    }

    private void setStatus(String taskName, String user, TaskStatus newStatus) {
        Optional.ofNullable(findActiveTask(taskName))
                .ifPresentOrElse(t -> setStatus(t, user, newStatus), () -> {
                    LOG.error("Tasks not exist: {}", taskName);
                    throw new TaskNotExistException(taskName);
                });
    }

    private boolean isTaskBelongToUser(Task task, String user) {
        return task.getUser().equals(user);
    }

    private void setStatus(Task task, String user, TaskStatus newStatus) {
        if (isTaskBelongToUser(task, user)) {
            if (TaskStatus.DELETED.equals(newStatus) && TaskStatus.CREATED.equals(task.getStatus())) {
                LOG.error("Attempt to set status deleted to tasks with status created : {}", task.getName());
                throw new CannotDeleteInStatusCreatedException(task.getName());
            } else {
                LOG.info("Changed task status: task {}, to {}", task.getName(), newStatus);
                task.setStatus(newStatus);
            }
        } else {
            LOG.error("Attempt to set status to tasks not belonged to user: {} task: {}", user, task.getName());
            throw new TaskNotBelongToUserException(task.getName());
        }
    }
}
