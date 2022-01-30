package com.example.demo.service;

import com.example.demo.model.Command;
import com.example.demo.model.CommandRequest;

public interface CommandRequestHandler {
    Command handle(CommandRequest commandRequest);
}
