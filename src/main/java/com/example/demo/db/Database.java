package com.example.demo.db;

import com.example.demo.type.TaskStatus;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Database {
    private Map<String, Set<String>> userTable;
    private Map<String, TaskStatus> taskTable;
    private static Database instance;

    private Database() {
        this.userTable = new HashMap<>();
        this.taskTable = new HashMap<>();
    }

    public static Database getInstance(){
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Map<String, Set<String>> getUserTable() {
        return userTable;
    }

    public Map<String, TaskStatus> getTaskTable() {
        return taskTable;
    }
}
