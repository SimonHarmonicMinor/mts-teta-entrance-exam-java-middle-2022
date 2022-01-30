package com.example.demo.model;

import java.util.List;

public class ResponseModel {
    private ResultType resultType;
    private String tasks;

    public ResponseModel(ResultType resultType) {
        this.resultType = resultType;
    }

    public ResponseModel(ResultType resultType, List<Task> tasks) {
        this.resultType = resultType;
        if (tasks != null) {
            this.tasks = tasks.toString();
        }
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public String getTasks() {
        return tasks;
    }

    public void setTasks(String tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return tasks == null ? resultType.getValue() : resultType.getValue() + " " + tasks;
    }
}
