package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommandExecutor;

import java.util.List;

/**
 * Выполнение задачи LIST_TASK
 */

public class ListExecute implements CommandExecutor {

    UserRepository userRepository;
    TaskRepository taskRepository;

    @Override
    public Result execute(Request request) {
        User currentUser = userRepository.getUserByName(request.getUserName());
        List<String> taskName = currentUser.getTaskName();
    }
}
