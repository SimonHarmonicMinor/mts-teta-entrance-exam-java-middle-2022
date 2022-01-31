package com.example.demo.config;

import com.example.demo.logic.*;
import com.example.demo.repo.TaskRepository;
import com.example.demo.repo.TaskRepositoryImp;

public class TaskConfig {
    public static TaskController createController() {
        TaskRepository repository = new TaskRepositoryImp();
        TaskService service = new TaskServiceImp(repository);

        return new TaskControllerImp(service);
    }

    public static TaskResponser createResponser() {
        return new TaskResponserImp();
    }
}
