package com.example.demo.service;

import com.example.demo.entity.Request;

public interface CommandExecutor {


    void execute(Request request);
}
