package com.example.demo.exception;

import com.example.demo.entity.User;

public class AccessDeniedException extends WorkflowException {
    public AccessDeniedException(User user, User user1) {
        super(String.format("Task of user \"%s\" can't be affect by \"%s\"", user.getName(), user1.getName()));
    }
}
