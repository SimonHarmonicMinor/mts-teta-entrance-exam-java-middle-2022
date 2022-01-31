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
        log.debug("Repo initiated");
    }

    @Override
    public Task getByTaskName(String name) {
        log.debug("Start findingByName");

        for (Task t : repo) {
            if (t.getName().equals(name)) {
                log.debug("Task was found by taskName");
                return t;
            }
        }

        log.debug("Task was not found by taskName");
        return null;
    }

    @Override
    public List<Task> getByUser(User user) {
        log.debug("Starting find tasks by user");

        List<Task> tasks = new ArrayList<>();
        for (Task t : repo) {
            if (t.getUser().equals(user)) {
                tasks.add(t);
            }
        }

        log.debug("Tasks by user were collected");
        return tasks;
    }


    @Override
    public void add(Task task) {
        log.debug("Start saving task");

        repo.add(task);
    }

    @Override
    public void delete(Task task) {
        log.debug("Start deleting task");

        repo.remove(task);
    }

    @Override
    public boolean isContainedByTaskName(String taskName) {
        return getByTaskName(taskName) != null;
    }
}
