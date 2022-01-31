package com.example.demo.service.handler;

import com.example.demo.service.DataService;

import static com.example.demo.enums.ServerResponse.TASKS;
import static com.example.demo.service.DataService.getInstance;

public class ListTaskHandler implements CommandHandler {

    private final DataService service;

    public ListTaskHandler() {
        this.service = getInstance();
    }

    @Override
    public String processCommand(String[] splitUserInput) {
        return TASKS.name() + " " + service.getUserTaskList(splitUserInput[2]);
    }
}
