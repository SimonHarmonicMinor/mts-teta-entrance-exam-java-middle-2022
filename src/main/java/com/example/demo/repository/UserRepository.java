package com.example.demo.repository;

import java.util.Set;

public interface UserRepository {

    Set<String> readTasksForUser(String userName);

    void updateUserTasks(String userName, String taskName);

}
