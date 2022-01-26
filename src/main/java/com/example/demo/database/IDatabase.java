package com.example.demo.database;

import java.util.List;

public interface IDatabase {

    public boolean checkUsers(String userName);

    public boolean addUser(String userName);

    public boolean checkTasks(String taskName);

    public boolean addTask(String userName, String taskName);

    public boolean closeTask(String userName, String taskName);

    public boolean reopenTask(String userName, String taskName);

    public boolean deleteTask(String userName, String taskName);

    public List<String> getTasks(String userName);

    public List<String> getUsers();

}
