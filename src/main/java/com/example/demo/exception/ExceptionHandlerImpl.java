package com.example.demo.exception;

import com.example.demo.entity.Result;

import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlerImpl implements ExceptionHandler {

    private Map<String, Result> resultMap = new HashMap<>();

    {
        resultMap.put(FormatException.class.getCanonicalName(), Result.WRONG_FORMAT);
        resultMap.put(RightException.class.getCanonicalName(), Result.ACCESS_DENIED);
        resultMap.put(Error.class.getCanonicalName(), Result.ERROR);
    }

    @Override
    public String handle(Exception e) {
        return resultMap.getOrDefault(e.getClass().getCanonicalName(), Result.ERROR).name();
    }
}
