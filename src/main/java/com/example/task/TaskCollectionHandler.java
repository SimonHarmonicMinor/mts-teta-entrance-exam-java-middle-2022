package com.example.task;

import com.example.response.Response;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TaskCollectionHandler {
    public static ArrayList<Task> TaskCollection = new ArrayList<Task>();

    public void CreateTask(String userName, String taskName, Response response) throws Exception{
        try
        {
            if (TaskCollection.stream().anyMatch(x -> x.getName().equals(taskName) && !x.getStatus().equals(Task.TaskStatuses.DELETED)))
                throw new Exception("Error while adding entity");
            TaskCollection.add(new Task(taskName, userName));
            response.setResponseCode(Response.ResponseCodes.CREATED);
        }
        catch (Exception e){
            response.setResponseCode(Response.ResponseCodes.ERROR);
        }
    }

   public void CloseTask(String userName, String taskName, Response response) throws Exception {
       Supplier<Stream<Task>> supplier = () ->TaskCollection.stream()
               .filter(x -> !x.getStatus().equals(Task.TaskStatuses.DELETED) && x.getName().equals(taskName));
        if (supplier.get().count() == 0)
            throw new Exception("Task name doesn't found");
        if (!supplier.get().findFirst().get().getCreatedBy().equals(userName)){
            response.setResponseCode(Response.ResponseCodes.ACCESS_DENIED);
            return;
        }
       for (Task x : TaskCollection) {
           if (x.getStatus().equals(Task.TaskStatuses.CREATED) && x.getName().equals(taskName) && x.getCreatedBy().equals(userName)) {
               x.setStatus(Task.TaskStatuses.CLOSED, response);
               response.setResponseCode(Response.ResponseCodes.CLOSED);
               return;
           }
       }
       response.setResponseCode(Response.ResponseCodes.ERROR);
    }

    public void DeleteTask(String userName, String taskName, Response response) throws Exception {
        Supplier<Stream<Task>> supplier = () ->TaskCollection.stream()
                .filter(x -> !x.getStatus().equals(Task.TaskStatuses.DELETED) && x.getName().equals(taskName));
        if (supplier.get().count() == 0)
            throw new Exception("Task name doesn't found");
        if (!supplier.get().findFirst().get().getCreatedBy().equals(userName)){
            response.setResponseCode(Response.ResponseCodes.ACCESS_DENIED);
            return;
        }
        TaskCollection.removeIf(x -> x.getName().equals(userName) && x.getCreatedBy().equals(userName));
        response.setResponseCode(Response.ResponseCodes.DELETED);
    }

    public void ReopenTask(String userName, String taskName, Response response) throws Exception {
        Supplier<Stream<Task>> supplier = () ->TaskCollection.stream()
                .filter(x -> !x.getStatus().equals(Task.TaskStatuses.DELETED) && x.getName().equals(taskName));
        if (supplier.get().count() != 1)
            throw new Exception("Task name doesn't found or count more than 1");
        if (!supplier.get().findFirst().get().getCreatedBy().equals(userName)){
            response.setResponseCode(Response.ResponseCodes.ACCESS_DENIED);
            return;
        }

        for (Task x : TaskCollection) {
            if (!x.getStatus().equals(Task.TaskStatuses.DELETED) && x.getName().equals(taskName) && x.getCreatedBy().equals(userName)) {
                x.setStatus(Task.TaskStatuses.CREATED, response);
                response.setResponseCode(Response.ResponseCodes.REOPENED);
                return;
            }
        }
        response.setResponseCode(Response.ResponseCodes.ERROR);
    }

    public void GetAllUserTasks(String userName, Response response){
        var task = TaskCollection.stream().filter(x -> !x.getStatus().equals(Task.TaskStatuses.DELETED) && x.getCreatedBy().equals(userName))
                .map(x -> x.getName());
        response.setResponseCode(Response.ResponseCodes.TASKS);
        response.setArgs(task.toArray(String[]::new));

    }

}
