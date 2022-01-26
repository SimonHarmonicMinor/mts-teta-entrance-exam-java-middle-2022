package com.example.demo.exceptions;

import com.example.demo.enums.Command;

public class UnknownCommandException extends AppException {
    public UnknownCommandException(String message) {
        super(message);
    }
    public UnknownCommandException(Command command) {
        super(command.name());
    }
}
