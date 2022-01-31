package com.example.demo.TasksServices;

import com.example.demo.ResultHandler.Result;
import com.example.demo.ResultHandler.ResultTypes;

import java.util.ArrayList;
import java.util.List;

public class TaskOperation {

    private static final List<Task> TasksList = new ArrayList<>();

    public void CreateTask(String User, String Name, Result result){
        for (Task task : TasksList) {
            if (Name.equals(task.getName()) && !task.getStatus().equals(TaskStatuses.DELETED)) {
                result.setResult(ResultTypes.ERROR);
                return;
            }
        }
        TasksList.add(new Task(Name,TaskStatuses.CREATED,User));
        result.setResult(ResultTypes.CREATED);
    }

    public void DeleteTask(String User, String Name, Result result){
        for (Task task : TasksList) {
            if (Name.equals(task.getName())) {
                if (!User.equals(task.getUser())) {
                    result.setResult(ResultTypes.ACCESS_DENIED);
                    return;
                }
                else if (User.equals(task.getUser()) && task.getStatus().equals(TaskStatuses.CLOSED)) {
                    result.setResult(ResultTypes.DELETED);
                    task.setStatus(TaskStatuses.DELETED);
                    return;
                }
            }
            else result.setResult(ResultTypes.ERROR);
        }
    }

    public void CloseTask(String User, String Name, Result result){
        for (Task task : TasksList) {
            if (Name.equals(task.getName())) {
                if (!User.equals(task.getUser())) {
                    result.setResult(ResultTypes.ACCESS_DENIED);
                    return;
                }
                else if (User.equals(task.getUser()) && !task.getStatus().equals(TaskStatuses.DELETED)) {
                    result.setResult(ResultTypes.CLOSED);
                    task.setStatus(TaskStatuses.CLOSED);
                    return;
                }
            }
            else result.setResult(ResultTypes.ERROR);
        }
    }

    public void ReopenTask(String User, String Name, Result result){
        for (Task task : TasksList) {
            if (Name.equals(task.getName())) {
                if (!User.equals(task.getUser())) {
                    result.setResult(ResultTypes.ACCESS_DENIED);
                    return;
                }
                else if (User.equals(task.getUser()) && task.getStatus().equals(TaskStatuses.CLOSED)) {
                    result.setResult(ResultTypes.CREATED);
                    task.setStatus(TaskStatuses.CREATED);
                    return;
                }
            }
            else result.setResult(ResultTypes.ERROR);
        }
    }

    public void ListTask(String User, String Name){

    }
}
