package com.example.demo.services;

import com.example.demo.models.Task;
import com.example.demo.models.statuses.ClosedStatus;
import com.example.demo.models.statuses.CreatedStatus;
import com.example.demo.models.statuses.DeletedStatus;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.requests.Request;
import com.example.demo.requests.exceptions.AcessDeniedException;
import com.example.demo.services.exceptions.CurrentStatusException;

import java.util.ArrayList;

public class TaskService {

    public static final int FORWARD = 1;
    public static final int BACKWARD = 2;

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public void createTask(String title) throws Exception {
        Task task = new Task(this.userRepository.getUser(), title);
        this.taskRepository.createTask(task);
    }

    public void deleteTask(String title) throws Exception {
        Task task = this.taskRepository.getTask(title);
        this.setStatus(task, DeletedStatus.ID, FORWARD);
    }

    public void closeTask(String title) throws Exception {
        Task task = this.taskRepository.getTask(title);
        this.setStatus(task, ClosedStatus.ID, FORWARD);
    }

    public void reopenTask(String title) throws Exception {
        Task task = this.taskRepository.getTask(title);
        this.setStatus(task, CreatedStatus.ID, BACKWARD);
    }

    public void setStatus(Task task, int statusId, int direction) throws Exception {
        if (direction == FORWARD) {
            this.moveStatusForward(task, statusId);
        } else if (direction == BACKWARD) {
            this.moveStatusBackward(task, statusId);
        } else {
            throw new CurrentStatusException();
        }
        this.taskRepository.setTask(task);
    }

    public ArrayList<Task> listTask(String user) throws Exception {
        return this.taskRepository.listTask(user);
    }

    public static void checkPermissions(Request request, TaskRepository taskRepository) throws Exception {
        switch (request.getCommand()) {
            case Request.CMD_CLOSE_TASK:
            case Request.CMD_DELETE_TASK:
            case Request.CMD_REOPEN_TASK:
                Task task = taskRepository.getTask(request.getArg());
                if (!task.getUser().equals(request.getUser())) {
                    throw new AcessDeniedException();
                }
        }
    }

    private void moveStatusForward(Task task, int statusId) throws Exception {
        if (!task.getStatus().next() || task.getStatus().getId() != statusId) {
            throw new CurrentStatusException();
        }
    }

    private void moveStatusBackward(Task task, int statusId) throws Exception {
        if (!task.getStatus().previous() || task.getStatus().getId() != statusId) {
            throw new CurrentStatusException();
        }
    }
}
