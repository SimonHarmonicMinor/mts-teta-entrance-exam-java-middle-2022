package com.example.demo.service.handler;

import com.example.demo.service.DataService;

import static com.example.demo.enums.ServerResponse.CREATED;
import static com.example.demo.service.DataService.getInstance;

public class CreateTaskHandler implements CommandHandler {

    private final DataService service;

    public CreateTaskHandler() {
        this.service = getInstance();
    }

    @Override
    public String processCommand(String[] splitUserInput) {
        service.createTask(splitUserInput[0], splitUserInput[2]);
        return CREATED.name();
    }
}
