package com.example.demo.repositories;

import com.example.demo.helpers.TaskInfo;
import com.example.demo.helpers.TaskState;
import com.example.demo.models.Task;

import java.util.LinkedHashMap;
import java.util.Optional;

public class TaskRepository {
    private final LinkedHashMap<String, TaskInfo> tasks;

    public TaskRepository() {
        tasks = new LinkedHashMap<>();
    }

    public boolean addTask(Task task) {
        return tasks.putIfAbsent(task.getName(), new TaskInfo(task)) == null;
    }

    public boolean updateTask(Task task) {
        return tasks.put(task.getName(), new TaskInfo(task)) != null;
    }

    public Optional<Task> getTaskByName(String taskName) {
        TaskInfo taskInfo = tasks.get(taskName);
        if (taskInfo != null) {
            return Optional.of(new Task(taskName,taskInfo));
        }
        return Optional.empty();
    }

    public Task[] getTasksByUser(String user) {
        return tasks.entrySet().stream().filter(x -> x.getValue().getUser().contentEquals(user) && x.getValue().getState() != TaskState.DELETED).map(x -> new Task(x.getKey(),x.getValue())).toArray(Task[]::new);
    }
}
