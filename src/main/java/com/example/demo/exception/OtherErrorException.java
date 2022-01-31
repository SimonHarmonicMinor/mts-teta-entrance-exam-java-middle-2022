package com.example.demo.exception;

import com.example.demo.entity.Status;

public class OtherErrorException extends RuntimeException {
    public OtherErrorException() {
        super(Status.ERROR.toString());
    }
}
