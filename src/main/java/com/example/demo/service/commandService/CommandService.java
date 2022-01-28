package com.example.demo.service.commandService;

import com.example.demo.exception.DemoException;

public interface CommandService {

    void validatePermission(String userName, String arg) throws DemoException;
    String executeCommand(String userName, String arg);
    String getName();

}
