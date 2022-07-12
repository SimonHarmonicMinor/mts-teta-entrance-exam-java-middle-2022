package com.example.demo.service;

import java.util.List;

import com.example.demo.model.*;
import com.example.demo.model.enums.Command;
import com.example.demo.model.enums.ServiceResult;
import com.example.demo.model.enums.TaskStatus;
import com.example.demo.repository.TaskRepository;

public class TaskService {

    public String processCommand(CommandRequest commandRequest) {
        switch (commandRequest.getCommand()) {
            case CREATE_TASK:
                return createTask(commandRequest);
            case DELETE_TASK:
                return deleteTask(commandRequest);
            case CLOSE_TASK:
                return closeTask(commandRequest);
            case REOPEN_TASK:
                return reopenTask(commandRequest);
            case LIST_TASK:
                return listTask(commandRequest);
            case __DELETE_ALL:
                TaskRepository.clear();
                return "ALL CLEAR!";
            default:
                return ServiceResult.ERROR.toString();
        }
    }

    private String createTask(CommandRequest commandRequest) {
        Task task = TaskRepository.getByTaskName(commandRequest.getArg());
        if (task != null) {
            //already exist
            return ServiceResult.ERROR.toString();
        }
        TaskRepository.add(new Task(commandRequest.getArg(), commandRequest.getUserName()));
        return "CREATED";//ServiceResult.CREATED.toString();
    }

    private String deleteTask(CommandRequest commandRequest) {
        Task task = TaskRepository.getByTaskName(commandRequest.getArg());
        if (task == null || !task.getTaskStatus().equals(TaskStatus.CLOSED)) {
            return ServiceResult.ERROR.toString();
        }
        TaskRepository.getByTaskName(commandRequest.getArg()).setTaskStatus(TaskStatus.DELETED);
        return ServiceResult.DELETED.toString();
    }

    private String closeTask(CommandRequest commandRequest) {
        Task task = TaskRepository.getByTaskName(commandRequest.getArg());
        if (task == null || !task.getTaskStatus().equals(TaskStatus.CREATED)) {
            return ServiceResult.ERROR.toString();
        }
        TaskRepository.getByTaskName(commandRequest.getArg()).setTaskStatus(TaskStatus.CLOSED);
        return ServiceResult.CLOSED.toString();
    }

    private String reopenTask(CommandRequest commandRequest) {
        Task task = TaskRepository.getByTaskName(commandRequest.getArg());
        if (task == null || !task.getTaskStatus().equals(TaskStatus.CLOSED)) {
            return ServiceResult.ERROR.toString();
        }
        TaskRepository.getByTaskName(commandRequest.getArg()).setTaskStatus(TaskStatus.CREATED);
        return ServiceResult.REOPENED.toString();
    }

    private String listTask(CommandRequest commandRequest) {
        List<Task> argUserTasks = TaskRepository.getByCreatedUserName(commandRequest.getArg());
        return ServiceResult.TASKS.toString() + " " + argUserTasks.toString();
    }
}
