package com.example.demo.service;

import com.example.demo.entity.Request;

public interface CommandExecutor {


    String execute(Request request);
}
