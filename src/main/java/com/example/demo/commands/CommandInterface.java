package com.example.demo.commands;

import com.example.demo.entity.RequestEntity;

import java.io.IOException;

public interface CommandInterface {

    String getName();

    String execute(RequestEntity request) throws IOException;
}

