package com.example.demo.exceptions;

import com.example.demo.enums.Result;

public class ErrorException extends DemoApplicationException {

    public ErrorException() {
        super(Result.ERROR.name());
    }

}