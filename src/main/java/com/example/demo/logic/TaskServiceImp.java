package com.example.demo.logic;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repo.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TaskServiceImp implements TaskService {
    private final Logger log = LoggerFactory.getLogger(TaskServiceImp.class);
    private final TaskRepository taskRepository;

    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        log.info("Service initiated");
    }

    @Override
    public Task findByName(String taskName) {
        log.info("start finding by taskName");
        return taskRepository.findByName(taskName);
    }

    @Override
    public List<Task> findAllByUser(User user) {
        log.info("start finding by all tasks by user");
        return taskRepository.findByUser(user);
    }

    @Override
    public void createTask(String taskName, User user) {
        log.info("start creating task");
        taskRepository.save(new Task(taskName, user));
    }

    @Override
    public void openTask(String taskName) {
        log.info("start opening task");
        taskRepository.findByName(taskName).open();
    }

    @Override
    public void closeTask(String taskName) {
        log.info("start closing task");
        taskRepository.findByName(taskName).close();
    }

    @Override
    public void deleteTask(String taskName) {
        log.info("start deleting task");
        taskRepository.findByName(taskName).delete();
    }
}
