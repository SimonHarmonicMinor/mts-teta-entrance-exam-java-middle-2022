package com.example.demo.exception;

import com.example.demo.entity.Status;

public class WrongFormatException extends RuntimeException{
    public WrongFormatException() {
        super(Status.WRONG_FORMAT.toString());
    }
}
