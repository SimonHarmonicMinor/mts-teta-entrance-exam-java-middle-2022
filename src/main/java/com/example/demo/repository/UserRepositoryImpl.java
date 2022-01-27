package com.example.demo.repository;

import com.example.demo.db.Database;
import com.example.demo.type.TaskStatus;
import java.util.Map;
import java.util.Set;

public class UserRepositoryImpl implements UserRepository {

    private Map<String, Set<String>> userTable;

    public UserRepositoryImpl() {
        this.userTable = Database.getInstance().getUserTable();
    }

    @Override
    public void createUser(String userName) {
    }

    @Override
    public Set<String> readTasksForUser(String userName) {
        return null;
    }

    @Override
    public void updateUserTasks(String userName, String taskName, TaskStatus taskStatus) {

    }
}
