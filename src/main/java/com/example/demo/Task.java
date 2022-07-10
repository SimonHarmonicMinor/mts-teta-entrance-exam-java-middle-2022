package com.example.demo;

import java.util.Date;

public class Task {
    public static int task_count = 0;
    private String status;
    private String created_by;
//    private Date created;
    private int task_num;

    public int getTask_num() {
        return task_num;
    }

    private String name;

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return created_by;
    }

//    public Date getCreated() {
//        return created;
//    }

    public String getStatus() {
        return status;
    }

    public Task(String status, String created_by, String name) {
        this.status = status;
        this.created_by = created_by;
        this.name = name;
        this.task_num = task_count++;
//        this.created = new Date();
    }

    @Override
    public String toString() {
        return this.name;
//        return String.valueOf(this.task_num);
    }
}
