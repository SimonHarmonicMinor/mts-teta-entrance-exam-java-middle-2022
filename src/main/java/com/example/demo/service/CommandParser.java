package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.CommandRequest;

public interface CommandParser {
    CommandRequest parseFromString(String input) throws ServiceException;
}
