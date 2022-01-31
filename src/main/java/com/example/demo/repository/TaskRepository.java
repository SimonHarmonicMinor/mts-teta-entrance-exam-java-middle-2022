package com.example.demo.repository;

import com.example.demo.model.Task;
import com.example.demo.model.enums.State;

import java.util.ArrayList;

public class TaskRepository {

    private ArrayList<Task> tasks;

    public TaskRepository(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public Task addTask(String title) {
        Task task = new Task(title);
        tasks.add(task);
        return task;
    }

    public Task getTask(String title) {
        return tasks.stream()
                .filter(t -> !State.DELETED.equals(t.getState()))
                .filter(t -> title.equals(t.getTitle()))
                .findAny()
                .orElse(null);
    }

}
