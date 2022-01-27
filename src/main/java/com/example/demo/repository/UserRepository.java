package com.example.demo.repository;

import com.example.demo.type.TaskStatus;
import java.util.Set;

public interface UserRepository {

    void createUser(String userName);

    Set<String> readTasksForUser(String userName);

    void updateUserTasks(String userName, String taskName, TaskStatus taskStatus);

}
