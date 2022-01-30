package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.BaseCommandResult;
import com.example.demo.model.Command;

public interface CommandExecutor {
    BaseCommandResult execute(Command command) throws ServiceException;
}
