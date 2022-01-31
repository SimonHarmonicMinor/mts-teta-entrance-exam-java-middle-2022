package com.example.demo.service.handler;

import com.example.demo.entity.Task;
import com.example.demo.entity.User;
import com.example.demo.enums.TaskStatus;
import com.example.demo.service.DataService;
import com.example.demo.validators.AccessValidator;

import static com.example.demo.enums.ServerResponse.CLOSED;
import static com.example.demo.service.DataService.getInstance;

public class CloseTaskHandler implements CommandHandler {

    private final DataService service;

    public CloseTaskHandler() {
        this.service = getInstance();
    }

    @Override
    public String processCommand(String[] splitUserInput) {
        Task task = service.getTaskByNameWithValidate(splitUserInput[2]);
        User user = service.getUserByName(splitUserInput[0]);
        AccessValidator.validate(user, task);
        task.changeTaskStatus(TaskStatus.CLOSED);
        return CLOSED.name();
    }
}
