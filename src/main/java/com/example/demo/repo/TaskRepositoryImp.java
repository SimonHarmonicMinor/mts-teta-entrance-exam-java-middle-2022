package com.example.demo.repo;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TaskRepositoryImp implements TaskRepository {
    private final Logger log = LoggerFactory.getLogger(TaskRepositoryImp.class);

    private List<Task> repo;

    public TaskRepositoryImp() {
        this.repo = new ArrayList<>();

        log.info("Repo initiated");
    }

    @Override
    public List<Task> findAll() {
        log.info("Collected all tasks in repo");
        return repo;
    }

    @Override
    public Task findByName(String name) {
        log.info("Start findingByName");

        for (Task t : repo) {
            if (t.getName().equals(name)) {
                log.info("Task was found by taskName");
                return t;
            }
        }

        log.info("Task was not found by taskName");
        return null;
    }

    @Override
    public List<Task> findByUser(User user) {
        log.info("Starting find tasks by user");

        List<Task> tasks = new ArrayList<>();
        for (Task t : repo) {
            if (t.getUser().equals(user)) {
                tasks.add(t);
            }
        }

        log.info("Tasks by user were collected");
        return tasks;
    }

    @Override
    public boolean save(Task task) {
        log.info("Start saving task");

        return repo.add(task);
    }

    @Override
    public boolean delete(Task task) {
        log.info("Start deleting task");

        return repo.remove(task);
    }
}
