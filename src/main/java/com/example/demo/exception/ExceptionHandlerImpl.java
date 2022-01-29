package com.example.demo.exception;

import com.example.demo.entity.Result;

import java.util.Map;

public class ExceptionHandlerImpl implements ExceptionHandler {

    private Map<String, Result> resultMap;


    @Override
    public String handle(Exception e) {
        return null;
    }
}
