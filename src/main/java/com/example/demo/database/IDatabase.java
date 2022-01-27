package com.example.demo.database;

import java.util.Map;
import java.util.Set;

public interface IDatabase {

    boolean checkUsers(String userName);

    boolean addUser(String userName);

    boolean checkTasks(String taskName);

    boolean addTask(String userName, String taskName);

    boolean closeTask(String userName, String taskName);

    boolean reopenTask(String userName, String taskName);

    boolean deleteTask(String userName, String taskName);

    Set<String> getTasks(String userName);

    Set<String> getUsers();

    Map<String, Set<String>> getCreatedTasks();

    Map<String, Set<String>> getClosedTasks();

    Map<String, Set<String>> getDeletedTasks();
}
