package com.example.demo.exception;

import com.example.demo.entity.Status;

public class AccessDeniedException extends RuntimeException{
    public AccessDeniedException() {
        super(Status.ACCESS_DENIED.toString());
    }
}
