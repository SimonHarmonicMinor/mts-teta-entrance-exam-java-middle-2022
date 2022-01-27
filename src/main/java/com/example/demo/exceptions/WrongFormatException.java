package com.example.demo.exceptions;

import com.example.demo.enums.Result;

public class WrongFormatException extends DemoApplicationException {

    public WrongFormatException() {
        super(Result.WRONG_FORMAT.name());
    }

}
