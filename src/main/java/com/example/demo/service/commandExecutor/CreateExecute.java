package com.example.demo.service.commandExecutor;

import com.example.demo.entity.*;
import com.example.demo.repository.TaskRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CommandExecutor;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Выполнение задачи CREATE_TASK
 */


public class CreateExecute implements CommandExecutor {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public CreateExecute(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Override
    public String execute(Request request) {
        Task task = new Task();
        task.setName(request.getAdditionalParam());
        task.setStatus(Status.CREATED);
        task.setUserName(request.getUserName());
        taskRepository.addTask(task);
        // проверка на наличие юзера и создание нового, если такого нет
        Optional<User> optionalUser = userRepository.getUserByName(request.getUserName());
        optionalUser.ifPresentOrElse(user -> user.addTaskName(request.getAdditionalParam()), () -> {
            ArrayList<String> taskName = new ArrayList<>();
            taskName.add(request.getAdditionalParam());
            userRepository.addUser(new User(request.getUserName(), taskName));

        });

        return Result.CREATED.name();
    }
}
