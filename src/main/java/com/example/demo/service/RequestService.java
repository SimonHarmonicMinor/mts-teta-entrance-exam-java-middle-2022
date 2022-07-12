package com.example.demo.service;

import com.example.demo.model.CommandRequest;
import com.example.demo.model.Task;
import com.example.demo.model.enums.Command;
import com.example.demo.model.enums.ServiceResult;
import com.example.demo.repository.TaskRepository;

public class RequestService {
    private final TaskService taskService = new TaskService();
    private final FormatService formatService = new FormatService();
    public String getResponse(String request) {
        CommandRequest cmd;
        try {
            cmd = formatService.parseRequest(request);
        } catch (Exception e) {
            return ServiceResult.WRONG_FORMAT.toString();
        }

        try {
            checkAccess(cmd);
        } catch (Exception e) {
            return ServiceResult.ACCESS_DENIED.toString();
        }
        return taskService.processCommand(cmd);

    }
    private void checkAccess(CommandRequest commandRequest) throws Exception {
        if (Command.CLOSE_TASK.equals(commandRequest.getCommand()) ||
                Command.DELETE_TASK.equals(commandRequest.getCommand()) ||
                Command.REOPEN_TASK.equals(commandRequest.getCommand())
        ) {
            Task task = TaskRepository.getByTaskName(commandRequest.getArg());
            if (!commandRequest.getUserName().equals(task.getUserName())) {
                throw new Exception("Access denied!");
            }
        }
    }
}
