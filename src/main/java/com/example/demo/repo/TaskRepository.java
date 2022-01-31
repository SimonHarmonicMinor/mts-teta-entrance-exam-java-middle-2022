package com.example.demo.repo;

import com.example.demo.entity.Task;
import com.example.demo.exception.OtherErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepository implements RepositoryInterface<String, Task> {

    private final Map<String, Task> taskMap = new HashMap<>() {{
        put("Em", new Task("Em", "VASYA", false));
        put("Om", new Task("Om", "VASYA", true));
    }};

    @Override
    public void create(Task task) {
        task.setActive(true);
        taskMap.put(task.getName(), task);
    }

    @Override
    public void remove(String name) {
        Task task = taskMap.get(name);
        if (task == null || task.isActive()) {
            throw new OtherErrorException();
        }
        taskMap.remove(name);
    }

    @Override
    public Task getEntity(String name) {

        return taskMap.get(name);
    }

    @Override
    public List<Task> readAll() {
        return new ArrayList<>(taskMap.values());
    }


    @Override
    public void close(String name) {
        Task task = taskMap.get(name);
        if (task == null) {
            throw new OtherErrorException();
        }
        task.setActive(false);
    }

    @Override
    public void reopen(String name) {
        Task task = taskMap.get(name);
        if (task == null) {
            throw new OtherErrorException();
        }
        task.setActive(true);
    }
}
