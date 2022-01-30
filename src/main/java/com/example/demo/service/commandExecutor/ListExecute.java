package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Выполнение задачи LIST_TASK
 */

public class ListExecute implements CommandExecutor {

    private final UserRepository userRepository;

    public ListExecute(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(Request request) {
//        User currentUser = userRepository.getUserByName(request.getUserName());
        List<String> taskName = userRepository.getUserByName(request.getAdditionalParam()).map(User::getTaskName).orElse(List.of());
//        List<String> taskName = currentUser.getTaskName();
//        if (nonNull(taskName)) {
            return Result.TASKS.name() + " " + taskName;
//        } else return Result.TASKS.name() + new ArrayList<>();
    }
}
