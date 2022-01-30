package com.example.demo.requests.exceptions;

public class UnknownCommandException extends Exception{
    public UnknownCommandException() {
        super("UNKNOWN COMMAND");
    }
}
