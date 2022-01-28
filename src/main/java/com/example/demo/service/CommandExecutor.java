package com.example.demo.service;

import com.example.demo.entity.Request;
import com.example.demo.entity.Result;

public interface CommandExecutor {


    String execute(Request request);
}
