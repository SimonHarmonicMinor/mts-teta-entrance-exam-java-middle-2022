package com.example.demo.repository;

import com.example.demo.db.Database;
import com.example.demo.type.MyLittleBean;
import com.example.demo.type.TaskStatus;
import java.util.Map;

@MyLittleBean
public class TaskRepositoryImpl implements TaskRepository {

    private Map<String, TaskStatus> taskTable;

    public TaskRepositoryImpl() {
        this.taskTable = Database.getInstance().getTaskTable();
    }

    @Override
    public void createTask(String taskName) {
        taskTable.put(taskName, TaskStatus.CREATED);
    }

    @Override
    public TaskStatus readStatus(String taskName) {
        return taskTable.get(taskName);
    }

    @Override
    public void updateTask(String taskName, TaskStatus taskStatus) {
        taskTable.put(taskName, taskStatus);
    }

    @Override
    public void removeTask(String taskName) {
        taskTable.remove(taskName);
    }
}
