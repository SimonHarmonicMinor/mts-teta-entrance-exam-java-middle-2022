package com.example.demo.TasksService;

import com.example.demo.ResultHandler.Result;
import com.example.demo.ResultHandler.ResultTypes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TaskOperation {
    private static final List<Task> TasksList = new ArrayList<>();

   public void CreateTask(String User, String Name, Result result){
       for (Task task : TasksList) {
               if (Name.equals(task.getName()) && !task.getStatus().equals(TasksStatuses.DELETED)) {
                   result.setResult(ResultTypes.ERROR);
                   return;
               }
       }
       TasksList.add(new Task(Name,TasksStatuses.CREATED,User));
       result.setResult(ResultTypes.CREATED);
    }

    public void DeleteTask(String User, String Name, Result result){
        for (Task task : TasksList) {
            if (Name.equals(task.getName())) {
                if (!User.equals(task.getUser())) {
                    result.setResult(ResultTypes.ACCESS_DENIED);
                    return;
                }
                else if (User.equals(task.getUser()) && task.getStatus().equals(TasksStatuses.CLOSED)) {
                    result.setResult(ResultTypes.DELETED);
                    task.setStatus(TasksStatuses.DELETED);
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
                else if (User.equals(task.getUser()) && !task.getStatus().equals(TasksStatuses.DELETED)) {
                    result.setResult(ResultTypes.CLOSED);
                    task.setStatus(TasksStatuses.CLOSED);
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
                else if (User.equals(task.getUser()) && task.getStatus().equals(TasksStatuses.CLOSED)) {
                    result.setResult(ResultTypes.CREATED);
                    task.setStatus(TasksStatuses.CREATED);
                    return;
                }
            }
            else result.setResult(ResultTypes.ERROR);
        }
    }

    public void ListTask(String User, String Name){

    }
}
