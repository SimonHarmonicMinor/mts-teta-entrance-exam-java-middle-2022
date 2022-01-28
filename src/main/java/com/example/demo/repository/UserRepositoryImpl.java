package com.example.demo.repository;

import static java.util.Objects.nonNull;

import com.example.demo.db.Database;
import com.example.demo.type.MyLittleBean;
import com.example.demo.type.TaskStatus;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@MyLittleBean
public class UserRepositoryImpl implements UserRepository {

    private Map<String, Set<String>> userTable;

    public UserRepositoryImpl() {
        this.userTable = Database.getInstance().getUserTable();
    }

    @Override
    public Set<String> readTasksForUser(String userName) {
        return userTable.get(userName);
    }

    @Override
    public void updateUserTasks(String userName, String taskName) {
        Set<String> taskSet = userTable.get(userName);
        if(!nonNull(taskSet)) {
            taskSet = new LinkedHashSet<>();
            taskSet.add(taskName);
            userTable.put(userName, taskSet);
        } else {
            taskSet.add(taskName);
        }
    }
}
