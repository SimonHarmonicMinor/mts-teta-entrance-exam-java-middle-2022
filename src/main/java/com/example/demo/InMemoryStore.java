package com.example.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InMemoryStore implements com.example.demo.domain.Store {
    private Map<String, Task> taskMap = new HashMap<>();

    @Override
    public void storeTask(String user, String name) {
        taskMap.put(name, new Task(user, name, TaskStatus.OPENED));
    }

    @Override
    public boolean hasTask(String name) {
        return taskMap.containsKey(name);
    }

    @Override
    public String getTaskOwner(String taskName) {
        return findTask(taskName).owner;
    }

    @Override
    public void setTaskClosed(String name) {
        setTaskStatus(name, TaskStatus.CLOSED);
    }

    @Override
    public void setTaskDeleted(String name) {
        taskMap.remove(name);
    }

    @Override
    public void setTaskOpened(String name) {
        setTaskStatus(name, TaskStatus.OPENED);
    }

    @Override
    public boolean isTaskOpened(String name) {
        return testTaskStatus(name, TaskStatus.OPENED);
    }

    @Override
    public Collection<String> queryUserTasks(String owner) {
        return taskMap
                .values().stream()
                .filter(task -> task.owner.equals(owner))
                .map(task -> task.name)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isTaskClosed(String name) {
        return testTaskStatus(name, TaskStatus.CLOSED);
    }

    @Override
    public boolean isTaskDeleted(String name) {
        return testTaskStatus(name, TaskStatus.DELETED);
    }

    private void setTaskStatus(String name, TaskStatus status) {
        findTask(name).status = status;
    }

    private boolean testTaskStatus(String name, TaskStatus status) {
        return findTask(name).status.equals(status);
    }

    private Task findTask(String name) {
        var task = taskMap.get(name);
        assertCond(task != null, "task was not found: " + name);
        return task;
    }




    private static class Task {
        String owner;
        String name;
        TaskStatus status;

        Task(String owner, String name, TaskStatus status) {
            this.owner = owner;
            this.name = name;
            this.status = status;
        }
    }

    private enum TaskStatus {
        OPENED, CLOSED, DELETED
    }

    private static void assertCond(boolean cond, String message) throws RuntimeException {
        if (!cond) {
            throw new RuntimeException(message);
        }
    }

}
