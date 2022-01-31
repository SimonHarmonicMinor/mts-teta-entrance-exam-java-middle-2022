package com.example.demo.service;

import com.example.demo.entity.Task;

public interface Validator {
    boolean validateTaskIsAbsent(String taskName);
    boolean validateTaskIsPresent(String taskName);
    boolean validateTaskBelongsToUser(Task task, String userName);
    boolean validateTaskStateMatch(Task task, Task.State state);
    boolean validateUserIsPresent(String userName);
}
