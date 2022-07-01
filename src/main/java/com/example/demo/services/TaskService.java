package com.example.demo.services;

import com.example.demo.enums.ResponseCodes;
import com.example.demo.enums.TaskStatus;
import com.example.demo.exception.DemoAppException;
import com.example.demo.model.Response;
import com.example.demo.persistence.entity.Task;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class TaskService {
    public static ArrayList<Task> tasks = new ArrayList<>();

    public void createTask(String userName, String taskName, Response response) {
        try {
            if (tasks.stream().anyMatch(x -> x.getName().equals(taskName) && !x.getStatus().equals(TaskStatus.DELETED)))
                throw new DemoAppException("Возникла ошибка при добавлении задачи", "ERROR");
            tasks.add(new Task(taskName, userName));
            response.setResponseCode(ResponseCodes.CREATED);
        } catch (Exception e){
            response.setResponseCode(ResponseCodes.ERROR);
        }
    }

   public void closeTask(String userName, String taskName, Response response) throws Exception {
       Supplier<Stream<Task>> userTask = () -> tasks.stream()
               .filter(x -> !x.getStatus().equals(TaskStatus.DELETED) && x.getName().equals(taskName));
        if (userTask.get().count() == 0) {
            throw new DemoAppException("Задача не найдена", "ERROR");
        }
        if (!userTask.get().findFirst().get().getCreatedByUser().equals(userName)){
            response.setResponseCode(ResponseCodes.ACCESS_DENIED);
            return;
        }
       for (Task x : tasks) {
           if (x.getStatus().equals(TaskStatus.CREATED) && x.getName().equals(taskName) && x.getCreatedByUser().equals(userName)) {
               x.setStatus(TaskStatus.CLOSED, response);
               response.setResponseCode(ResponseCodes.CLOSED);
               return;
           }
       }
       response.setResponseCode(ResponseCodes.ERROR);
    }

    public void deleteTask(String userName, String taskName, Response response) throws Exception {
        Supplier<Stream<Task>> userTask = () -> tasks.stream()
                .filter(x -> !x.getStatus().equals(TaskStatus.DELETED)
                        && !x.getStatus().equals(TaskStatus.CREATED) && x.getName().equals(taskName));

        if (userTask.get().count() == 0) {
            throw new DemoAppException("Задача не найдена", "ERROR");
        } else if (!userTask.get().findFirst().get().getCreatedByUser().equals(userName)){
            response.setResponseCode(ResponseCodes.ACCESS_DENIED);
            return;
        }

        tasks.removeIf(x -> x.getName().equals(taskName) && x.getCreatedByUser().equals(userName));
        response.setResponseCode(ResponseCodes.DELETED);
    }

    public void reopenTask(String userName, String taskName, Response response) throws Exception {
        Supplier<Stream<Task>> userTask = () -> tasks.stream()
                .filter(x -> !x.getStatus().equals(TaskStatus.DELETED)
                        && !x.getStatus().equals(TaskStatus.CREATED) && x.getName().equals(taskName));
        if (userTask.get().count() != 1) {
            throw new DemoAppException("Задача не найдена или упоминается больше 1 раза", "ERROR");
        } else if (!userTask.get().findFirst().get().getCreatedByUser().equals(userName)){
            response.setResponseCode(ResponseCodes.ACCESS_DENIED);
            return;
        }

        for (Task task : tasks) {
            if (!task.getStatus().equals(TaskStatus.DELETED) && task.getName().equals(taskName)
                    && task.getCreatedByUser().equals(userName)) {
                task.setStatus(TaskStatus.CREATED, response);
                response.setResponseCode(ResponseCodes.REOPENED);
                return;
            }
        }
        response.setResponseCode(ResponseCodes.ERROR);
    }

    public void getAllUserTasks(String userName, Response response) {
        Stream<String> userTasks = tasks.stream()
                    .filter(x -> !x.getStatus().equals(TaskStatus.DELETED) && x.getCreatedByUser().equals(userName))
                    .map(Task::getName);
        response.setResponseCode(ResponseCodes.TASKS);
        response.setArgs(userTasks.toArray(String[]::new));
    }

    public void deleteAllUserTasks(Response response) {
        tasks.clear();
        response.setResponseCode(ResponseCodes.DELETED);
    }
}
