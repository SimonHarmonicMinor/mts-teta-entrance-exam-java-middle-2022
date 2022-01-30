package com.example.demo.repositories.exceptions;

public class TaskExistsException extends Exception{
    public TaskExistsException() {
        super("TASK WITH THIS NAME EXISTS");
    }
}
