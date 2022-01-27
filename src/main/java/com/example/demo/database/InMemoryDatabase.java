package com.example.demo.database;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryDatabase implements IDatabase {
    private final Set<String> users;
    private final Map<String, Set<String>> createdTasks;
    private final Map<String, Set<String>> closedTasks;
    private final Map<String, Set<String>> deletedTasks;

    public InMemoryDatabase() {
        users = new LinkedHashSet<>();
        createdTasks = new HashMap<>();
        closedTasks = new HashMap<>();
        deletedTasks = new HashMap<>();
    }

    @Override
    public boolean checkUsers(String userName) {
        return users.contains(userName);
    }

    @Override
    public boolean addUser(String userName) {
        createdTasks.put(userName, new LinkedHashSet<>());
        closedTasks.put(userName, new LinkedHashSet<>());
        deletedTasks.put(userName, new LinkedHashSet<>());
        return users.add(userName);
    }

    @Override
    public boolean checkTasks(String taskName) {
        boolean taskFind = false;
        for (Set<String> taskSet : createdTasks.values()) {
            if (taskSet.contains(taskName)) {
                taskFind = true;
                break;
            }
        }

        for (Set<String> taskSet : closedTasks.values()) {
            if (taskSet.contains(taskName)) {
                taskFind = true;
                break;
            }
        }

        return taskFind;
    }

    @Override
    public boolean addTask(String userName, String taskName) {
        if (!createdTasks.containsKey(userName)) {
            createdTasks.put(userName, new LinkedHashSet<>());
        }

        return createdTasks.get(userName).add(taskName);
    }

    @Override
    public boolean closeTask(String userName, String taskName) {
        if(!users.contains(userName)){
            return false;
        }else if (createdTasks.get(userName).remove(taskName)) {
            return closedTasks.get(userName).add(taskName);
        } else {
            return false;
        }
    }

    @Override
    public boolean reopenTask(String userName, String taskName) {
        if(!users.contains(userName)){
            return false;
        }else if (closedTasks.get(userName).remove(taskName)) {
            return createdTasks.get(userName).add(taskName);
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteTask(String userName, String taskName) {
        if(!users.contains(userName)){
            return false;
        }else if (closedTasks.get(userName).remove(taskName)) {
            return deletedTasks.get(userName).add(taskName);
        } else {
            return false;
        }
    }

    @Override
    public Set<String> getTasks(String userName) {

        if (!users.contains(userName)) {
            return Collections.emptySet();
        } else {
            return Stream.concat(createdTasks.get(userName).stream(),
                            closedTasks.get(userName).stream())
                    .collect(Collectors.toSet());
        }
    }

    @Override
    public Set<String> getUsers() {
        return users;
    }

    @Override
    public Map<String, Set<String>> getCreatedTasks() {
        return createdTasks;
    }

    @Override
    public Map<String, Set<String>> getClosedTasks() {
        return closedTasks;
    }

    @Override
    public Map<String, Set<String>> getDeletedTasks() {
        return deletedTasks;
    }
}
