package com.example.demo.service;

import com.example.demo.enums.Command;
import com.example.demo.enums.Result;
import com.example.demo.enums.TaskStatus;
import com.example.demo.exceptions.AccessDeniedException;
import com.example.demo.exceptions.DemoApplicationException;
import com.example.demo.exceptions.ErrorException;
import com.example.demo.exceptions.WrongFormatException;
import com.example.demo.models.Request;
import com.example.demo.models.Response;
import com.example.demo.models.Task;
import com.example.demo.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;


public class TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);

    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;

    }

    public Response processRequest(Request request) throws DemoApplicationException {

        String user = request.getUser();
        Command command = request.getCommand();
        String arg = request.getArg();

        switch (command) {
            case CREATE_TASK:
                return createTask(user, arg);
            case DELETE_TASK:
                return deleteTask(user, arg);
            case CLOSE_TASK:
                return closeTask(user, arg);
            case REOPEN_TASK:
                return reopenTask(user, arg);
            case LIST_TASK:
                return listTask(arg == null ? user : arg);
            default:
                throw new WrongFormatException();
        }
    }

    private Response createTask(String user, String name) throws DemoApplicationException {
        if (repository.existByName(name)) {
            LOG.error("Task with name \"{}\" already exist", name);
            throw new ErrorException();
        }
        repository.save(new Task(user, name));
        LOG.info("Task created: {}", name);
        return new Response(Result.CREATED);
    }

    private Response deleteTask(String user, String name) throws DemoApplicationException {
        Task task = getByNameAndTaskStatus(name, TaskStatus.CLOSED);
        checkAccess(task, user);
        repository.delete(task);
        LOG.info("Task deleted: {}", name);
        return new Response(Result.DELETED);
    }

    private Response closeTask(String user, String name) throws DemoApplicationException {
        Task task = getByNameAndTaskStatus(name, TaskStatus.CREATED);
        checkAccess(task, user);
        repository.close(task);
        LOG.info("Task closed: {}", name);
        return new Response(Result.CLOSED);
    }

    private Response reopenTask(String user, String name) throws DemoApplicationException {
        Task task = getByNameAndTaskStatus(name, TaskStatus.CLOSED);
        checkAccess(task, user);
        repository.reopen(task);
        LOG.info("Task reopened: {}", name);
        return new Response(Result.REOPENED);
    }

    private Response listTask(String user) {
        Set<String> tasks = repository.findAllNotDeleted(user);
        LOG.info("Task list:", tasks);
        return new Response(Result.TASKS, tasks);

    }

    private Task getByNameAndTaskStatus(String name, TaskStatus status) throws DemoApplicationException {
        Optional<Task> task = repository.findByNameAndTaskStatus(name, status);
        if (!task.isPresent()) {
            LOG.error("Task with name \"{}\" and {} status not found", name, status.name());
            throw new ErrorException();
        }
        return task.get();
    }

    private void checkAccess(Task task, String user) throws DemoApplicationException {
        if (!task.getUser().equals(user)) {
            LOG.error("Access denied: Task with name \"{}\" belongs to another user", task.getName());
            throw new AccessDeniedException();
        }
    }

}
