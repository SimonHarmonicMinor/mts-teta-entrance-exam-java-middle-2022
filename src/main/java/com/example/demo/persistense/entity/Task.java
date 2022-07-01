package com.example.demo.persistense.entity;

import com.example.demo.enums.ResponseCodes;
import com.example.demo.enums.TaskStatus;
import com.example.demo.exception.DemoAppException;
import com.example.demo.model.Response;

public class Task {
    private String taskName;
    private TaskStatus taskStatus;
    private String createdByUser;

    public Task(String name, String createdBy) {
        taskName = name;
        taskStatus = TaskStatus.CREATED;
        createdByUser = createdBy;
    }

    public String getName() {
        return taskName;
    }

    public TaskStatus getStatus() {
        return taskStatus;
    }

    public void setStatus(TaskStatus status, Response response) throws Exception {
        try {
            if (this.taskStatus == TaskStatus.DELETED)
                throw new DemoAppException("Задача в статусе DELETED больше не может переходить ни в какое состояние", "ERROR");
            if (this.taskStatus == TaskStatus.CREATED && status == TaskStatus.DELETED)
                throw new DemoAppException("Задача в статусе CREATED не может сразу перейти в DELETED", "ERROR");
            taskStatus = status;
        }
        catch (Exception ex){
            response.setResponseCode(ResponseCodes.ERROR);
            throw ex;
        }
    }

    public String getCreatedByUser() {
        return createdByUser;
    }

}
