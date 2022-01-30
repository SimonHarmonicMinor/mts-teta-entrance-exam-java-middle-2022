package com.example.demo.domain;

import java.util.Collection;

public class Service {
    private final Store store;

    public Service(Store store) {
        this.store = store;
    }

    public void createTask(String user, String name) {
        assertCond(!store.hasTask(name), "task already exists: " + name);
        store.storeTask(user, name);
    }

    public void closeTask(String user, String task) {
        checkTaskAndAccess(user, task);
        assertCond(!store.isTaskClosed(task), "task already was closed: " + task);
        store.setTaskClosed(task);
    }

    public Collection<String> listTasks(String user, String owner) {
        return store.queryUserTasks(owner);
    }

    public void deleteTask(String user, String taskName) {
        checkTaskAndAccess(user, taskName);
        assertCond(store.isTaskClosed(taskName), "task is not closed" + taskName);
        store.setTaskDeleted(taskName);
    }

    private void checkTaskAndAccess(String user, String taskName) {
        assertCond(store.hasTask(taskName), "task does not exist: " + taskName);
        if (!store.getTaskOwner(taskName).equals(user)) {
            throw new AccessDenied("access denied for task: " + taskName + ", user: " + user);
        }
    }

    public void reopenTask(String user, String taskName) {
        checkTaskAndAccess(user, taskName);
        assertCond(store.isTaskClosed(taskName), "task is not closed" + taskName);
        store.setTaskOpened(taskName);
    }

    public static void assertCond(boolean cond, String message) throws WrongOperation {
        if (!cond) {
            throw new WrongOperation(message);
        }
    }

    public static class AccessDenied extends RuntimeException {
        public AccessDenied(String message) {
            super(message);
        }
    }

    public static class WrongOperation extends RuntimeException {
        public WrongOperation(String message) {
            super(message);
        }
    }
}
