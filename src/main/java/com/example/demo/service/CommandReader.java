package com.example.demo.service;

import com.example.demo.exception.ServiceException;
import com.example.demo.model.CommandRequest;

public interface CommandReader {
    CommandRequest readFromString(String input) throws ServiceException;
}
