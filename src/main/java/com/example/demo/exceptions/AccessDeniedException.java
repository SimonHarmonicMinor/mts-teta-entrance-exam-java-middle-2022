package com.example.demo.exceptions;

import com.example.demo.enums.Result;

public class AccessDeniedException extends DemoApplicationException {

    public AccessDeniedException() {
        super(Result.ACCESS_DENIED.name());
    }

}
