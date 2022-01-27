package com.example.demo.services;

import com.example.demo.models.Command;
import com.example.demo.models.Response;
import com.example.demo.models.User;

public interface CommandProcessor {
    Response process(Command command, User user, String arg);
}
